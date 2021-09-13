package me.edulynch.nicesetspawn.listeners;

import me.edulynch.nicesetspawn.Main;
import me.edulynch.nicesetspawn.Spawn;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlayerRespawn(PlayerRespawnEvent e) {
        if (Main.getConfiguration().getBoolean("teleport-to-spawn-on.respawn")) {
            Location location = Spawn.getLocation();
            if (location != null) {
                e.setRespawnLocation(location);
            }
        }
    }

}
