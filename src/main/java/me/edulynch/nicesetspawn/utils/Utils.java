package me.edulynch.nicesetspawn.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static TextComponent textComponent;

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> color(List<String> messageList) {
        List<String> stringList = new ArrayList<>();
        for (String message : messageList) {
            stringList.add(ChatColor.translateAlternateColorCodes('&', message));
        }
        return stringList;
    }

    public static String colorPapi(String message, Player player) {
        String preMessage = ChatColor.translateAlternateColorCodes('&', message);
        return PlaceholderAPI.setPlaceholders(player, preMessage);
    }

    public static boolean verifyIfIsAPlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigUtil.getConsoleUseCommand());
            return true;
        } else {
            return false;
        }
    }

    public static boolean hasPermission(Player player, String permission) {
        return player.hasPermission(Constants.PREFIX_PERMISSION + "." + permission)
                || player.hasPermission(Constants.PERMISSION_GLOBAL)
                || player.hasPermission(Constants.PERMISSION_ALL);
    }

    public static boolean hasPermission(CommandSender sender, String permission) {
        return sender.hasPermission(Constants.PREFIX_PERMISSION + "." + permission)
                || sender.hasPermission(Constants.PERMISSION_GLOBAL)
                || sender.hasPermission(Constants.PERMISSION_ALL);
    }

    public static void urlHoverClick(CommandSender sender, String messageChat, String hoverText, String URL) {
        textComponent = new TextComponent(URL);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, URL));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverText)));
        sender.sendMessage(messageChat);
        sender.spigot().sendMessage(textComponent);
    }

}
