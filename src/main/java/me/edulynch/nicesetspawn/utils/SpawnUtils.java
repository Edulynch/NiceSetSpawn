package me.edulynch.nicesetspawn.utils;

import me.edulynch.nicesetspawn.config.EnumConfig;
import me.edulynch.nicesetspawn.config.EnumLang;
import me.edulynch.nicesetspawn.Main;
import me.edulynch.nicesetspawn.helpers.SpawnEffect;
import me.edulynch.nicesetspawn.models.Delay;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class SpawnUtils {

    public static HashMap<Player, Delay> delay = new HashMap<>();

    public static void setLocation(Location l) {
        World world = l.getWorld();

        if (world != null) {
            Main.getConfiguration().set("spawn.world", l.getWorld().getName());

            Main.getConfiguration().set("spawn.x", l.getX());
            Main.getConfiguration().set("spawn.y", l.getY());
            Main.getConfiguration().set("spawn.z", l.getZ());
            Main.getConfiguration().set("spawn.yaw", l.getYaw());
            Main.getConfiguration().set("spawn.pitch", l.getPitch());

            Main.getInstance().saveConfig();
        } else {
            Main.getInstance().getLogger().warning("ERROR: Trying get Spawn .");
        }
    }

    public static Location getLocation() {
        String worldName = Main.getConfiguration().getString("spawn.world");

        if (worldName == null || worldName.equalsIgnoreCase("")) {
            return null;
        } else {
            World world = Bukkit.getServer().getWorld(worldName);
            double x = Main.getConfiguration().getDouble("spawn.x");
            double y = Main.getConfiguration().getDouble("spawn.y");
            double z = Main.getConfiguration().getDouble("spawn.z");
            float yaw = Main.getConfiguration().getInt("spawn.yaw");
            float pitch = Main.getConfiguration().getInt("spawn.pitch");

            return new Location(world, x, y, z, yaw, pitch);
        }
    }

    public static void spawn(Player player, boolean bypass) {
        if (!bypass) {
            if (delay.containsKey(player) && delay.get(player).getTask().isSync()) {
                delay.get(player).getTask().cancel();
            }

            Location location = player.getLocation();

            delay.put(player, new Delay(new BukkitRunnable() {
                @Override
                public void run() {
                    if (delay.get(player).getTime() >= EnumConfig.TELEPORT_DELAY_IN_SECONDS.getConfigInteger()) {

                        delay.get(player).setTime(1);

                        SpawnUtils.teleport(player);

                        cancel();

                    } else {
                        delay.get(player).setTime(delay.get(player).getTime() + 1);

                        if (delay.get(player).getStartX() != (int) player.getLocation().getX()
                                || delay.get(player).getStartY() != (int) player.getLocation().getY()
                                || delay.get(player).getStartZ() != (int) player.getLocation().getZ()) {

                            delay.get(player).setTime(1);

                            cancel();

                            player.sendMessage(MethodsUtils.color(player, EnumLang.MESSAGES_PLAYER_MOVE.getConfigValue(player)));
                        }
                    }
                }
            }.runTaskTimer(Main.getInstance(), 20L, 20L), (int) location.getX(), (int) location.getY(), (int) location.getZ()));

            player.sendMessage(EnumLang.MESSAGES_TELEPORT_DELAY.getConfigValue(player));

        } else {
            SpawnUtils.teleport(player);
        }
    }

    public static void teleport(Player player) {
        Location location = getLocation();

        if (location == null) {
            String spawnNotSet = EnumLang.MESSAGES_SPAWN_NOT_SET.getConfigValue(player);
            player.sendMessage(MethodsUtils.color(player, spawnNotSet));
        } else {
            if (!location.getChunk().isLoaded()) {
                location.getChunk().load();
            }
            player.teleport(location);
            SpawnEffect.register(location);
            if (EnumConfig.SPAWN_COMMAND_MESSAGE_ENABLED.getConfigBoolean()) {
                player.sendMessage(EnumLang.SPAWN_COMMAND_MESSAGE.getConfigValue(player));
            }
        }
    }

    public static void removeDelay(Player p) {
        if (delay.containsKey(p)) {
            if (delay.get(p).getTask().isSync()) {
                delay.get(p).getTask().cancel();
            }
            delay.remove(p);
        }
    }

}
