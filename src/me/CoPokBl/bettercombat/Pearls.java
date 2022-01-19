package me.CoPokBl.bettercombat;

import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class Pearls implements Listener {
    /*@EventHandler
    public void onPearl(ProjectileLaunchEvent e) {
        if(!(e.getEntity() instanceof EnderPearl)) return;
        if (!(e.getEntity().getShooter() instanceof Player)) return;
        Player p = (Player) e.getEntity().getShooter();
        e.setCancelled(false);
        p.getInventory().remove(new ItemStack(Material.ENDER_PEARL, 1));
        p.launchProjectile(e.getEntity().getClass());
    }*/
}
