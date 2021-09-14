package me.edulynch.nicesetspawn;

import me.edulynch.nicesetspawn.commands.NiceSetSpawnCMD;
import me.edulynch.nicesetspawn.commands.SetSpawnCMD;
import me.edulynch.nicesetspawn.commands.SpawnCMD;
import me.edulynch.nicesetspawn.configs.Config;
import me.edulynch.nicesetspawn.listeners.*;
import me.edulynch.nicesetspawn.placeholderapi.NSSExpansion;
import me.edulynch.nicesetspawn.utils.Constants;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        onPluginLoadMessage();
        initConfigs();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new NSSExpansion(this).register();
            registerListeners();
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        registerCommands();
        registerMetrics();

    }

    private void registerMetrics() {
        int pluginId = 12777; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
        /* Players Online Metric */
        metrics.addCustomChart(new SingleLineChart("players", () -> {
            // (This is useless as there is already a player chart by default.)
            return Bukkit.getOnlinePlayers().size();
        }));
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

    private void initConfigs() {
        Config config = new Config(this);
        config.createConfig();
    }

    private void registerCommands() {
        SetSpawnCMD setSpawnCmd = new SetSpawnCMD();
        PluginCommand setSpawnPC = getCommand("setspawn");
        if (setSpawnPC != null) {
            setSpawnPC.setExecutor(setSpawnCmd);
            setSpawnPC.setTabCompleter(setSpawnCmd);
        }

        SpawnCMD spawnCmd = new SpawnCMD();
        PluginCommand spawnPC = getCommand("spawn");
        if (spawnPC != null) {
            spawnPC.setExecutor(spawnCmd);
            spawnPC.setTabCompleter(spawnCmd);
        }

        NiceSetSpawnCMD niceSetSpawnCmd = new NiceSetSpawnCMD();
        PluginCommand niceSetSpawnPC = getCommand("nss");
        if (niceSetSpawnPC != null) {
            niceSetSpawnPC.setExecutor(niceSetSpawnCmd);
            niceSetSpawnPC.setTabCompleter(niceSetSpawnCmd);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FileConfiguration getConfiguration() {
        return getInstance().getConfig();
    }

    public static Main getInstance() {
        return instance;
    }

}
