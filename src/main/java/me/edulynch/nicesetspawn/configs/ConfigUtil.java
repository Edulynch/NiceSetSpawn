package me.edulynch.nicesetspawn.configs;

import me.edulynch.nicesetspawn.utils.Utils;

public class ConfigUtil {

    public static String getNoPermission() {
        return Utils.color("&cNo tienes permiso para usar ese comando.");
    }

    public static String getPlayerNotFound() {
        return Utils.color("&cJugador no encontrado.");
    }

    public static String getConsoleUseCommand() {
        return Utils.color("&cComando no debe ser ejecutado desde la consola.");
    }

}
