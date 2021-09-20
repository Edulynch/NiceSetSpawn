package me.edulynch.nicesetspawn.commands;

import me.edulynch.nicesetspawn.Config.enumConfig;
import me.edulynch.nicesetspawn.Config.enumLang;
import me.edulynch.nicesetspawn.helpers.Spawn;
import me.edulynch.nicesetspawn.interfaces.CommandTab;
import me.edulynch.nicesetspawn.listeners.EntityDamageByEntity;
import me.edulynch.nicesetspawn.utils.Constants;
import me.edulynch.nicesetspawn.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class SpawnCMD implements CommandTab {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (Utils.verifyIfIsConsole(sender)) return true;

        Player player = (Player) sender;

        if (args.length == 0) {

            if (EntityDamageByEntity.containsKey(player)
                    && enumConfig.DISABLE_SPAWN_COMMAND_IN_PVP_ENABLED.getConfigBoolean()
                    && enumConfig.DISABLE_SPAWN_COMMAND_IN_PVP_SECONDS.getConfigInteger() > 0) {

                sender.sendMessage(enumLang.DISABLE_SPAWN_COMMAND_IN_PVP_MESSAGE.getConfigValue(player));

            } else {
                if (!Utils.hasPermission(player, Constants.PERMISSION_BYPASSDELAY)) {
                    if (enumConfig.SPAWN_COMMAND_NEED_PERMISSION.getConfigBoolean()) {
                        if (Utils.hasPermission(player, Constants.PERMISSION_SPAWN)) {
                            Spawn.spawn(player, false);
                        } else {
                            sender.sendMessage(enumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
                        }
                    } else {
                        Spawn.spawn(player, false);
                    }
                } else {
                    Spawn.spawn(player, true);
                }
            }

        } else if (args.length == 1) {

            if (Utils.hasPermission(player, Constants.PERMISSION_TELEPORTOTHERS)) {

                Player target = Bukkit.getPlayer(args[0]);

                if (target != null && target.isOnline()) {
                    EntityDamageByEntity.remove(target);
                    Spawn.removeDelay(target);
                    Spawn.spawn(target, true);

                    player.sendMessage(enumLang.MESSAGES_TELEPORTED_OTHER_PLAYER.getConfigValue(player));

                } else {
                    sender.sendMessage(enumLang.MESSAGES_PLAYER_NOT_FOUND.getConfigValue(sender));
                }
            } else {
                sender.sendMessage(enumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
            }

        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1 && Utils.hasPermission(sender, Constants.PERMISSION_TELEPORTOTHERS)) {
            return null; // Bukkit will list online players.
        }
        return Collections.emptyList();
    }
}
