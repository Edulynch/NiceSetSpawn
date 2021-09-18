package me.edulynch.nicesetspawn.utils;

public class Constants {

    //########################### PLUGIN ############################

    public final static String PLUGIN_NAME = "NiceSetSpawn";
    public final static String PLUGIN_VERSION = "1.3";
    public final static String PLUGIN_AUTHOR = "Edulynch";

    //############################# URL #############################

    public final static String URL_GITHUBVERSIONTXT = "https://raw.githubusercontent.com/Edulynch/NiceSetSpawn/main/version.txt";
    public final static String URL_SPIGOTMC = "https://www.spigotmc.org/resources/nicesetspawn.96240/";
    public final static String URL_GITHUB = "https://www.github.com/Edulync/NiceSetSpawn/";
    public final static String URL_DISCORD = "https://discord.gg/NSt6SrHC";

    //########################## PLACEHOLDER #########################

    public final static String PLACEHOLDER_SECONDS = "%seconds%";
    public final static String PLACEHOLDER_TARGET = "%target%";

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

    //######################### PERMISSIONS #########################

    public final static String COMMAND_SETSPAWN = PERMISSION_SETSPAWN;
    public final static String COMMAND_SPAWN = PERMISSION_SPAWN;
    public final static String COMMAND_NSS = "nss";

}
