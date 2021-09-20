package me.edulynch.nicesetspawn.commands;

import me.edulynch.nicesetspawn.config.EnumConfig;
import me.edulynch.nicesetspawn.config.EnumLang;
import me.edulynch.nicesetspawn.utils.SpawnUtils;
import me.edulynch.nicesetspawn.interfaces.CommandTab;
import me.edulynch.nicesetspawn.listeners.EntityDamageByEntity;
import me.edulynch.nicesetspawn.utils.PluginConstants;
import me.edulynch.nicesetspawn.utils.MethodsUtils;
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

        if (MethodsUtils.verifyIfIsConsole(sender)) return true;

        Player player = (Player) sender;

        if (args.length == 0) {

            if (EntityDamageByEntity.containsKey(player)
                    && EnumConfig.DISABLE_SPAWN_COMMAND_IN_PVP_ENABLED.getConfigBoolean()
                    && EnumConfig.DISABLE_SPAWN_COMMAND_IN_PVP_SECONDS.getConfigInteger() > 0) {

                sender.sendMessage(EnumLang.DISABLE_SPAWN_COMMAND_IN_PVP_MESSAGE.getConfigValue(player));

            } else {
                if (!MethodsUtils.hasPermission(player, PluginConstants.PERMISSION_BYPASSDELAY)) {
                    if (EnumConfig.SPAWN_COMMAND_NEED_PERMISSION.getConfigBoolean()) {
                        if (MethodsUtils.hasPermission(player, PluginConstants.PERMISSION_SPAWN)) {
                            SpawnUtils.spawn(player, false);
                        } else {
                            sender.sendMessage(EnumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
                        }
                    } else {
                        SpawnUtils.spawn(player, false);
                    }
                } else {
                    SpawnUtils.spawn(player, true);
                }
            }

        } else if (args.length == 1) {

            if (MethodsUtils.hasPermission(player, PluginConstants.PERMISSION_TELEPORTOTHERS)) {

                Player target = Bukkit.getPlayer(args[0]);

                if (target != null && target.isOnline()) {
                    EntityDamageByEntity.remove(target);
                    SpawnUtils.removeDelay(target);
                    SpawnUtils.spawn(target, true);

                    player.sendMessage(EnumLang.MESSAGES_TELEPORTED_OTHER_PLAYER.getConfigValue(player));

                } else {
                    sender.sendMessage(EnumLang.MESSAGES_PLAYER_NOT_FOUND.getConfigValue(sender));
                }
            } else {
                sender.sendMessage(EnumLang.MESSAGES_NO_PERMISSION.getConfigValue(sender));
            }

        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1 && MethodsUtils.hasPermission(sender, PluginConstants.PERMISSION_TELEPORTOTHERS)) {
            return null; // Bukkit will list online players.
        }
        return Collections.emptyList();
    }
}
