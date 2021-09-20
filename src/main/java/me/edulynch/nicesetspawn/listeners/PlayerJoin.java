package me.edulynch.nicesetspawn.listeners;

import me.edulynch.nicesetspawn.Config.enumConfig;
import me.edulynch.nicesetspawn.Config.enumLang;
import me.edulynch.nicesetspawn.Main;
import me.edulynch.nicesetspawn.helpers.Spawn;
import me.edulynch.nicesetspawn.utils.Constants;
import me.edulynch.nicesetspawn.utils.Utils;
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
        if (enumConfig.OPTIONS_SET_MAX_HEALTH_ON_JOIN.getConfigBoolean()) {
            player.setHealth(20);
        }

        if (enumConfig.OPTIONS_SET_MAX_FOOD_LEVEL_ON_JOIN.getConfigBoolean()) {
            player.setFoodLevel(20);
        }

    }

    private void playerFly(Player player) {
        if (enumConfig.OPTIONS_SET_FLY_ON_JOIN_ENABLED.getConfigBoolean()) {
            player.setAllowFlight(true);
            player.sendMessage(enumLang.OPTIONS_SET_FLY_ON_JOIN_MESSAGE.getConfigValue(player));
        } else {
            player.setAllowFlight(false);
        }
    }

    private void playerJoin(Player player, PlayerJoinEvent e) {
        if (player.isOp() && enumConfig.CHECK_VERSION_ENABLED.getConfigBoolean()) {
            checkNewVersionAvailable(player);
        }

        if (enumConfig.TELEPORT_TO_SPAWN_ON_JOIN.getConfigBoolean()) {
            Spawn.spawn(player, true);
        }
        if (enumConfig.BROADCAST_PLAYER_JOIN_ENABLED.getConfigBoolean()) {
            Bukkit.broadcastMessage(enumLang.BROADCAST_PLAYER_JOIN_MESSAGE.getConfigValue(player));
        }
        if (enumConfig.WELCOME_MESSAGE_PLAYER_JOIN_ENABLED.getConfigBoolean()) {
            e.setJoinMessage(null);
            for (String message : enumConfig.WELCOME_MESSAGE_PLAYER_JOIN_TEXT.getConfigStringList(player)) {
                player.sendMessage(Utils.color(player, message));
            }
        }
    }

    private void checkNewVersionAvailable(Player player) {
        try {
            String newVersion = "";
            boolean isNewVersionAvailable = false;
            URL url = new URL(Constants.URL_GITHUBVERSIONTXT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                if (!inputLine.equalsIgnoreCase(Constants.PLUGIN_VERSION)) {
                    isNewVersionAvailable = true;
                    newVersion = inputLine;
                    break;
                }
            }
            reader.close();
            if (isNewVersionAvailable) {
                player.sendMessage(enumLang.CHECK_VERSION_WARNING_MESSAGE.getConfigValue(player) + " §aV" + newVersion);
                Main.getInstance().getLogger().warning(enumLang.CHECK_VERSION_WARNING_MESSAGE.getConfigValue(player) + " §aV" + newVersion);
            }
        } catch (Exception e) {
            Main.getInstance().getLogger().severe(enumLang.CHECK_VERSION_ERROR_MESSAGE.getConfigValue(player));
        }
    }

    private void playerFirstJoin(Player player, PlayerJoinEvent e) {
        if (enumConfig.TELEPORT_TO_SPAWN_ON_FIRST_JOIN.getConfigBoolean()) {
            Spawn.spawn(player, true);
        }
        if (enumConfig.BROADCAST_FIRST_JOIN_ENABLED.getConfigBoolean()) {
            Bukkit.broadcastMessage(enumLang.BROADCAST_FIRST_JOIN_MESSAGE.getConfigValue(player));
        }
        if (enumConfig.WELCOME_MESSAGE_FIRST_JOIN_ENABLED.getConfigBoolean()) {
            e.setJoinMessage(null);
            for (String message : enumConfig.WELCOME_MESSAGE_FIRST_JOIN_TEXT.getConfigStringList(player)) {
                player.sendMessage(Utils.color(player, message));
            }
        }
    }

    private void playerGameMode(Player player) {
        if (enumConfig.OPTIONS_SET_GAMEMODE_ON_JOIN_ENABLED.getConfigBoolean()) {
            int gamemode = enumConfig.OPTIONS_SET_GAMEMODE_ON_JOIN_GAMEMODE.getConfigInteger();
            switch (gamemode) {
                case 0: {
                    player.setGameMode(GameMode.SURVIVAL);
                    break;
                }
                case 1: {
                    player.setGameMode(GameMode.CREATIVE);
                    break;
                }
                case 2: {
                    player.setGameMode(GameMode.ADVENTURE);
                    break;
                }
                case 3: {
                    player.setGameMode(GameMode.SPECTATOR);
                    break;
                }
                default:
                    Main.getConfiguration().set(enumConfig.OPTIONS_SET_GAMEMODE_ON_JOIN_GAMEMODE.getPath(), gamemode);
                    player.setGameMode(GameMode.SURVIVAL);
            }
        }
    }

}
