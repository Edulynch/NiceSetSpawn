package me.edulynch.nicesetspawn.listeners;

import me.edulynch.nicesetspawn.config.EnumConfig;
import me.edulynch.nicesetspawn.config.EnumLang;
import me.edulynch.nicesetspawn.Main;
import me.edulynch.nicesetspawn.utils.SpawnUtils;
import me.edulynch.nicesetspawn.utils.PluginConstants;
import me.edulynch.nicesetspawn.utils.MethodsUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        if (!player.hasPlayedBefore()) {
            playerFirstJoin(player, e);
        } else {
            playerJoin(player, e);
        }

        playerGameMode(player);
        playerFly(player);
        playerHealthAndFood(player);

    }

    private void playerHealthAndFood(Player player) {
        if (EnumConfig.OPTIONS_SET_MAX_HEALTH_ON_JOIN.getConfigBoolean()) {
            player.setHealth(20);
        }

        if (EnumConfig.OPTIONS_SET_MAX_FOOD_LEVEL_ON_JOIN.getConfigBoolean()) {
            player.setFoodLevel(20);
        }

    }

    private void playerFly(Player player) {
        if (EnumConfig.OPTIONS_SET_FLY_ON_JOIN_ENABLED.getConfigBoolean()) {
            player.setAllowFlight(true);
            player.sendMessage(EnumLang.OPTIONS_SET_FLY_ON_JOIN_MESSAGE.getConfigValue(player));
        } else {
            player.setAllowFlight(false);
        }
    }

    private void playerJoin(Player player, PlayerJoinEvent e) {
        if (player.isOp() && EnumConfig.CHECK_VERSION_ENABLED.getConfigBoolean()) {
            checkNewVersionAvailable(player);
        }

        if (EnumConfig.TELEPORT_TO_SPAWN_ON_JOIN.getConfigBoolean()) {
            SpawnUtils.spawn(player, true);
        }
        if (EnumConfig.BROADCAST_PLAYER_JOIN_ENABLED.getConfigBoolean()) {
            Bukkit.broadcastMessage(EnumLang.BROADCAST_PLAYER_JOIN_MESSAGE.getConfigValue(player));
        }
        if (EnumConfig.WELCOME_MESSAGE_PLAYER_JOIN_ENABLED.getConfigBoolean()) {
            e.setJoinMessage(null);
            for (String message : EnumConfig.WELCOME_MESSAGE_PLAYER_JOIN_TEXT.getConfigStringList(player)) {
                player.sendMessage(MethodsUtils.color(player, message));
            }
        }
    }

    private void checkNewVersionAvailable(Player player) {
        try {
            String newVersion = "";
            boolean isNewVersionAvailable = false;
            URL url = new URL(PluginConstants.URL_GITHUBVERSIONTXT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                if (!inputLine.equalsIgnoreCase(PluginConstants.PLUGIN_VERSION)) {
                    isNewVersionAvailable = true;
                    newVersion = inputLine;
                    break;
                }
            }
            reader.close();
            if (isNewVersionAvailable) {
                player.sendMessage(EnumLang.CHECK_VERSION_WARNING_MESSAGE.getConfigValue(player) + " §aV" + newVersion);
                Main.getInstance().getLogger().warning(EnumLang.CHECK_VERSION_WARNING_MESSAGE.getConfigValue(player) + " §aV" + newVersion);
            }
        } catch (Exception e) {
            Main.getInstance().getLogger().severe(EnumLang.CHECK_VERSION_ERROR_MESSAGE.getConfigValue(player));
        }
    }

    private void playerFirstJoin(Player player, PlayerJoinEvent e) {
        if (EnumConfig.TELEPORT_TO_SPAWN_ON_FIRST_JOIN.getConfigBoolean()) {
            SpawnUtils.spawn(player, true);
        }
        if (EnumConfig.BROADCAST_FIRST_JOIN_ENABLED.getConfigBoolean()) {
            Bukkit.broadcastMessage(EnumLang.BROADCAST_FIRST_JOIN_MESSAGE.getConfigValue(player));
        }
        if (EnumConfig.WELCOME_MESSAGE_FIRST_JOIN_ENABLED.getConfigBoolean()) {
            e.setJoinMessage(null);
            for (String message : EnumConfig.WELCOME_MESSAGE_FIRST_JOIN_TEXT.getConfigStringList(player)) {
                player.sendMessage(MethodsUtils.color(player, message));
            }
        }
    }

    private void playerGameMode(Player player) {
        if (EnumConfig.OPTIONS_SET_GAMEMODE_ON_JOIN_ENABLED.getConfigBoolean()) {
            int gamemode = EnumConfig.OPTIONS_SET_GAMEMODE_ON_JOIN_GAMEMODE.getConfigInteger();
            switch (gamemode) {
                case 0:
                    player.setGameMode(GameMode.SURVIVAL);
                    break;
                case 1:
                    player.setGameMode(GameMode.CREATIVE);
                    break;
                case 2:
                    player.setGameMode(GameMode.ADVENTURE);
                    break;
                case 3:
                    player.setGameMode(GameMode.SPECTATOR);
                    break;
                default:
                    Main.getConfiguration().set(EnumConfig.OPTIONS_SET_GAMEMODE_ON_JOIN_GAMEMODE.getPath(), gamemode);
                    player.setGameMode(GameMode.SURVIVAL);
            }
        }
    }

}
