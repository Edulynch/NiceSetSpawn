package me.edulynch.nicesetspawn.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.edulynch.nicesetspawn.config.EnumLang;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MethodsUtils {

    public static TextComponent textComponent;

    public static String color(CommandSender sender, String message) {
        if (sender != null) {
            if (verifyIfIsConsoleColor(sender)) {
                return ChatColor.translateAlternateColorCodes('&', message);
            } else {
                Player player = (Player) sender;
                return ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, message));
            }
        } else {
            return ChatColor.translateAlternateColorCodes('&', message);
        }
    }

    public static String color(Player player, String message) {
        if (player != null) {
            return ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, message));
        } else {
            return ChatColor.translateAlternateColorCodes('&', message);
        }
    }

    public static boolean verifyIfIsConsole(CommandSender sender) {
        if (sender instanceof Player) {
            return false;
        } else {
            String onlyPlayer = EnumLang.MESSAGES_CONSOLE_USE_COMMAND.getConfigValue(sender);
            String onlyPlayerColor = onlyPlayer.replaceAll("&", "§");
            sender.sendMessage(onlyPlayerColor);
            return true;
        }
    }

    public static boolean verifyIfIsConsoleColor(CommandSender sender) {
        return !(sender instanceof Player);
    }

    public static boolean hasPermission(Player player, String permission) {
        return player.hasPermission(PluginConstants.PREFIX_PERMISSION + "." + permission)
                || player.hasPermission(PluginConstants.PERMISSION_GLOBAL)
                || player.hasPermission(PluginConstants.PERMISSION_ALL);
    }

    public static boolean hasPermission(CommandSender sender, String permission) {
        return sender.hasPermission(PluginConstants.PREFIX_PERMISSION + "." + permission)
                || sender.hasPermission(PluginConstants.PERMISSION_GLOBAL)
                || sender.hasPermission(PluginConstants.PERMISSION_ALL);
    }

    public static void urlHoverClick(CommandSender sender, String messageChat, String hoverText, String URL) {
        textComponent = new TextComponent(URL);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, URL));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverText)));
        sender.sendMessage(messageChat);
        sender.spigot().sendMessage(textComponent);
    }

}
