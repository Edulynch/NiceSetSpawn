package me.edulynch.nicesetspawn.helpers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ConfigWrapper {

    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;
    private final String folderName;
    private final String fileName;

    public ConfigWrapper(final JavaPlugin instance, final String folderName,
                         final String fileName) {
        this.plugin = instance;
        this.folderName = folderName;
        this.fileName = fileName;
    }

    public void createNewFile(final String message, final String header) {
        reloadConfig();
        saveConfig();
        loadConfig(header);

        if (message != null) {
            plugin.getLogger().info(message);
        }
    }

    public FileConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    public void loadConfig(final String header) {
        config.options().header(header);
        config.options().copyDefaults(true);
        saveConfig();
    }

    public void reloadConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder() + folderName, fileName);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig() {
        if (config == null || configFile == null) {
            return;
        }
        try {
            getConfig().save(configFile);
        } catch (final IOException ex) {
            plugin.getLogger().log(Level.SEVERE,
                    "Could not save config to " + configFile, ex);
        }
    }

    // CHECK VERSION

    public void checkVersion() {
        if (plugin.getDataFolder().exists() && configFile.exists()) {
            File file1 = configFile;
            File file2 = new File(plugin.getDataFolder() + folderName, "old" + fileName);

            if (file2.exists()) {
                boolean deletedSuccess = file2.delete();
                if (deletedSuccess) {
                    plugin.getLogger().warning("DELETED FILE: " + file2.getName());
                } else {
                    plugin.getLogger().severe("ERROR DELETING FILE: " + file2.getName());
                }
            }

            boolean renamedSuccess = file1.renameTo(file2);
            if (renamedSuccess) {
                plugin.getLogger().warning("SUCCESSFUL RENAMING FILE: " + file2.getName());
            } else {
                plugin.getLogger().severe("ERROR RENAMING FILE: " + file1.getName());
            }

        }

        plugin.getLogger().warning("PLEASE CONSIDER MODIFYING THE NEW CONFIG.");

    }

    public void convertOldConfig() {
        File file = new File(plugin.getDataFolder() + folderName, "old" + fileName);

        if (plugin.getDataFolder().exists() && file.exists() && (plugin.getConfig().getString("spawn.world") == null)) {
            YamlConfiguration oldconfig = YamlConfiguration.loadConfiguration(file);

            if (oldconfig.getString("spawn.world") != null) {
                plugin.getConfig().set("spawn.world", oldconfig.getString("spawn.world"));
                plugin.getConfig().set("spawn.x", oldconfig.getDouble("spawn.x"));
                plugin.getConfig().set("spawn.y", oldconfig.getDouble("spawn.y"));
                plugin.getConfig().set("spawn.z", oldconfig.getDouble("spawn.z"));
                plugin.getConfig().set("spawn.yaw", (float) oldconfig.getDouble("spawn.yaw"));
                plugin.getConfig().set("spawn.pitch", (float) oldconfig.getDouble("spawn.pitch"));

                plugin.saveConfig();
            }
        }
    }

}
