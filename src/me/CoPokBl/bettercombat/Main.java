package me.CoPokBl.bettercombat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
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
	
	
	@EventHandler
	public void OnDamage(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		
		if (e.getDamager() instanceof Player) {
			if (p.isSneaking()) {
				e.setCancelled(true);
				return;
			}
			Player dmer = (Player) e.getDamager();
			ItemStack wepon = dmer.getInventory().getItemInMainHand();
			if (isaxe(wepon)) {
				
			}
		}
		
		
		
		if (p.isSneaking()) {
			e.setDamage(e.getDamage() / 2);
		}
	}
	
	
	
	
	
	
	
	
	public Boolean isaxe(ItemStack item) {
		Material i = item.getType();
		if (i.equals(Material.WOODEN_AXE) || i.equals(Material.STONE_AXE) || i.equals(Material.IRON_AXE) || i.equals(Material.DIAMOND_AXE) || i.equals(Material.NETHERITE_AXE)) {
			return true;
		}
		return false;
	}
	public Boolean ispickaxe(ItemStack item) {
		Material i = item.getType();
		if (i.equals(Material.WOODEN_PICKAXE) || i.equals(Material.STONE_PICKAXE) || i.equals(Material.IRON_PICKAXE) || i.equals(Material.DIAMOND_PICKAXE) || i.equals(Material.NETHERITE_PICKAXE)) {
			return true;
		}
		return false;
	}
	
	
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("bettercombat")) {
			sender.sendMessage(ChatColor.GREEN + "BetterCombat is enabled!");
		}
		
		return true;
	}

}
