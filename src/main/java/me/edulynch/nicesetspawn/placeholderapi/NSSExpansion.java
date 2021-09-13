package me.edulynch.nicesetspawn.placeholderapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.edulynch.nicesetspawn.Main;
import me.edulynch.nicesetspawn.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NSSExpansion extends PlaceholderExpansion {

    private final Main plugin;

    public NSSExpansion(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "nss";
    }

    @Override
    public @NotNull String getAuthor() {
        return Constants.PLUGIN_AUTHOR;
    }

    @Override
    public @NotNull String getVersion() {
        return Constants.PLUGIN_VERSION;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
       /*
         %nss_players_online%
         Returns the number of online players
          */
        if (identifier.equalsIgnoreCase("players_online")) {
            return String.valueOf(Bukkit.getOnlinePlayers().size());
        }

        /*
        Check if the player is online,
        You should do this before doing anything regarding players
         */
        if (player == null) {
            return "";
        }

        /*
        %nss_player_name%
        Returns the player name
         */
        if (identifier.equalsIgnoreCase("player_name")) {
            return player.getName();
        }

        return null;
    }
}
