package me.edulynch.nicesetspawn.commands;


import me.edulynch.nicesetspawn.Main;
import me.edulynch.nicesetspawn.configs.ConfigUtil;
import me.edulynch.nicesetspawn.Spawn;
import me.edulynch.nicesetspawn.interfaces.CommandTab;
import me.edulynch.nicesetspawn.listeners.EntityDamageByEntity;
import me.edulynch.nicesetspawn.utils.Constants;
import me.edulynch.nicesetspawn.utils.Utils;
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

        if (Utils.verifyIfIsAPlayer(sender)) return true;

        Player player = (Player) sender;

        if (EntityDamageByEntity.containsKey(player) && Main.getConfiguration().getBoolean("disable-spawn-command-in-pvp.enabled")) {

            sender.sendMessage(Utils.color(Main.getConfiguration().getString("disable-spawn-command-in-pvp.message")));

        } else {
            if (!Utils.hasPermission(player, Constants.PERMISSION_BYPASSDELAY)) {
                if (Main.getConfiguration().getBoolean("spawn-command.need-permission")) {
                    if (Utils.hasPermission(player, Constants.PERMISSION_SPAWN)) {
                        Spawn.spawn(player, false);
                    } else {
                        sender.sendMessage(ConfigUtil.getNoPermission());
                    }
                } else {
                    Spawn.spawn(player, false);
                }
            } else {
                Spawn.spawn(player, true);
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
