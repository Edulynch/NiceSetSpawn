package me.edulynch.nicesetspawn.listeners;

import me.edulynch.nicesetspawn.Main;
import me.edulynch.nicesetspawn.Spawn;
import me.edulynch.nicesetspawn.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (Main.getConfiguration().getBoolean("broadcast.player-quit.enabled")) {
            Player player = event.getPlayer();
            String playerQuitMessage = Main.getConfiguration().getString("broadcast.player-quit.message");
            if (playerQuitMessage != null) {
                event.setQuitMessage(Utils.colorPapi(playerQuitMessage, player));
            }
            Spawn.removeDelay(player);
            EntityDamageByEntity.remove(player);
        }
    }
}
