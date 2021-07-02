package me.CoPokBl.bettercombat;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class isTool {
	
	// methods to get if the wepon is an axe or pickaxe
		public Boolean isaxe( ItemStack item ) {
			Material i = item.getType();
			if (i.equals(Material.WOODEN_AXE) || i.equals(Material.STONE_AXE) || i.equals(Material.IRON_AXE) || i.equals(Material.DIAMOND_AXE) || i.equals(Material.NETHERITE_AXE)) {
				return true;
			}
			return false;
		}
		
		// get if pickaxe
		public Boolean ispickaxe( ItemStack item ) {
			Material i = item.getType();
			if (i.equals(Material.WOODEN_PICKAXE) || i.equals(Material.STONE_PICKAXE) || i.equals(Material.IRON_PICKAXE) || i.equals(Material.DIAMOND_PICKAXE) || i.equals(Material.NETHERITE_PICKAXE)) {
				return true;
			}
			return false;
		}
		
		// get if hoe
		public Boolean ishoe( ItemStack item ) {
			Material i = item.getType();
			if (i.equals(Material.WOODEN_HOE) || i.equals(Material.STONE_HOE) || i.equals(Material.IRON_HOE) || i.equals(Material.DIAMOND_HOE) || i.equals(Material.NETHERITE_HOE)) {
				return true;
			}
			return false;
		}
		

}
