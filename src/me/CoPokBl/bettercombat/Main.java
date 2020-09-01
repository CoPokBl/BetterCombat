package me.CoPokBl.bettercombat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	@Override
	public void onEnable() {
		// register events
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		Bukkit.getLogger().fine("Enabled BetterCombat By CoPokBl and Calcilator");
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("bettercombat")) {
			sender.sendMessage(ChatColor.GREEN + "BetterCombat is enabled!");
		}
		
		return true;
	}

}
