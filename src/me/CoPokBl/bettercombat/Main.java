package me.CoPokBl.bettercombat;

import java.util.ArrayList;
import java.util.Objects;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {

		OnDamage.plugin = this;
		OnJoin.plugin = this;
		
		// make list for world exclude
		ArrayList<String> worlds = new ArrayList<>();
		worlds.add("newCombat");
		
		// register events
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new OnDamage(), this);
		pm.registerEvents(new OnJoin(), this);

		Objects.requireNonNull(getCommand("BetterCombat")).setExecutor( new BetterCombatCommand() );
		
		Bukkit.getLogger().fine( "Enabled BetterCombat By CoPokBl" );
		
		// basic config for attack speed
		getConfig().addDefault( Attribute.GENERIC_ATTACK_SPEED.name(), 100 );
		if ( (! getConfig().contains("worlds") ) ) {
			
			// add worlds list
			getConfig().set("worlds", worlds);
			
		}
		
        getConfig().options().copyDefaults( true );
        saveConfig();
        
	}
	
	@Override
	public void onDisable() { }
	
	
	// start the loop of going through the config and applying values of Attribute modifications
	@Deprecated
	public void startLoop() {
		BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
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
		}, 20L, 20L);
	}


}
