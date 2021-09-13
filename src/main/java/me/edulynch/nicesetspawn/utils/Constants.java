package me.edulynch.nicesetspawn.utils;

import me.edulynch.nicesetspawn.Main;

public class Constants {

    //########################### PLUGIN ############################

    public final static String PLUGIN_NAME = "NiceSetSpawn";
    public final static String PLUGIN_VERSION = "1.0";
    public final static String PLUGIN_AUTHOR = "Edulynch";

    //########################## MESSAGES ###########################

    public final static String PREFIX_MESSAGE = Main.getConfiguration().getString("global-message-prefix");

    //######################### PERMISSIONS #########################

    // REMEMBER: Modify plugin.yml for match permissions.

    // PREFIX
    public final static String PREFIX_PERMISSION = "nicesetspawn";
    public final static String PERMISSION_GLOBAL = PREFIX_PERMISSION + ".*";
    public final static String PERMISSION_ALL = PREFIX_PERMISSION + ".all";

    // PERMISSION LIST
    public final static String PERMISSION_HELP = "help";
    public final static String PERMISSION_INFO = "info";
    public final static String PERMISSION_SETSPAWN = "setspawn";
    public final static String PERMISSION_SPAWN = "spawn";
    public final static String PERMISSION_RELOAD = "reload";
    public final static String PERMISSION_SETDELAY = "setdelay";
    public final static String PERMISSION_BYPASSDELAY = "bypassdelay";
    public final static String PERMISSION_BYPASSPVP = "bypasspvp";
    public final static String PERMISSION_TELEPORTOTHERS = "teleportothers";


}
