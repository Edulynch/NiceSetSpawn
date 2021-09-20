package me.edulynch.nicesetspawn.commands;

import me.edulynch.nicesetspawn.config.EnumConfig;
import me.edulynch.nicesetspawn.config.EnumLang;
import me.edulynch.nicesetspawn.Main;
import me.edulynch.nicesetspawn.utils.SpawnUtil;
import me.edulynch.nicesetspawn.helpers.SpawnEffect;
import me.edulynch.nicesetspawn.interfaces.CommandTab;
import me.edulynch.nicesetspawn.utils.Constants;
import me.edulynch.nicesetspawn.utils.Util;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

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
            if (Util.verifyIfIsConsole(sender)) return true;
            testSpawnEffects(sender);
        }
        return true;
    }

    private void testSpawnEffects(CommandSender sender) {
        if (Util.hasPermission(sender, Constants.PERMISSION_SHOWEFFECTS)) {
            Player player = (Player) sender;
            try {
                Location location = SpawnUtil.getLocation();
                SpawnEffect.register(location);
            } catch (Exception e) {
                player.sendMessage(EnumLang.MESSAGES_SPAWN_NOT_SET.getConfigValue(player));
            }
        } else {
            sender.sendMessage(EnumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
        }
    }

    private void showInfo(CommandSender sender) {
        if (Util.hasPermission(sender, Constants.PERMISSION_INFO)) {
            sender.sendMessage("§6===== §bNiceSetSpawn v" + Constants.PLUGIN_VERSION + " §6=====");
            Util.urlHoverClick(sender, "§bSpigotMC Page:", "Visit the SpigotMC page!", Constants.URL_SPIGOTMC);
            Util.urlHoverClick(sender, "§bGithub Page:", "Visit the Github page!", Constants.URL_GITHUB);
            Util.urlHoverClick(sender, "§bDiscord:", "Visit the Discord!", Constants.URL_DISCORD);
        } else {
            sender.sendMessage(EnumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
        }
    }

    private void showHelp(CommandSender sender, Command command) {
        if (Util.hasPermission(sender, Constants.PERMISSION_HELP)) {
            sender.sendMessage("§6===== §bNiceSetSpawn §6=====");
            sender.sendMessage("§b/spawn §7- " + Util.color(sender, EnumLang.MESSAGES_TELEPORT_SPAWN_MESSAGE.getConfigValue(sender)));
            sender.sendMessage("§b/setspawn §7- " + Util.color(sender, EnumLang.MESSAGES_TELEPORT_SETSPAWN_MESSAGE.getConfigValue(sender)));
            sender.sendMessage("§b/" + command.getName() + " | /" + command.getName() + " help §7- " + Util.color(sender, EnumLang.MESSAGES_COMMANDS_LIST_MESSAGE.getConfigValue(sender)));
            sender.sendMessage("§b/" + command.getName() + " info §7- " + Util.color(sender, EnumLang.MESSAGES_PLUGIN_INFO_MESSAGE.getConfigValue(sender)));
            sender.sendMessage("§b/" + command.getName() + " setdelay [" + Util.color(sender, EnumLang.MESSAGES_SECONDS_MESSAGE.getConfigValue(sender)) + "] §7- " + Util.color(sender, EnumLang.MESSAGES_SETSPAWN_DELAY_MESSAGE.getConfigValue(sender)));
            sender.sendMessage("§b/" + command.getName() + " reload §7- " + Util.color(sender, EnumLang.MESSAGES_RELOAD_CONFIG_MESSAGE.getConfigValue(sender)));
        } else {
            sender.sendMessage(EnumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
        }
    }

    private void reloadCommand(@NotNull CommandSender sender) {
        if (Util.hasPermission(sender, Constants.PERMISSION_RELOAD)) {
            Main.getInstance().reloadConfigFile();
            Main.getInstance().reloadConfigLang();
            sender.sendMessage(Util.color(sender, EnumLang.MESSAGES_CONFIG_RELOADED.getConfigValue(sender)));
        } else {
            sender.sendMessage(EnumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
        }
    }

    private void setDelayCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (Util.hasPermission(sender, Constants.PERMISSION_SETDELAY)) {
            if (args.length == 1) {
                try {
                    sender.sendMessage(EnumLang.MESSAGES_CURRENTLY_TELEPORT_DELAY_MESSAGE.getConfigValue(sender).
                            replaceAll("%seconds%", Main.getConfiguration().
                                    getInt(EnumConfig.TELEPORT_DELAY_IN_SECONDS.getPath()) + ""));
                } catch (Exception e) {
                    sender.sendMessage(EnumLang.MESSAGES_ONLY_NUMBER_MESSAGE.getConfigValue(sender));
                    Main.getConfiguration().set(EnumConfig.TELEPORT_DELAY_IN_SECONDS.getPath(), 0);
                    Main.getInstance().saveConfig();
                }
            } else {
                try {
                    Main.getConfiguration().set(EnumConfig.TELEPORT_DELAY_IN_SECONDS.getPath(), Integer.parseInt(args[1]));
                    Main.getInstance().saveConfig();

                    sender.sendMessage(EnumLang.MESSAGES_CHANGED_TELEPORT_DELAY_MESSAGE.getConfigValue(sender).
                            replaceAll("%seconds%", Main.getConfiguration().
                                    getInt(EnumConfig.TELEPORT_DELAY_IN_SECONDS.getPath()) + ""));

                } catch (Exception e) {
                    sender.sendMessage(EnumLang.MESSAGES_ONLY_NUMBER_MESSAGE.getConfigValue(sender));
                    Main.getConfiguration().set(EnumConfig.TELEPORT_DELAY_IN_SECONDS.getPath(), 0);
                    Main.getInstance().saveConfig();
                }
            }
        } else {
            sender.sendMessage(EnumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
        }
    }

    // HasMap<PERMISSION, COMMAND>
    Map<String, String> permsCommands = new HashMap<String, String>() {{
        put(Constants.PERMISSION_HELP, Constants.COMMAND_HELP);
        put(Constants.COMMAND_INFO, Constants.COMMAND_INFO);
        put(Constants.PERMISSION_SETDELAY, Constants.COMMAND_SETDELAY);
        put(Constants.PERMISSION_RELOAD, Constants.COMMAND_RELOAD);
        put(Constants.PERMISSION_SHOWEFFECTS, Constants.COMMAND_SHOWEFFECTS);
    }};

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> results = new ArrayList<>();

            for (String perms : permsCommands.keySet()) {
                if (Util.hasPermission(sender, perms) && permsCommands.get(perms).startsWith(args[0])) {
                    results.add(permsCommands.get(perms));
                }
            }

            return results;
        }
        return Collections.emptyList();
    }
}
