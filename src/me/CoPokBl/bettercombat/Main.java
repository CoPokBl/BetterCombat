package me.CoPokBl.bettercombat;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin implements Listener{
	
	// define starting vars
	
	// define isTool class
	isTool istool = new isTool();
	
	@Override
	public void onEnable() {
		
		// make list for world exclude
		ArrayList<String> worlds = new ArrayList<String>();
		
		// register events
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		Bukkit.getLogger().fine( "Enabled BetterCombat By CoPokBl and Calcilator" );
		
		// basic config for attack speed
		getConfig().addDefault( Attribute.GENERIC_ATTACK_SPEED.name(), 40 );
		if ( (! getConfig().contains("worlds") ) ) {
			
			// add worlds list
			getConfig().set("worlds", worlds);
			
		}
		
        getConfig().options().copyDefaults( true );
        saveConfig();
        
        // make sure that people can spam
        
	}
	
	@Override
	public void onDisable() {
		
	}
	
	
	// start the loop of going through the config and applying values of Atribute modifications
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
	
	
	// priority highest so that it runs last after plugins have had chance to cancel
	@EventHandler( priority = EventPriority.HIGHEST )
	public void OnDamage( EntityDamageByEntityEvent e ) {
		
		// return if another plugin has cancelled the event
		if ( e.isCancelled() ) return;
		
		
		Double damage = e.getDamage();
		
		// if the person being attacked is a player
		if ( e.getEntity() instanceof Player ) {
			
			// put person taking damage into Player var
			Player p = ( Player ) e.getEntity();
			
			// if sneaking half damage again
			if ( p.isSneaking() ) {
				damage = damage / 2;
			}
			
		}
		
		// if the the person thats dealing damage is a player
		if ( e.getDamager() instanceof Player ) {
		    
			
			// half all player attack damage to make more balanced due to spam
			damage = damage / 2;
			
			// put person dealing damage into variable
			Player dmer = ( Player ) e.getDamager();
			
			// get the wepon and put into ItemStack var
			ItemStack wepon = dmer.getInventory().getItemInMainHand();
			
			// put the level of knockback into an int
			Integer kblevel = wepon.getEnchantmentLevel( Enchantment.KNOCKBACK );
			
			// get Entity taking damage (they might not be a player)
			Entity pl = e.getEntity();
			
			// if the person dealing damage is swimming then half damage (if you are swimming you can't swing very hard)
			if ( dmer.isSwimming() )
					damage = damage / 2;
			
			// the are trying to push a player
			if ( dmer.isSneaking() ) {
				
				// if they are sneaking then deal push knockback and cancel event so there is no damage
				pl.setVelocity(dmer.getLocation().getDirection().multiply(0.7).setY(0.4));
				// cancel event so that there is no damage
				e.setCancelled(true);
				
				
			} 
			
			// damager is not sneaking so deal damage and also apply knockback
			else {
				
				
				// if player is holding axe deal knockback to them (push them away from the person they are attacking)
				// and also give the person being attacked a little push (to make axes not as powerful)
				if ( istool.isaxe(wepon) ) {
					
					
					// if the axe has no knockback don't apply it
					if ( kblevel == 0 ) {
						
						// deal knockback to damager
						Bukkit.getScheduler().runTaskLater(
								this, () -> dmer.setVelocity(dmer.getLocation().getDirection().multiply(-0.1)
										.setY(0)), 1l);
						
					} 
					
					// there is knockback on axe
					else {
						
						// deal knockback to victim
						Bukkit.getScheduler().runTaskLater(
								this, () -> pl.setVelocity(dmer.getLocation().getDirection()
										.multiply(0.1 * kblevel).setY(0.4)), 1l);
						
						// deal knockback to damager
						Bukkit.getScheduler().runTaskLater(
								this, () -> dmer.setVelocity(dmer.getLocation().getDirection().multiply(-0.1)
										.setY(0)), 1l);
						
					}
					
				}
				
				// wepon is hoe deal negative knockback to victim
				else if ( istool.ishoe( wepon ) ) {
					
					// ignore knocback enchant for now
					
					// deal negative knockback to victim
					Bukkit.getScheduler().runTaskLater(
							this, () -> pl.setVelocity(dmer.getLocation().getDirection().multiply(-0.05)
									.setY(0)), 1l);
					
					
				}
				
				// the attacker is holding a non axe or hoe wepon. Deal knockback to person being hit
				else if (!((istool.isaxe(wepon)) && (istool.ishoe(wepon)))) {
					
					if ( kblevel == 0 ) {
						
						// deal knockback to victim
						Bukkit.getScheduler().runTaskLater(
								this, () -> pl.setVelocity(dmer.getLocation().getDirection().multiply(0.4)
										.setY(0.4)), 1l);
						
					} 
					
					// there is knockback on wepon
					else {
						
						// deal knockback * ench level
						Bukkit.getScheduler().runTaskLater(
								this, () -> pl.setVelocity(dmer.getLocation().getDirection().multiply(0.4 * kblevel)
										.setY(0.4)), 1l);
						
						
					}
					
				}
				
			}
			
		}
		    
			// after all the damage modifications set the damage amount
			e.setDamage(damage);
		}
	
	
	
	@EventHandler
    public void playerJoin( PlayerJoinEvent event ) {
		
		// when a player joins set their attack speed
        for ( String key : getConfig().getKeys( false ) )
        {
            AttributeInstance instance = event.getPlayer().getAttribute( Attribute.valueOf( key ) );
            if ( instance != null )
            {
                instance.setBaseValue( getConfig().getDouble( key, instance.getBaseValue() ) );
            }
        }
        // end for loop
        
    }
	
	
	// info command only
	public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args ) {
		
		if ( label.equalsIgnoreCase( "bettercombat" ) ) {
			sender.sendMessage( ChatColor.GREEN + "BetterCombat by CoPokBl is enabled!" );
		}
		
		return true;
	}
	
	
	
	
	
	
	
	
	// testing (rod knockback)
	
	@EventHandler(priority = EventPriority.HIGHEST)
	  public void onRodLand(ProjectileHitEvent e) {
	    Entity hitEntity;
	    Projectile projectile = e.getEntity();
	    World world = projectile.getWorld();
	    if (!isEnabled(world))
	      return; 
	    if (e.getEntityType() != EntityType.FISHING_HOOK)
	      return; 
	    boolean knockbackNonPlayerEntities = isSettingEnabled("knockbackNonPlayerEntities");
	    try {S
	      hitEntity = e.getHitEntity();
	    } catch (NoSuchMethodError e1) {
	      hitEntity = world.getNearbyEntities(projectile.getLocation(), 0.25D, 0.25D, 0.25D).stream().filter(entity -> (!knockbackNonPlayerEntities && entity instanceof Player)).findFirst().orElse(null);
	    } 
	    if (hitEntity == null)
	      return; 
	    if (!(hitEntity instanceof LivingEntity))
	      return; 
	    if (!knockbackNonPlayerEntities && !(hitEntity instanceof Player))
	      return; 
	    if (hitEntity.hasMetadata("NPC"))
	      return; 
	    FishHook hook = (FishHook)projectile;
	    Player rodder = (Player)hook.getShooter();
	    if (!knockbackNonPlayerEntities) {
	      Player player = (Player)hitEntity;
	      debug("You were hit by a fishing rod!", (CommandSender)player);
	      if (player.equals(rodder))
	        return; 
	      if (player.getGameMode() == GameMode.CREATIVE)
	        return; 
	    } 
	    LivingEntity livingEntity = (LivingEntity)hitEntity;
	    if (livingEntity.getNoDamageTicks() > livingEntity.getMaximumNoDamageTicks() / 2.0F)
	      return; 
	    double damage = module().getDouble("damage");
	    if (damage < 0.0D)
	      damage = 0.2D; 
	    EntityDamageEvent event = makeEvent(rodder, hitEntity, damage);
	    Bukkit.getPluginManager().callEvent((Event)event);
	    if (module().getBoolean("checkCancelled") && event.isCancelled()) {
	      if (this.plugin.getConfig().getBoolean("debug.enabled")) {
	        debug("You can't do that here!", (CommandSender)rodder);
	        HandlerList hl = event.getHandlers();
	        for (RegisteredListener rl : hl.getRegisteredListeners())
	          debug("Plugin Listening: " + rl.getPlugin().getName(), (CommandSender)rodder); 
	      } 
	      return;
	    } 
	    livingEntity.damage(damage);
	    livingEntity.setVelocity(calculateKnockbackVelocity(livingEntity.getVelocity(), livingEntity.getLocation(), hook.getLocation()));
	  }
	  
	  private Vector calculateKnockbackVelocity(Vector currentVelocity, Location player, Location hook) {
	    double xDistance = hook.getX() - player.getX();
	    double zDistance = hook.getZ() - player.getZ();
	    while (xDistance * xDistance + zDistance * zDistance < 1.0E-4D) {
	      xDistance = (Math.random() - Math.random()) * 0.01D;
	      zDistance = (Math.random() - Math.random()) * 0.01D;
	    } 
	    double distance = Math.sqrt(xDistance * xDistance + zDistance * zDistance);
	    double y = currentVelocity.getY() / 2.0D;
	    double x = currentVelocity.getX() / 2.0D;
	    double z = currentVelocity.getZ() / 2.0D;
	    x -= xDistance / distance * 0.4D;
	    y += 0.4D;
	    z -= zDistance / distance * 0.4D;
	    if (y >= 0.4D)
	      y = 0.4D; 
	    return new Vector(x, y, z);
	  }
	  
	  @EventHandler(priority = EventPriority.HIGHEST)
	  private void onReelIn(PlayerFishEvent e) {
	    if (e.getState() != PlayerFishEvent.State.CAUGHT_ENTITY)
	      return; 
	    String cancelDraggingIn = module().getString("cancelDraggingIn");
	    boolean isPlayer = e.getCaught() instanceof org.bukkit.entity.HumanEntity;
	    if ((cancelDraggingIn.equals("players") && isPlayer) || (cancelDraggingIn
	      .equals("mobs") && !isPlayer) || cancelDraggingIn
	      .equals("all")) {
	      ((Entity)this.hookEntityFeature.apply(e)).remove();
	      e.setCancelled(true);
	    } 
	  }
	
	

}
