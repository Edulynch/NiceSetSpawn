package me.edulynch.nicesetspawn.commands;

import me.edulynch.nicesetspawn.Spawn;
import me.edulynch.nicesetspawn.utils.ConfigUtil;
import me.edulynch.nicesetspawn.enumMessages.enumLang;
import me.edulynch.nicesetspawn.interfaces.CommandTab;
import me.edulynch.nicesetspawn.utils.Constants;
import me.edulynch.nicesetspawn.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class SetSpawnCMD implements CommandTab {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (Utils.verifyIfIsAPlayer(sender)) return true;

        Player player = (Player) sender;

        if (Utils.hasPermission(player, Constants.PERMISSION_SETSPAWN)) {

            Location location = player.getLocation();

            Spawn.setLocation(location);

            player.getWorld().setSpawnLocation(location);

            player.sendMessage(enumLang.MESSAGES_SPAWN_SUCCESSFULLY_SET.getConfigValue(new String[]{}));

        } else {
            sender.sendMessage(ConfigUtil.getNoPermission());
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
