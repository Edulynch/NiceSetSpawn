package me.edulynch.nicesetspawn.commands;

import me.edulynch.nicesetspawn.config.EnumConfig;
import me.edulynch.nicesetspawn.config.EnumLang;
import me.edulynch.nicesetspawn.Main;
import me.edulynch.nicesetspawn.utils.SpawnUtils;
import me.edulynch.nicesetspawn.helpers.SpawnEffect;
import me.edulynch.nicesetspawn.interfaces.CommandTab;
import me.edulynch.nicesetspawn.utils.PluginConstants;
import me.edulynch.nicesetspawn.utils.MethodsUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class NiceSetSpawnCMD implements CommandTab {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase(PluginConstants.COMMAND_HELP)) {
            showHelp(sender, command);
        } else if (args[0].equalsIgnoreCase(PluginConstants.COMMAND_RELOAD)) {
            reloadCommand(sender);
        } else if (args[0].equalsIgnoreCase(PluginConstants.COMMAND_SETDELAY)) {
            setDelayCommand(sender, args);
        } else if (args[0].equalsIgnoreCase(PluginConstants.COMMAND_INFO)) {
            showInfo(sender);
        } else if (args[0].equalsIgnoreCase(PluginConstants.COMMAND_SHOWEFFECTS)) {
            if (MethodsUtils.verifyIfIsConsole(sender)) return true;
            testSpawnEffects(sender);
        }
        return true;
    }

    private void testSpawnEffects(CommandSender sender) {
        if (MethodsUtils.hasPermission(sender, PluginConstants.PERMISSION_SHOWEFFECTS)) {
            Player player = (Player) sender;
            try {
                Location location = SpawnUtils.getLocation();
                SpawnEffect.register(location);
            } catch (Exception e) {
                player.sendMessage(EnumLang.MESSAGES_SPAWN_NOT_SET.getConfigValue(player));
            }
        } else {
            sender.sendMessage(EnumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
        }
    }

    private void showInfo(CommandSender sender) {
        if (MethodsUtils.hasPermission(sender, PluginConstants.PERMISSION_INFO)) {
            sender.sendMessage("§6===== §bNiceSetSpawn v" + PluginConstants.PLUGIN_VERSION + " §6=====");
            MethodsUtils.urlHoverClick(sender, "§bSpigotMC Page:", "Visit the SpigotMC page!", PluginConstants.URL_SPIGOTMC);
            MethodsUtils.urlHoverClick(sender, "§bGithub Page:", "Visit the Github page!", PluginConstants.URL_GITHUB);
            MethodsUtils.urlHoverClick(sender, "§bDiscord:", "Visit the Discord!", PluginConstants.URL_DISCORD);
        } else {
            sender.sendMessage(EnumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
        }
    }

    private void showHelp(CommandSender sender, Command command) {
        if (MethodsUtils.hasPermission(sender, PluginConstants.PERMISSION_HELP)) {
            sender.sendMessage("§6===== §bNiceSetSpawn §6=====");
            sender.sendMessage("§b/spawn §7- " + MethodsUtils.color(sender, EnumLang.MESSAGES_TELEPORT_SPAWN_MESSAGE.getConfigValue(sender)));
            sender.sendMessage("§b/setspawn §7- " + MethodsUtils.color(sender, EnumLang.MESSAGES_TELEPORT_SETSPAWN_MESSAGE.getConfigValue(sender)));
            sender.sendMessage("§b/" + command.getName() + " | /" + command.getName() + " help §7- " + MethodsUtils.color(sender, EnumLang.MESSAGES_COMMANDS_LIST_MESSAGE.getConfigValue(sender)));
            sender.sendMessage("§b/" + command.getName() + " info §7- " + MethodsUtils.color(sender, EnumLang.MESSAGES_PLUGIN_INFO_MESSAGE.getConfigValue(sender)));
            sender.sendMessage("§b/" + command.getName() + " setdelay [" + MethodsUtils.color(sender, EnumLang.MESSAGES_SECONDS_MESSAGE.getConfigValue(sender)) + "] §7- " + MethodsUtils.color(sender, EnumLang.MESSAGES_SETSPAWN_DELAY_MESSAGE.getConfigValue(sender)));
            sender.sendMessage("§b/" + command.getName() + " reload §7- " + MethodsUtils.color(sender, EnumLang.MESSAGES_RELOAD_CONFIG_MESSAGE.getConfigValue(sender)));
        } else {
            sender.sendMessage(EnumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
        }
    }

    private void reloadCommand(@NotNull CommandSender sender) {
        if (MethodsUtils.hasPermission(sender, PluginConstants.PERMISSION_RELOAD)) {
            Main.getInstance().reloadConfigFile();
            Main.getInstance().reloadConfigLang();
            sender.sendMessage(MethodsUtils.color(sender, EnumLang.MESSAGES_CONFIG_RELOADED.getConfigValue(sender)));
        } else {
            sender.sendMessage(EnumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
        }
    }

    private void setDelayCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (MethodsUtils.hasPermission(sender, PluginConstants.PERMISSION_SETDELAY)) {
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
        put(PluginConstants.PERMISSION_HELP, PluginConstants.COMMAND_HELP);
        put(PluginConstants.COMMAND_INFO, PluginConstants.COMMAND_INFO);
        put(PluginConstants.PERMISSION_SETDELAY, PluginConstants.COMMAND_SETDELAY);
        put(PluginConstants.PERMISSION_RELOAD, PluginConstants.COMMAND_RELOAD);
        put(PluginConstants.PERMISSION_SHOWEFFECTS, PluginConstants.COMMAND_SHOWEFFECTS);
    }};

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> results = new ArrayList<>();

            for (String perms : permsCommands.keySet()) {
                if (MethodsUtils.hasPermission(sender, perms) && permsCommands.get(perms).startsWith(args[0])) {
                    results.add(permsCommands.get(perms));
                }
            }

            return results;
        }
        return Collections.emptyList();
    }
}
