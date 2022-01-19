package me.CoPokBl.bettercombat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BetterCombatCommand implements CommandExecutor {

    // info command only
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if ( label.equalsIgnoreCase( "bettercombat" ) ) {
            sender.sendMessage( ChatColor.GREEN + "BetterCombat is enabled!" );
            return true;
        }

        return false;
    }

}
