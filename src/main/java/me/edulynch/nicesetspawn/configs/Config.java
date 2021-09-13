package me.edulynch.nicesetspawn.configs;

import org.bukkit.plugin.Plugin;

public class Config {

    private Plugin pl;

    public Config(Plugin pl) {
        this.pl = pl;
    }

    public void createConfig() {
        pl.getConfig().options().copyDefaults(true);
        pl.saveDefaultConfig();
    }

//    public void checkVersion() {
//        File file1 = new File(pl.getDataFolder(), "config.yml");
//        YamlConfiguration config1 = YamlConfiguration.loadConfiguration(file1);
//
//        if (pl.getDataFolder().exists() && file1.exists()) {
//            String configVersion = config1.getString("config-version");
//            if (configVersion != null) {
//                if (!configVersion.equalsIgnoreCase(Main.getConfigVersion())) {
//                    File file2 = new File(pl.getDataFolder(), "oldconfig.yml");
//
//                    boolean successRename = file1.renameTo(file2);
//                    if (!successRename) {
//                        pl.getLogger().severe("ERROR: RENAMING OLD CONFIG.YML");
//                    }
//                    try {
//                        boolean successCreateNewFile = file2.createNewFile();
//                        if (!successCreateNewFile) {
//                            pl.getLogger().severe("ERROR: GENERATING UPDATED CONFIG.YML");
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    pl.getLogger().warning("OLD CONFIG RENAMED!");
//                    pl.getLogger().warning("NEW CONFIG GENERATED!");
//                    pl.getLogger().warning("PLEASE CONSIDER MODIFYING THE NEW CONFIG.");
//                }
//            }
//        }
//    }

//    public void convertOldConfig() {
//        File file = new File(pl.getDataFolder(), "oldconfig.yml");
//
//        if (pl.getDataFolder().exists() && file.exists() && (pl.getConfig().getString("spawn.world") == null)) {
//            YamlConfiguration oldconfig = YamlConfiguration.loadConfiguration(file);
//
//            if (oldconfig.getString("spawn.world") != null) {
//                pl.getConfig().set("spawn.world", oldconfig.getString("spawn.world"));
//                pl.getConfig().set("spawn.x", oldconfig.getDouble("spawn.x"));
//                pl.getConfig().set("spawn.y", oldconfig.getDouble("spawn.y"));
//                pl.getConfig().set("spawn.z", oldconfig.getDouble("spawn.z"));
//                pl.getConfig().set("spawn.yaw", (float) oldconfig.getDouble("spawn.yaw"));
//                pl.getConfig().set("spawn.pitch", (float) oldconfig.getDouble("spawn.pitch"));
//
//                pl.saveConfig();
//            }
//        }
//    }

}
