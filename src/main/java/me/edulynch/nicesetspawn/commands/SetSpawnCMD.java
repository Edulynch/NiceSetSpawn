package me.edulynch.nicesetspawn.commands;

import me.edulynch.nicesetspawn.config.EnumLang;
import me.edulynch.nicesetspawn.utils.SpawnUtils;
import me.edulynch.nicesetspawn.interfaces.CommandTab;
import me.edulynch.nicesetspawn.utils.PluginConstants;
import me.edulynch.nicesetspawn.utils.MethodsUtils;
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
        if (MethodsUtils.verifyIfIsConsole(sender)) return true;

        Player player = (Player) sender;

        if (MethodsUtils.hasPermission(player, PluginConstants.PERMISSION_SETSPAWN)) {

            Location location = player.getLocation();

            SpawnUtils.setLocation(location);

            player.getWorld().setSpawnLocation(location);

            player.sendMessage(EnumLang.MESSAGES_SPAWN_SUCCESSFULLY_SET.getConfigValue(player));

        } else {
            sender.sendMessage(EnumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
