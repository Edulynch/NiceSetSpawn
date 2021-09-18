package me.edulynch.nicesetspawn.utils;

import me.edulynch.nicesetspawn.enumMessages.enumLang;

public class ConfigUtil {

    public static String getNoPermission() {
        return enumLang.MESSAGES_NO_PERMISSION.getConfigValue(new String[]{});
    }

    public static String getPlayerNotFound() {
        return enumLang.MESSAGES_PLAYER_NOT_FOUND.getConfigValue(new String[]{});
    }

    public static String getConsoleUseCommand() {
        return enumLang.MESSAGES_CONSOLE_USE_COMMAND.getConfigValue(new String[]{});
    }

}
