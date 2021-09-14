package me.edulynch.nicesetspawn.commands;

import me.edulynch.nicesetspawn.Main;
import me.edulynch.nicesetspawn.configs.ConfigUtil;
import me.edulynch.nicesetspawn.interfaces.CommandTab;
import me.edulynch.nicesetspawn.utils.Constants;
import me.edulynch.nicesetspawn.utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
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
            TextComponent textComponent;
//            textComponent = new TextComponent("https://dev.bukkit.org/projects/nicesetspawn/");
//            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://dev.bukkit.org/projects/nicesetspawn/"));
//            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Visit the Bukkit page!")));
//            sender.sendMessage("§bBukkit Page:");
//            sender.spigot().sendMessage(textComponent);
//            textComponent = new TextComponent("https://www.spigotmc.org/");
//            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/"));
//            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Visit the SpigotMC page!")));
//            sender.sendMessage("§bSpigotMC Page:");
//            sender.spigot().sendMessage(textComponent);
            textComponent = new TextComponent("https://www.github.com/Edulync/NiceSetSpawn/");
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.github.com/Edulync/NiceSetSpawn/"));
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Visit the Github page!")));
            sender.sendMessage("§bGithub Page:");
            sender.spigot().sendMessage(textComponent);
            textComponent = new TextComponent("https://discord.gg/NSt6SrHC");
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/NSt6SrHC"));
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Visit the Discord!")));
            sender.sendMessage("§bDiscord:");
            sender.spigot().sendMessage(textComponent);
        } else {
            sender.sendMessage(ConfigUtil.getNoPermission());
        }
    }

    private void showHelp(CommandSender sender, Command command) {
        if (Utils.hasPermission(sender, Constants.PERMISSION_HELP)) {
            sender.sendMessage("§6===== §bNiceSetSpawn §6=====");
            sender.sendMessage("§b/spawn §7- " + Utils.color(Main.getConfiguration().getString("messages.teleport-spawn-message"), false));
            sender.sendMessage("§b/setspawn §7- " + Utils.color(Main.getConfiguration().getString("messages.teleport-setspawn-message"), false));
            sender.sendMessage("§b/" + command.getName() + " | /" + command.getName() + " help §7- "
                    + Utils.color(Main.getConfiguration().getString("messages.commands-list-message"), false));
            sender.sendMessage("§b/" + command.getName() + " info §7- "
                    + Utils.color(Main.getConfiguration().getString("messages.plugin-info-message"), false));
            sender.sendMessage("§b/" + command.getName() + " setdelay ["
                    + Utils.color(Main.getConfiguration().getString("messages.seconds-message"), false) + "] §7- "
                    + Utils.color(Main.getConfiguration().getString("messages.setspawn-delay-message"), false));
            sender.sendMessage("§b/" + command.getName() + " reload §7- "
                    + Utils.color(Main.getConfiguration().getString("messages.reload-config-message"), false));
        } else {
            sender.sendMessage(ConfigUtil.getNoPermission());
        }
    }

    private void reloadCommand(@NotNull CommandSender sender) {
        if (Utils.hasPermission(sender, Constants.PERMISSION_RELOAD)) {
            Main.getInstance().reloadConfig();
            sender.sendMessage(Utils.color(Main.getConfiguration().getString("messages.config-reloaded")));
        } else {
            sender.sendMessage(ConfigUtil.getNoPermission());
        }
    }

    private void setDelayCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        String delayTeleportString;
        int delayTeleportInt;
        if (Utils.hasPermission(sender, Constants.PERMISSION_SETDELAY)) {
            if (args.length == 1) {
                try {
                    delayTeleportString = Main.getConfiguration().getString("teleport-delay-in-seconds");
                    delayTeleportInt = Integer.parseInt(delayTeleportString != null ? delayTeleportString : "0");

                    sender.sendMessage(Utils.color(Main.getConfiguration().getString("messages.currently-teleport-delay-message")).
                            replaceAll("%seconds%", delayTeleportInt + ""));

                } catch (Exception e) {
                    Main.getConfiguration().set("teleport-delay-in-seconds", 0);
                    Main.getInstance().saveConfig();
                }
            } else {
                try {
                    Main.getConfiguration().set("teleport-delay-in-seconds", Integer.parseInt(args[1]));
                    Main.getInstance().saveConfig();

                    delayTeleportString = Main.getConfiguration().getString("teleport-delay-in-seconds");
                    delayTeleportInt = Integer.parseInt(delayTeleportString != null ? delayTeleportString : "0");


                    sender.sendMessage(Utils.color(Main.getConfiguration().getString("messages.changed-teleport-delay-message")).
                            replaceAll("%seconds%", delayTeleportInt + ""));

                } catch (Exception e) {
                    sender.sendMessage(Utils.color(Main.getConfiguration().getString("messages.only-number-message")));
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
