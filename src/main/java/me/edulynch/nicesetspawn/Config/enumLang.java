package me.edulynch.nicesetspawn.Config;

import me.edulynch.nicesetspawn.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public enum enumLang {
    /**
     * check-version.warning-message
     */
    CHECK_VERSION_WARNING_MESSAGE("check-version.warning-message", "&bNew update available on the official page."),
    /**
     * check-version.error-message
     */
    CHECK_VERSION_ERROR_MESSAGE("check-version.error-message", "&cError Checking for new Version."),
    /**
     * disable-spawn-command-in-pvp.message
     */
    DISABLE_SPAWN_COMMAND_IN_PVP_MESSAGE("check-version.error-message", "&cYou can not go to spawn when you are in PvP."),
    /**
     * options.set-fly-on-join.message
     */
    OPTIONS_SET_FLY_ON_JOIN_MESSAGE("options.set-fly-on-join.message", "&bYou can fly now"),
    /**
     * spawn-command.message
     */
    SPAWN_COMMAND_MESSAGE("spawn-command.message", "&eTeleported to spawn."),
    /**
     * messages.no-permission
     */
    MESSAGES_NO_PERMISSION("messages.no-permission", "&cYou do not have permission to use this command."),
    /**
     * messages.teleport-spawn-message
     */
    MESSAGES_TELEPORT_SPAWN_MESSAGE("messages.teleport-spawn-message", "&6Teleport to spawn."),
    /**
     * messages.teleport-setspawn-message
     */
    MESSAGES_TELEPORT_SETSPAWN_MESSAGE("messages.teleport-setspawn-message", "&6Set spawn."),
    /**
     * messages.commands-list-message
     */
    MESSAGES_COMMANDS_LIST_MESSAGE("messages.commands-list-message", "&6Commands list."),
    /**
     * messages.plugin-info-message
     */
    MESSAGES_PLUGIN_INFO_MESSAGE("messages.plugin-info-message", "&6Plugin info."),
    /**
     * messages.seconds-message
     */
    MESSAGES_SECONDS_MESSAGE("messages.seconds-message", "seconds"),
    /**
     * messages.setspawn-delay-message
     */
    MESSAGES_SETSPAWN_DELAY_MESSAGE("messages.setspawn-delay-message", "&6Set spawn delay. 0 = no delay"),
    /**
     * messages.reload-config-message
     */
    MESSAGES_RELOAD_CONFIG_MESSAGE("messages.reload-config-message", "&6Reload config."),
    /**
     * messages.teleported-other-player
     */
    MESSAGES_TELEPORTED_OTHER_PLAYER("messages.teleported-other-player", "&b%nss_player_name% teleported to spawn."),
    /**
     * messages.spawn-not-set
     */
    MESSAGES_SPAWN_NOT_SET("messages.spawn-not-set", "&cSpawn has not yet been set."),
    /**
     * messages.player-not-found
     */
    MESSAGES_PLAYER_NOT_FOUND("messages.player-not-found", "&cPlayer not found."),
    /**
     * messages.spawn-successfully-set
     */
    MESSAGES_SPAWN_SUCCESSFULLY_SET("messages.spawn-successfully-set", "&bSpawn successfully set."),
    /**
     * messages.config-reloaded
     */
    MESSAGES_CONFIG_RELOADED("messages.config-reloaded", "&aConfig reloaded."),
    /**
     * messages.console-use-command
     */
    MESSAGES_CONSOLE_USE_COMMAND("messages.console-use-command", "&cOnly players can run this command."),
    /**
     * messages.teleport-delay
     */
    MESSAGES_TELEPORT_DELAY("messages.teleport-delay", "&6Teleportation will start in &b%nss_spawn_delay% &6seconds. &cDo not move."),
    /**
     * messages.player-move
     */
    MESSAGES_PLAYER_MOVE("messages.player-move", "&cTeleportation canceled due to movement."),
    /**
     * messages.void-fall
     */
    MESSAGES_VOID_FALL("messages.void-fall", "&cYou have been teleported back to spawn!."),
    /**
     * messages.currently-teleport-delay-message
     */
    MESSAGES_CURRENTLY_TELEPORT_DELAY_MESSAGE("messages.currently-teleport-delay-message", "&bCurrently teleport delay: &3%seconds% &bseconds."),
    /**
     * messages.changed-teleport-delay-message
     */
    MESSAGES_CHANGED_TELEPORT_DELAY_MESSAGE("messages.changed-teleport-delay-message", "&bTeleport delay changed to &3%seconds% &bseconds."),
    /**
     * messages.only-number-message
     */
    MESSAGES_ONLY_NUMBER_MESSAGE("messages.only-number-message", "&cYou can only use numbers."),
    /**
     * broadcast.player-join.message
     */
    BROADCAST_PLAYER_JOIN_MESSAGE("broadcast.player-join.message", "&e&l%nss_player_name% &ejoined the server!"),
    /**
     * broadcast.player-quit.message
     */
    BROADCAST_PLAYER_QUIT_MESSAGE("broadcast.player-quit.message", "&c&l%nss_player_name% &cleft the server!"),
    /**
     * broadcast.first-join.message
     */
    BROADCAST_FIRST_JOIN_MESSAGE("broadcast.first-join.message", "&e&l%nss_player_name% &ejoined the server for the first time!"),
    ;

    private final String path;
    private final String def;
    private static FileConfiguration LANG;

    enumLang(final String path, final String start) {
        this.path = path;
        this.def = start;
    }

    public static void setFile(final FileConfiguration config) {
        LANG = config;
    }

    public String getDefault() {
        return this.def;
    }

    public String getPath() {
        return this.path;
    }

    public String getConfigValue(CommandSender sender) {
        return Utils.color(sender, LANG.getString(this.path, this.def));
    }

    public String getConfigValue(Player player) {
        return Utils.color(player, LANG.getString(this.path, this.def));
    }
}
