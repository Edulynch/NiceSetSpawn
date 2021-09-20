package me.edulynch.nicesetspawn.listeners;

import me.edulynch.nicesetspawn.Config.enumConfig;
import me.edulynch.nicesetspawn.Config.enumLang;
import me.edulynch.nicesetspawn.helpers.Spawn;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (enumConfig.BROADCAST_PLAYER_QUIT_ENABLED.getConfigBoolean()) {
            Player player = event.getPlayer();
            event.setQuitMessage(enumLang.BROADCAST_PLAYER_QUIT_MESSAGE.getConfigValue(player));
            Spawn.removeDelay(player);
            EntityDamageByEntity.remove(player);
        }
    }
}
