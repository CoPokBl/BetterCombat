package me.CoPokBl.bettercombat;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class OnDamage implements Listener {

    public static Main plugin;

    @EventHandler( priority = EventPriority.HIGHEST )
    public void DamageEvent(EntityDamageByEntityEvent e) {

        // return if another plugin has cancelled the event
        if ( e.isCancelled() ) return;

        // Get the amount of damage
        double damage = e.getDamage();

        // if the person being attacked is a player
        if (e.getEntity() instanceof Player) {

            // p is the person taking damage
            Player p = (Player) e.getEntity();

            // if sneaking half damage again
            if ( p.isSneaking() ) {
                damage = damage / 2;
            }

        }

        // if the the person thats dealing damage is a player
        if (e.getDamager() instanceof Player) {

            Player dmer = (Player) e.getDamager();

            // half all player attack damage to make more balanced due to spam
            damage = damage / 2;

            // put person dealing damage into variable

            // get the weapon and put into ItemStack var
            ItemStack weapon = dmer.getInventory().getItemInMainHand();

            // put the level of knockback into an int
            int kbLevel = weapon.getEnchantmentLevel( Enchantment.KNOCKBACK );

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
                if ( isTool.isAxe(weapon) ) {


                    // if the axe has no knockback don't apply it
                    if ( kbLevel != 0 ) {

                        // deal knockback to victim
                        Bukkit.getScheduler().runTaskLater(
                                plugin, () -> pl.setVelocity(dmer.getLocation().getDirection()
                                        .multiply(0.1 * kbLevel).setY(0.4)), 1L);

                    }

                    // deal knockback to damager
                    Bukkit.getScheduler().runTaskLater(
                            plugin, () -> dmer.setVelocity(dmer.getLocation().getDirection().multiply(-0.1)
                                    .setY(0)), 1L);

                }

                // weapon is hoe deal negative knockback to victim
                else if ( isTool.isHoe( weapon ) ) {

                    // ignore knocback enchant for now

                    // deal negative knockback to victim
                    Bukkit.getScheduler().runTaskLater(
                            plugin, () -> pl.setVelocity(dmer.getLocation().getDirection().multiply(-0.05)
                                    .setY(0)), 1L);


                }

                // the attacker is holding a non axe or hoe weapon. Deal knockback to person being hit
                else if (! ( (isTool.isAxe( weapon )) && (isTool.isHoe( weapon )) ) ) {

                    if ( kbLevel == 0 ) {

                        // deal knockback to victim
                        Bukkit.getScheduler().runTaskLater(
                                plugin, () -> pl.setVelocity(dmer.getLocation().getDirection().multiply(0.4)
                                        .setY(0.4)), 1L);

                    }

                    // there is knockback on weapon
                    else {

                        // deal knockback * ench level
                        Bukkit.getScheduler().runTaskLater(
                                plugin, () -> pl.setVelocity(dmer.getLocation().getDirection().multiply(0.4 * kbLevel)
                                        .setY(0.4)), 1L);


                    }

                }

            }

        }

        // after all the damage modifications set the damage amount
        e.setDamage(damage);
    }

}
