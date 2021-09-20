package me.edulynch.nicesetspawn.listeners;

import me.edulynch.nicesetspawn.config.EnumConfig;
import me.edulynch.nicesetspawn.config.EnumLang;
import me.edulynch.nicesetspawn.utils.SpawnUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (EnumConfig.BROADCAST_PLAYER_QUIT_ENABLED.getConfigBoolean()) {
            Player player = event.getPlayer();
            event.setQuitMessage(EnumLang.BROADCAST_PLAYER_QUIT_MESSAGE.getConfigValue(player));
            SpawnUtil.removeDelay(player);
            EntityDamageByEntity.remove(player);
        }
    }
}
