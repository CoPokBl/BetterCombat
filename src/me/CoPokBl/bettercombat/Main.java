package me.CoPokBl.bettercombat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import net.minecraft.server.v1_16_R1.EntityLiving;
import net.minecraft.server.v1_16_R1.NBTTagCompound;

public class Main extends JavaPlugin implements Listener{
	@Override
	public void onEnable() {
		// register events
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		Bukkit.getLogger().fine("Enabled BetterCombat By CoPokBl and Calcilator");
		
		// basic config for attack speed
		getConfig().addDefault( Attribute.GENERIC_ATTACK_SPEED.name(), 16 );
		
        getConfig().options().copyDefaults( true );
        saveConfig();
        
        // make sure that people can spam
        
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public void startloop() {
		BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player p: Bukkit.getOnlinePlayers()) {
                	
                	for ( String key : getConfig().getKeys( false ) )
                    {
                        AttributeInstance instance = p.getAttribute( Attribute.valueOf( key ) );
                        if ( instance != null )
                        {
                            instance.setBaseValue( getConfig().getDouble( key, instance.getBaseValue() ) );
                        }
                    }
                	
                }
            }
        }, 20L, 20L);
	}
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void OnDamage(EntityDamageByEntityEvent e) {
		if (e.isCancelled())return;
		//if (!(e.getCause().equals(DamageCause.)))
		//	return;
		
		
		Double damage = e.getDamage();
		
		if (e.getEntity() instanceof Player) {
			
			Player p = (Player) e.getEntity();
			
			// if sneaking half damage again
			if (p.isSneaking()) {
				damage = damage / 2;
			}
			
		}
		
		if (e.getDamager() instanceof Player) {
			
			if (e.isCancelled())
				return;
			
			// half all player attack damage
			damage = damage / 2;

			Player dmer = (Player) e.getDamager();
			ItemStack wepon = dmer.getInventory().getItemInMainHand();
			Integer kblevel = wepon.getEnchantmentLevel(Enchantment.KNOCKBACK);
			Entity pl = e.getEntity();
			if (dmer.isSwimming())
					damage = damage / 2;
			if (dmer.isSneaking()) {
				// pushing (the new and epic mechanic)
				pl.setVelocity(dmer.getLocation().getDirection().multiply(0.7).setY(0.4));
				e.setCancelled(true);
			} else {
				// with damage
				
				if (isaxe(wepon)) {
					
					if (kblevel == 0) {
						Bukkit.getScheduler().runTaskLater(this, () -> dmer.setVelocity(dmer.getLocation().getDirection().multiply(-0.1).setY(0)), 1l);
					} else {
						Bukkit.getScheduler().runTaskLater(this, () -> pl.setVelocity(dmer.getLocation().getDirection().multiply(0.1 * kblevel).setY(0.4)), 1l);
					}
					
				} else {
					
					if(kblevel == 0) {
						Bukkit.getScheduler().runTaskLater(this, () -> pl.setVelocity(dmer.getLocation().getDirection().multiply(0.4).setY(0.4)), 1l);
					} else {
						Bukkit.getScheduler().runTaskLater(this, () -> pl.setVelocity(dmer.getLocation().getDirection().multiply(0.4 * kblevel).setY(0.4)), 1l);
					}
					
				}
				
			}
			
		}
			// apply damage
			e.setDamage(damage);
		}
	
	@EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        for ( String key : getConfig().getKeys( false ) )
        {
            AttributeInstance instance = event.getPlayer().getAttribute( Attribute.valueOf( key ) );
            if ( instance != null )
            {
                instance.setBaseValue( getConfig().getDouble( key, instance.getBaseValue() ) );
            }
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
