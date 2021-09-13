package me.edulynch.nicesetspawn.listeners;

import me.edulynch.nicesetspawn.Main;
import me.edulynch.nicesetspawn.Spawn;
import me.edulynch.nicesetspawn.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class EntityDamage implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    private void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            //If the entity is a player
            Player player = (Player) event.getEntity();
            // If the cause of the event is the void
            player.setFallDistance(0);
            Spawn.spawn(player, true);
            //Teleport the player to the world's spawn-location
            player.sendMessage(Utils.colorPapi(Main.getConfiguration().getString("messages.void-fall"), player));
        } else {
            event.setCancelled(false);
        }
    }

}
