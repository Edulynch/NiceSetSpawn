package me.edulynch.nicesetspawn.commands;

import me.edulynch.nicesetspawn.Config.enumConfig;
import me.edulynch.nicesetspawn.Config.enumLang;
import me.edulynch.nicesetspawn.Main;
import me.edulynch.nicesetspawn.helpers.Spawn;
import me.edulynch.nicesetspawn.helpers.SpawnEffect;
import me.edulynch.nicesetspawn.interfaces.CommandTab;
import me.edulynch.nicesetspawn.utils.Constants;
import me.edulynch.nicesetspawn.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NiceSetSpawnCMD implements CommandTab {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase(Constants.COMMAND_HELP)) {
            showHelp(sender, command);
        } else if (args[0].equalsIgnoreCase(Constants.COMMAND_RELOAD)) {
            reloadCommand(sender);
        } else if (args[0].equalsIgnoreCase(Constants.COMMAND_SETDELAY)) {
            setDelayCommand(sender, args);
        } else if (args[0].equalsIgnoreCase(Constants.COMMAND_INFO)) {
            showInfo(sender);
        } else if (args[0].equalsIgnoreCase(Constants.COMMAND_SHOWEFFECTS)) {
            if (Utils.verifyIfIsConsole(sender)) return true;
            testSpawnEffects(sender);
        }
        return true;
    }

    private void testSpawnEffects(CommandSender sender) {
        if (Utils.hasPermission(sender, Constants.PERMISSION_SHOWEFFECTS)) {
            Player player = (Player) sender;
            try {
                Location location = Spawn.getLocation();
                SpawnEffect.register(location);
            } catch (Exception e) {
                player.sendMessage(enumLang.MESSAGES_SPAWN_NOT_SET.getConfigValue(player));
            }
        } else {
            sender.sendMessage(enumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
        }
    }

    private void showInfo(CommandSender sender) {
        if (Utils.hasPermission(sender, Constants.PERMISSION_INFO)) {
            sender.sendMessage("§6===== §bNiceSetSpawn v" + Constants.PLUGIN_VERSION + " §6=====");
            Utils.urlHoverClick(sender, "§bSpigotMC Page:", "Visit the SpigotMC page!", Constants.URL_SPIGOTMC);
            Utils.urlHoverClick(sender, "§bGithub Page:", "Visit the Github page!", Constants.URL_GITHUB);
            Utils.urlHoverClick(sender, "§bDiscord:", "Visit the Discord!", Constants.URL_DISCORD);
        } else {
            sender.sendMessage(enumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
        }
    }

    private void showHelp(CommandSender sender, Command command) {
        if (Utils.hasPermission(sender, Constants.PERMISSION_HELP)) {
            sender.sendMessage("§6===== §bNiceSetSpawn §6=====");
            sender.sendMessage("§b/spawn §7- " + Utils.color(sender, enumLang.MESSAGES_TELEPORT_SPAWN_MESSAGE.getConfigValue(sender)));
            sender.sendMessage("§b/setspawn §7- " + Utils.color(sender, enumLang.MESSAGES_TELEPORT_SETSPAWN_MESSAGE.getConfigValue(sender)));
            sender.sendMessage("§b/" + command.getName() + " | /" + command.getName() + " help §7- " + Utils.color(sender, enumLang.MESSAGES_COMMANDS_LIST_MESSAGE.getConfigValue(sender)));
            sender.sendMessage("§b/" + command.getName() + " info §7- " + Utils.color(sender, enumLang.MESSAGES_PLUGIN_INFO_MESSAGE.getConfigValue(sender)));
            sender.sendMessage("§b/" + command.getName() + " setdelay [" + Utils.color(sender, enumLang.MESSAGES_SECONDS_MESSAGE.getConfigValue(sender)) + "] §7- " + Utils.color(sender, enumLang.MESSAGES_SETSPAWN_DELAY_MESSAGE.getConfigValue(sender)));
            sender.sendMessage("§b/" + command.getName() + " reload §7- " + Utils.color(sender, enumLang.MESSAGES_RELOAD_CONFIG_MESSAGE.getConfigValue(sender)));
        } else {
            sender.sendMessage(enumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
        }
    }

    private void reloadCommand(@NotNull CommandSender sender) {
        if (Utils.hasPermission(sender, Constants.PERMISSION_RELOAD)) {
            Main.getInstance().reloadConfigFile();
            Main.getInstance().reloadConfigLang();
            sender.sendMessage(Utils.color(sender, enumLang.MESSAGES_CONFIG_RELOADED.getConfigValue(sender)));
        } else {
            sender.sendMessage(enumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
        }
    }

    private void setDelayCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (Utils.hasPermission(sender, Constants.PERMISSION_SETDELAY)) {
            if (args.length == 1) {
                try {
                    sender.sendMessage(enumLang.MESSAGES_CURRENTLY_TELEPORT_DELAY_MESSAGE.getConfigValue(sender).
                            replaceAll("%seconds%", Main.getConfiguration().
                                    getInt(enumConfig.TELEPORT_DELAY_IN_SECONDS.getPath()) + ""));
                } catch (Exception e) {
                    sender.sendMessage(enumLang.MESSAGES_ONLY_NUMBER_MESSAGE.getConfigValue(sender));
                    Main.getConfiguration().set(enumConfig.TELEPORT_DELAY_IN_SECONDS.getPath(), 0);
                    Main.getInstance().saveConfig();
                }
            } else {
                try {
                    Main.getConfiguration().set(enumConfig.TELEPORT_DELAY_IN_SECONDS.getPath(), Integer.parseInt(args[1]));
                    Main.getInstance().saveConfig();

                    sender.sendMessage(enumLang.MESSAGES_CHANGED_TELEPORT_DELAY_MESSAGE.getConfigValue(sender).
                            replaceAll("%seconds%", Main.getConfiguration().
                                    getInt(enumConfig.TELEPORT_DELAY_IN_SECONDS.getPath()) + ""));

                } catch (Exception e) {
                    sender.sendMessage(enumLang.MESSAGES_ONLY_NUMBER_MESSAGE.getConfigValue(sender));
                    Main.getConfiguration().set(enumConfig.TELEPORT_DELAY_IN_SECONDS.getPath(), 0);
                    Main.getInstance().saveConfig();
                }
            }
        } else {
            sender.sendMessage(enumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> results = new ArrayList<>();
            if (Utils.hasPermission(sender, Constants.PERMISSION_HELP) && Constants.COMMAND_HELP.startsWith(args[0])) {
                results.add(Constants.COMMAND_HELP);
            }
            if (Utils.hasPermission(sender, Constants.PERMISSION_INFO) && Constants.COMMAND_INFO.startsWith(args[0])) {
                results.add(Constants.COMMAND_INFO);
            }
            if (Utils.hasPermission(sender, Constants.PERMISSION_SETDELAY) && Constants.COMMAND_SETDELAY.startsWith(args[0])) {
                results.add(Constants.COMMAND_SETDELAY);
            }
            if (Utils.hasPermission(sender, Constants.PERMISSION_RELOAD) && Constants.COMMAND_RELOAD.startsWith(args[0])) {
                results.add(Constants.COMMAND_RELOAD);
            }
            if (Utils.hasPermission(sender, Constants.PERMISSION_SHOWEFFECTS) && Constants.COMMAND_SHOWEFFECTS.startsWith(args[0])) {
                results.add(Constants.COMMAND_SHOWEFFECTS);
            }
            return results;
        }
        return Collections.emptyList();
    }
}
