package me.edulynch.nicesetspawn.commands;

import me.edulynch.nicesetspawn.Main;
import me.edulynch.nicesetspawn.utils.ConfigUtil;
import me.edulynch.nicesetspawn.enumMessages.enumConfig;
import me.edulynch.nicesetspawn.enumMessages.enumLang;
import me.edulynch.nicesetspawn.interfaces.CommandTab;
import me.edulynch.nicesetspawn.utils.Constants;
import me.edulynch.nicesetspawn.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NiceSetSpawnCMD implements CommandTab {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase(Constants.PERMISSION_HELP)) {
            showHelp(sender, command);
        } else if (args[0].equalsIgnoreCase(Constants.PERMISSION_RELOAD)) {
            reloadCommand(sender);
        } else if (args[0].equalsIgnoreCase(Constants.PERMISSION_SETDELAY)) {
            if (Utils.verifyIfIsAPlayer(sender)) return true;
            setDelayCommand(sender, args);
        } else if (args[0].equalsIgnoreCase(Constants.PERMISSION_INFO)) {
            showInfo(sender);
        }
        return true;
    }

    private void showInfo(CommandSender sender) {
        if (Utils.hasPermission(sender, Constants.PERMISSION_INFO)) {
            sender.sendMessage("§6===== §bNiceSetSpawn v" + Constants.PLUGIN_VERSION + " §6=====");
            Utils.urlHoverClick(sender, "§bSpigotMC Page:", "Visit the SpigotMC page!", Constants.URL_SPIGOTMC);
            Utils.urlHoverClick(sender, "§bGithub Page:", "Visit the Github page!", Constants.URL_GITHUB);
            Utils.urlHoverClick(sender, "§bDiscord:", "Visit the Discord!", Constants.URL_DISCORD);
        } else {
            sender.sendMessage(ConfigUtil.getNoPermission());
        }
    }

    private void showHelp(CommandSender sender, Command command) {
        if (Utils.hasPermission(sender, Constants.PERMISSION_HELP)) {
            sender.sendMessage("§6===== §bNiceSetSpawn §6=====");
            sender.sendMessage("§b/spawn §7- " + enumLang.MESSAGES_TELEPORT_SPAWN_MESSAGE.getConfigValue());
            sender.sendMessage("§b/setspawn §7- " + Utils.color(enumLang.MESSAGES_TELEPORT_SETSPAWN_MESSAGE.getConfigValue()));
            sender.sendMessage("§b/" + command.getName() + " | /" + command.getName() + " help §7- " + Utils.color(enumLang.MESSAGES_COMMANDS_LIST_MESSAGE.getConfigValue()));
            sender.sendMessage("§b/" + command.getName() + " info §7- " + Utils.color(enumLang.MESSAGES_PLUGIN_INFO_MESSAGE.getConfigValue()));
            sender.sendMessage("§b/" + command.getName() + " setdelay [" + Utils.color(enumLang.MESSAGES_SECONDS_MESSAGE.getConfigValue()) + "] §7- " + Utils.color(enumLang.MESSAGES_SETSPAWN_DELAY_MESSAGE.getConfigValue()));
            sender.sendMessage("§b/" + command.getName() + " reload §7- " + Utils.color(enumLang.MESSAGES_RELOAD_CONFIG_MESSAGE.getConfigValue()));
        } else {
            sender.sendMessage(ConfigUtil.getNoPermission());
        }
    }

    private void reloadCommand(@NotNull CommandSender sender) {
        if (Utils.hasPermission(sender, Constants.PERMISSION_RELOAD)) {
            Main.getInstance().reloadConfigFile();
            Main.getInstance().reloadConfigLang();

            Main.getInstance().getLogger().severe(enumConfig.OPTIONS_SET_FLY_ON_JOIN_ENABLED.getConfigBoolean() + "");

            sender.sendMessage(Utils.color(enumLang.MESSAGES_CONFIG_RELOADED.getConfigValue(new String[]{})));
        } else {
            sender.sendMessage(ConfigUtil.getNoPermission());
        }
    }

    private void setDelayCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        int delayTeleportInt;
        if (Utils.hasPermission(sender, Constants.PERMISSION_SETDELAY)) {
            if (args.length == 1) {
                try {
                    if (enumLang.MESSAGES_CURRENTLY_TELEPORT_DELAY_MESSAGE.getConfigValue(new String[]{}) != null) {
                        sender.sendMessage(enumLang.MESSAGES_CURRENTLY_TELEPORT_DELAY_MESSAGE.getConfigValue(new String[]{
                                String.valueOf(Main.getConfiguration().getInt("teleport-delay-in-seconds", 0))
                        }));

                    }

                } catch (Exception e) {
                    Main.getConfiguration().set("teleport-delay-in-seconds", 0);
                    Main.getInstance().saveConfig();
                }
            } else {
                try {
                    Main.getConfiguration().set("teleport-delay-in-seconds", Integer.parseInt(args[1]));
                    Main.getInstance().saveConfig();

                    delayTeleportInt = Main.getConfiguration().getInt("teleport-delay-in-seconds");

                    if (enumLang.MESSAGES_CHANGED_TELEPORT_DELAY_MESSAGE.getConfigValue(new String[]{}) != null) {
                        sender.sendMessage(enumLang.MESSAGES_CHANGED_TELEPORT_DELAY_MESSAGE.getConfigValue(new String[]{
                                String.valueOf(delayTeleportInt)
                        }));
                    }

                } catch (Exception e) {
                    sender.sendMessage(enumLang.MESSAGES_ONLY_NUMBER_MESSAGE.getConfigValue(new String[]{}));
                    Main.getConfiguration().set("teleport-delay-in-seconds", 0);
                    Main.getInstance().saveConfig();
                }
            }
        } else {
            sender.sendMessage(ConfigUtil.getNoPermission());
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> results = new ArrayList<>();
            if (Utils.hasPermission(sender, "help") && "help".startsWith(args[0])) {
                results.add("help");
            }
            if (Utils.hasPermission(sender, "info") && "info".startsWith(args[0])) {
                results.add("info");
            }
            if (Utils.hasPermission(sender, "setdelay") && "setdelay".startsWith(args[0])) {
                results.add("setdelay");
            }
            if (Utils.hasPermission(sender, "reload") && "reload".startsWith(args[0])) {
                results.add("reload");
            }
            return results;
        }
        return Collections.emptyList();
    }
}
