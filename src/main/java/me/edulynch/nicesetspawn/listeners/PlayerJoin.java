package me.edulynch.nicesetspawn.listeners;

import me.edulynch.nicesetspawn.Main;
import me.edulynch.nicesetspawn.Spawn;
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
import java.util.List;

public class PlayerJoin implements Listener {

    private String broadcastJoinMessage;
    private List<String> welcomeJoinText;

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
        if (Main.getConfiguration().getBoolean("options.set-max-health-on-join")) {
            player.setHealth(20);
        }

        if (Main.getConfiguration().getBoolean("options.set-max-food-level-on-join")) {
            player.setFoodLevel(20);
        }

    }

    private void playerFly(Player player) {
        player.setAllowFlight(Main.getConfiguration().getBoolean("options.set-fly-on-join"));
    }

    private void playerJoin(Player player, PlayerJoinEvent e) {
        if (player.isOp() && Main.getConfiguration().getBoolean("check-version.enabled")) {
            checkNewVersionAvailable(player);
        }

        if (Main.getConfiguration().getBoolean("teleport-to-spawn-on.join")) {
            Spawn.spawn(player, true);
        }
        if (Main.getConfiguration().getBoolean("broadcast.player-join.enabled")) {
            broadcastJoinMessage = Main.getConfiguration().getString("broadcast.player-join.message");
            Bukkit.broadcastMessage(Utils.colorPapi(broadcastJoinMessage, e.getPlayer()));
        }
        if (Main.getConfiguration().getBoolean("welcome-message.player-join.enabled")) {
            e.setJoinMessage(null);
            welcomeJoinText = Main.getConfiguration().getStringList("welcome-message.player-join.text");
            for (String message : welcomeJoinText) {
                player.sendMessage(Utils.colorPapi(message, e.getPlayer()));
            }
        }
    }

    private void checkNewVersionAvailable(Player player) {
        try {
            boolean isNewVersionAvailable = false;
            URL url = new URL("https://raw.githubusercontent.com/Edulynch/NiceSetSpawn/main/version.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                if (!inputLine.equalsIgnoreCase(Constants.PLUGIN_VERSION)) {
                    isNewVersionAvailable = true;
                    break;
                }
            }
            reader.close();
            if (isNewVersionAvailable) {
                player.sendMessage(Utils.color(Main.getConfiguration().getString("check-version.warning-message")));
                Main.getInstance().getLogger().warning(Main.getConfiguration().getString("check-version.warning-message"));
            }
            Main.getInstance().getLogger().severe(isNewVersionAvailable + "");
        } catch (Exception e) {
            Main.getInstance().getLogger().severe(Main.getConfiguration().getString("check-version.error-message"));
        }
    }

    private void playerFirstJoin(Player player, PlayerJoinEvent e) {
        if (Main.getConfiguration().getBoolean("teleport-to-spawn-on.first-join")) {
            Spawn.spawn(player, true);
        }
        if (Main.getConfiguration().getBoolean("broadcast.first-join.enabled")) {
            broadcastJoinMessage = Main.getConfiguration().getString("broadcast.first-join.message");
            Bukkit.broadcastMessage(Utils.colorPapi(broadcastJoinMessage, e.getPlayer()));
        }
        if (Main.getConfiguration().getBoolean("welcome-message.first-join.enabled")) {
            e.setJoinMessage(null);
            welcomeJoinText = Main.getConfiguration().getStringList("welcome-message.first-join.text");
            for (String message : welcomeJoinText) {
                player.sendMessage(Utils.colorPapi(message, e.getPlayer()));
            }
        }
    }

    private void playerGameMode(Player player) {
        if (Main.getConfiguration().getBoolean("options.set-gamemode-on-join.enabled")) {
            int gamemode = Main.getConfiguration().getInt("options.set-gamemode-on-join.gamemode");
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
                    Main.getConfiguration().set("options.set-gamemode-on-join.gamemode", gamemode);
                    player.setGameMode(GameMode.SURVIVAL);
            }
        }
    }

}
