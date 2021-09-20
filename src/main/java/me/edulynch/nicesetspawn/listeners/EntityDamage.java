package me.edulynch.nicesetspawn.listeners;

import me.edulynch.nicesetspawn.Config.enumConfig;
import me.edulynch.nicesetspawn.Config.enumLang;
import me.edulynch.nicesetspawn.helpers.Spawn;
import me.edulynch.nicesetspawn.utils.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class EntityDamage implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    private void onEntityDamage(EntityDamageEvent event) {

        Entity entity = event.getEntity();

        if (entity instanceof Player) {

            Player player = (Player) event.getEntity();

            if (event.getCause() == EntityDamageEvent.DamageCause.VOID && enumConfig.TELEPORT_TO_SPAWN_ON_VOID_FALL.getConfigBoolean()) {
                event.setCancelled(true);
                player.setFallDistance(0);
                Spawn.spawn(player, true);
                player.sendMessage(Utils.color(player, enumLang.MESSAGES_VOID_FALL.getConfigValue(player)));
            } else if (event.getCause() == EntityDamageEvent.DamageCause.FALL && enumConfig.OPTIONS_DISABLE_FALL_DAMAGE.getConfigBoolean()) {
                event.setCancelled(true);
                player.setFallDistance(0);
            } else {
                event.setCancelled(false);
            }
        } else {
            event.setCancelled(false);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player && enumConfig.OPTIONS_DISABLE_HUNGER_DEPLETE.getConfigBoolean()) {
            e.setCancelled(true);
            e.setFoodLevel(20);
        } else {
            e.setCancelled(false);
        }
    }

}
