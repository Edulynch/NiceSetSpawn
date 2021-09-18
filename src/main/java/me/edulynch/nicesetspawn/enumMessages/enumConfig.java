package me.edulynch.nicesetspawn.enumMessages;

import me.edulynch.nicesetspawn.utils.Constants;
import me.edulynch.nicesetspawn.utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum enumConfig {
    /**
     * translate-messages
     */
    TRANSLATE_MESSAGES("translate-messages", "en_US", String.class),
    /**
     * teleport-to-spawn-on.first-join
     */
    TELEPORT_TO_SPAWN_ON_FIRST_JOIN("teleport-to-spawn-on.first-join", true, Boolean.class),
    /**
     * teleport-to-spawn-on.join
     */
    TELEPORT_TO_SPAWN_ON_JOIN("teleport-to-spawn-on.join", true, Boolean.class),
    /**
     * teleport-to-spawn-on.respawn
     */
    TELEPORT_TO_SPAWN_ON_RESPAWN("teleport-to-spawn-on.respawn", true, Boolean.class),
    /**
     * teleport-to-spawn-on.void-fall
     */
    TELEPORT_TO_SPAWN_ON_VOID_FALL("teleport-to-spawn-on.void-fall", true, Boolean.class),
    /**
     * teleport-delay-in-seconds
     */
    TELEPORT_DELAY_IN_SECONDS("teleport-delay-in-seconds", 0, Integer.class),
    /**
     * check-version.enabled
     */
    CHECK_VERSION_ENABLED("check-version.enabled", true, Boolean.class),
    /**
     * disable-spawn-command-in-pvp.enabled
     */
    DISABLE_SPAWN_COMMAND_IN_PVP_ENABLED("disable-spawn-command-in-pvp.enabled", true, Boolean.class),
    /**
     * options.set-gamemode-on-join.enabled
     */
    OPTIONS_SET_GAMEMODE_ON_JOIN_ENABLED("options.set-gamemode-on-join.enabled", true, Boolean.class),
    /**
     * options.set-gamemode-on-join.gamemode
     */
    OPTIONS_SET_GAMEMODE_ON_JOIN_GAMEMODE("options.set-gamemode-on-join.gamemode", 0, Integer.class),
    /**
     * options.set-fly-on-join.enabled
     */
    OPTIONS_SET_FLY_ON_JOIN_ENABLED("options.set-fly-on-join.enabled", true, Boolean.class),
    /**
     * options.set-max-health-on-join
     */
    OPTIONS_SET_MAX_HEALTH_ON_JOIN("options.set-max-health-on-join", true, Boolean.class),
    /**
     * options.set-max-food-level-on-join
     */
    OPTIONS_SET_MAX_FOOD_LEVEL_ON_JOIN("options.set-max-food-level-on-join", true, Boolean.class),
    /**
     * spawn-command.message-enabled
     */
    SPAWN_COMMAND_MESSAGE_ENABLED("spawn-command.message-enabled", true, Boolean.class),
    /**
     * spawn-command.need-permission
     */
    SPAWN_COMMAND_NEED_PERMISSION("spawn-command.need-permission", true, Boolean.class),
    /**
     * broadcast.player-join.enabled
     */
    BROADCAST_PLAYER_JOIN_ENABLED("broadcast.player-join.enabled", true, Boolean.class),
    /**
     * broadcast.player-quit.enabled
     */
    BROADCAST_PLAYER_QUIT_ENABLED("broadcast.player-quit.enabled", true, Boolean.class),
    /**
     * broadcast.first-join.enabled
     */
    BROADCAST_FIRST_JOIN_ENABLED("broadcast.first-join.enabled", true, Boolean.class),
    /**
     * welcome-message.player-join.enabled
     */
    WELCOME_MESSAGE_PLAYER_JOIN_ENABLED("welcome-message.player-join.enabled", true, Boolean.class),
    /**
     * welcome-message.player-join.text
     */
    WELCOME_MESSAGE_PLAYER_JOIN_TEXT("welcome-message.player-join.text",
            new ArrayList<>(Arrays.asList("&6Welcome, {0}", "&6Enjoy the Server!")),
            List.class),

    /**
     * welcome-message.first-join.enabled
     */
    WELCOME_MESSAGE_FIRST_JOIN_ENABLED("welcome-message.first-join.enabled", true, Boolean.class),

    /**
     * welcome-message.first-join.text
     */
    WELCOME_MESSAGE_FIRST_JOIN_TEXT("welcome-message.first-join.text",
            new ArrayList<>(Arrays.asList("&6Welcome for the first time, {0}!", "&6Enjoy the Server!")),
            List.class),
    /**
     * config-version
     */
    CONFIG_VERSION("config-version", Constants.PLUGIN_VERSION, String.class),
    /**
     * options.disable-damage
     */
    OPTIONS_DISABLE_FALL_DAMAGE("options.disable-fall-damage", true, Boolean.class),
    /**
     * options.disable-hunger-deplete
     */
    OPTIONS_DISABLE_HUNGER_DEPLETE("options.disable-fall-damage", true, Boolean.class),
    /**
     * options.spawn-particle.enabled
     */
    OPTIONS_SPAWN_PARTICLE_ENABLED("options.spawn-particle.enabled", true, Boolean.class),
    /**
     * options.spawn-particle.effect
     */
    OPTIONS_SPAWN_PARTICLE_EFFECT("options.spawn-particle.effect", true, String.class),
    ;

    private final String path;
    private String string;
    private List<String> stringList;
    private boolean bool;
    private int num;
    private static FileConfiguration CONFIG;
    private final Class<?> valueType;

    enumConfig(final String path, final boolean value, Class<?> valueType) {
        this.path = path;
        this.bool = value;
        this.valueType = valueType;
    }

    enumConfig(final String path, final String value, Class<?> valueType) {
        this.path = path;
        this.string = value;
        this.valueType = valueType;
    }

    enumConfig(final String path, final List<String> stringList, Class<?> valueType) {
        this.path = path;
        this.stringList = stringList;
        this.valueType = valueType;
    }

    enumConfig(final String path, final int value, Class<?> valueType) {
        this.path = path;
        this.num = value;
        this.valueType = valueType;
    }

    public String getString() {
        return this.string;
    }

    public List<String> getStringList() {
        return this.stringList;
    }

    public boolean getBoolean() {
        return this.bool;
    }

    public int getInt() {
        return this.num;
    }

    public String getPath() {
        return this.path;
    }

    public Class<?> getValueType() {
        return this.valueType;
    }

    public String getConfigString(final String[] args) {
        String value = Utils.color(CONFIG.getString(this.path, this.string));

        if (args != null) {
            if (args.length == 0) {
                return value;
            }
            for (int i = 0; i < args.length; i++) {
                value = value.replace("{" + i + "}", args[i]);
            }
        }

        return value;
    }

    public List<String> getConfigStringList(final String[] args) {
        List<String> valueList = CONFIG.getStringList(this.path);
        List<String> tempList = new ArrayList<>();

        if (args != null) {
            if (args.length == 0) {
                return valueList;
            }
            for (String value : valueList) {
                for (int i = 0; i < args.length; i++) {
                    value = value.replace("{" + i + "}", args[i]);
                    tempList.add(value);
                }
            }
        }

        return tempList;
    }

    public String getConfigString() {
        return CONFIG.getString(this.path, this.string);
    }

    public boolean getConfigBoolean() {
        return CONFIG.getBoolean(this.path, this.bool);
    }

    public int getConfigInteger() {
        return CONFIG.getInt(this.path, this.num);
    }

    public static void setFile(final FileConfiguration config) {
        CONFIG = config;
    }

}
