package me.CoPokBl.bettercombat;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class isTool {
	
	// methods to get if the wepon is an axe or pickaxe
		public static boolean isAxe( ItemStack item ) {
			Material i = item.getType();
			return i.equals(Material.WOODEN_AXE) || i.equals(Material.STONE_AXE) || i.equals(Material.IRON_AXE) || i.equals(Material.DIAMOND_AXE) || i.equals(Material.NETHERITE_AXE);
		}
		
		// get if pickaxe
		public static boolean isPickaxe( ItemStack item ) {
			Material i = item.getType();
			return i.equals(Material.WOODEN_PICKAXE) || i.equals(Material.STONE_PICKAXE) || i.equals(Material.IRON_PICKAXE) || i.equals(Material.DIAMOND_PICKAXE) || i.equals(Material.NETHERITE_PICKAXE);
		}
		
		// get if hoe
		public static boolean isHoe( ItemStack item ) {
			Material i = item.getType();
			return i.equals(Material.WOODEN_HOE) || i.equals(Material.STONE_HOE) || i.equals(Material.IRON_HOE) || i.equals(Material.DIAMOND_HOE) || i.equals(Material.NETHERITE_HOE);
		}
		

}
