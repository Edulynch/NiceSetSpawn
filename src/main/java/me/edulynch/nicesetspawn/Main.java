package me.edulynch.nicesetspawn;

import me.edulynch.nicesetspawn.Config.enumConfig;
import me.edulynch.nicesetspawn.Config.enumLang;
import me.edulynch.nicesetspawn.commands.NiceSetSpawnCMD;
import me.edulynch.nicesetspawn.commands.SetSpawnCMD;
import me.edulynch.nicesetspawn.commands.SpawnCMD;
import me.edulynch.nicesetspawn.helpers.ConfigWrapper;
import me.edulynch.nicesetspawn.listeners.*;
import me.edulynch.nicesetspawn.placeholderapi.NSSExpansion;
import me.edulynch.nicesetspawn.utils.Constants;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    @SuppressWarnings("FieldCanBeLocal")
    private ConfigWrapper langWrapper, configWrapper;

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        onPluginLoadMessage();

        loadConfig();
        loadMessagesLang();

        enableLibraries();

        registerListeners();
        registerCommands();

        if (enumConfig.BSTATS_METRICS.getConfigBoolean()) {
            registerMetrics();
        }

    }

    private void enableLibraries() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new NSSExpansion(this).register();
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @SuppressWarnings("unused")
    private void registerMetrics() {
        int pluginId = 12777;
        Metrics metrics = new Metrics(this, pluginId);
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerJoin(), this);
        pm.registerEvents(new PlayerRespawn(), this);
        pm.registerEvents(new EntityDamage(), this);
        pm.registerEvents(new PlayerQuit(), this);
        pm.registerEvents(new EntityDamageByEntity(), this);

    }

    private void onPluginLoadMessage() {
        Logger.getLogger("").info("");
        Logger.getLogger("").info(ChatColor.DARK_AQUA + " _   _  _____ _____ ");
        Logger.getLogger("").info(ChatColor.DARK_AQUA + "| \\ | |/ ____/ ____|");
        Logger.getLogger("").info(ChatColor.DARK_AQUA + "|  \\| | (___| (___  " + "\t" + ChatColor.DARK_GREEN + Constants.PLUGIN_NAME + ChatColor.AQUA + " v" + Constants.PLUGIN_VERSION);
        Logger.getLogger("").info(ChatColor.DARK_AQUA + "| . ` |\\___ \\\\___ \\" + "\t" + ChatColor.DARK_GRAY + "Running on " + getServer().getName());
        Logger.getLogger("").info(ChatColor.DARK_AQUA + "| |\\  |____) |___) |");
        Logger.getLogger("").info(ChatColor.DARK_AQUA + "|_| \\_|_____/_____/ ");
        Logger.getLogger("").info("");
    }

    private void loadConfig() {
        // Load config.yml
        configWrapper = new ConfigWrapper(this, "", "config.yml");
        configWrapper.createNewFile("Loading NiceSetSpawn config.yml", "NiceSetSpawn Config file");

        enumConfig.setFile(configWrapper.getConfig());

        for (final enumConfig value : enumConfig.values()) {
            if (value.getValueType() == String.class) {
                configWrapper.getConfig().addDefault(value.getPath(), value.getString());
            } else if (value.getValueType() == Boolean.class) {
                configWrapper.getConfig().addDefault(value.getPath(), value.getBoolean());
            } else if (value.getValueType() == Integer.class) {
                configWrapper.getConfig().addDefault(value.getPath(), value.getInt());
            } else if (value.getValueType() == List.class) {
                configWrapper.getConfig().addDefault(value.getPath(), value.getStringList());
            }
        }

        configWrapper.getConfig().options().copyDefaults(true);
        configWrapper.saveConfig();

        String version = enumConfig.CONFIG_VERSION.getConfigString(null);
        if (!version.equalsIgnoreCase(Constants.PLUGIN_VERSION)) {
            configWrapper.checkVersion();
            configWrapper.convertOldConfig();
            loadConfig();
        }

    }

    private void loadMessagesLang() {
        // Load messages.yml
        langWrapper = new ConfigWrapper(this, File.separator + "Languages", "messages-" + enumConfig.TRANSLATE_MESSAGES.getConfigString(null) + ".yml");
        langWrapper.createNewFile("Loading NiceSetSpawn messages.yml", "NiceSetSpawn Messages file");

        enumLang.setFile(langWrapper.getConfig());

        for (final enumLang value : enumLang.values()) {
            langWrapper.getConfig().addDefault(value.getPath(), value.getDefault());
        }

        langWrapper.getConfig().options().copyDefaults(true);
        langWrapper.saveConfig();
    }

    private void registerCommands() {
        SetSpawnCMD setSpawnCmd = new SetSpawnCMD();
        PluginCommand setSpawnPC = getCommand(Constants.COMMAND_SETSPAWN);
        if (setSpawnPC != null) {
            setSpawnPC.setExecutor(setSpawnCmd);
            setSpawnPC.setTabCompleter(setSpawnCmd);
        }

        SpawnCMD spawnCmd = new SpawnCMD();
        PluginCommand spawnPC = getCommand(Constants.COMMAND_SPAWN);
        if (spawnPC != null) {
            spawnPC.setExecutor(spawnCmd);
            spawnPC.setTabCompleter(spawnCmd);
        }

        NiceSetSpawnCMD niceSetSpawnCmd = new NiceSetSpawnCMD();
        PluginCommand niceSetSpawnPC = getCommand(Constants.COMMAND_NSS);
        if (niceSetSpawnPC != null) {
            niceSetSpawnPC.setExecutor(niceSetSpawnCmd);
            niceSetSpawnPC.setTabCompleter(niceSetSpawnCmd);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled!");
    }

    public static FileConfiguration getConfiguration() {
        return getInstance().getConfig();
    }

    public void reloadConfigLang() {
        loadMessagesLang();
    }

    public void reloadConfigFile() {
        loadConfig();
    }

    public static Main getInstance() {
        return instance;
    }

}
