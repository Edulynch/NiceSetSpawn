package me.edulynch.nicesetspawn.listeners;

import me.edulynch.nicesetspawn.Config.enumConfig;
import me.edulynch.nicesetspawn.Main;
import me.edulynch.nicesetspawn.utils.Constants;
import me.edulynch.nicesetspawn.utils.Utils;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class EntityDamageByEntity implements Listener {

    private static final HashMap<Player, BukkitTask> pvp = new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {

        // Disable Firework Spawn Damage
        if (e.getDamager() instanceof Firework) {
            Firework fw = (Firework) e.getDamager();
            if (fw.hasMetadata("nodamage")) {
                e.setCancelled(true);
            }
        }

        if (!((e.getEntity() instanceof Player) && enumConfig.DISABLE_SPAWN_COMMAND_IN_PVP_ENABLED.getConfigBoolean())) {
            return;
        }

        final Player p = (Player) e.getEntity();

        if (e.getDamager() instanceof Player) {
            pvp(p);
            pvp((Player) e.getDamager());
        } else if ((e.getDamager() instanceof Arrow) && (((Arrow) e.getDamager()).getShooter() instanceof Player)) {
            pvp(p);
            pvp((Player) ((Arrow) e.getDamager()).getShooter());
        }
    }

    public static boolean containsKey(Player p) {
        if (!Utils.hasPermission(p, Constants.PERMISSION_BYPASSPVP)) {
            return pvp.containsKey(p);
        } else {
            return false;
        }
    }

    public static void remove(Player p) {
        if (pvp.containsKey(p)) {
            if (pvp.get(p).isSync()) {
                pvp.get(p).cancel();
            }

            pvp.remove(p);
        }
    }

    @SuppressWarnings("all")
    private void pvp(final Player p) {
        if (pvp.containsKey(p)) {
            if (pvp.get(p).isSync()) {
                pvp.get(p).cancel();
            }
        }

        pvp.put(p, new BukkitRunnable() {

            @Override
            public void run() {
                if (pvp.containsKey(p)) {
                    pvp.remove(p);
                }
            }

        }.runTaskLater(Main.getInstance(), enumConfig.DISABLE_SPAWN_COMMAND_IN_PVP_SECONDS.getConfigInteger() * 20L));
    }

}

