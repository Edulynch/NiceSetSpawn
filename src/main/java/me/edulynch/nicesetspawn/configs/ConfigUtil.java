package me.edulynch.nicesetspawn.configs;

import me.edulynch.nicesetspawn.Main;
import me.edulynch.nicesetspawn.utils.Utils;

public class ConfigUtil {

    public static String getNoPermission() {
        return Utils.color(Main.getConfiguration().getString("messages.no-permission"));
    }

    public static String getPlayerNotFound() {
        return Utils.color(Main.getConfiguration().getString("messages.player-not-found"));
    }

    public static String getConsoleUseCommand() {
        return Utils.color(Main.getConfiguration().getString("messages.console-use-command"));
    }

}
