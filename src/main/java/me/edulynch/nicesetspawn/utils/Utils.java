package me.edulynch.nicesetspawn.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.edulynch.nicesetspawn.configs.ConfigUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', Constants.PREFIX_MESSAGE + message);
    }

    public static String color(String message, boolean prefix) {
        return ChatColor.translateAlternateColorCodes('&', prefix ? Constants.PREFIX_MESSAGE != null ? Constants.PREFIX_MESSAGE : "" : "" + message);
    }

    public static String colorPapi(String message, Player player) {
        String preMessage = ChatColor.translateAlternateColorCodes('&', Constants.PREFIX_MESSAGE + message);
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

}
