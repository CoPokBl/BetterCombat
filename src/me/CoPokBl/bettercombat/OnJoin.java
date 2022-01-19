package me.CoPokBl.bettercombat;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

    public static Main plugin;

    @EventHandler
    public void playerJoin( PlayerJoinEvent event ) {

        // when a player joins set their attack speed
        for ( String key : plugin.getConfig().getKeys( false ) ) {

            if (key.equals("worlds"))
                continue;

            AttributeInstance instance = event.getPlayer().getAttribute( Attribute.valueOf( key ) );

            if ( instance != null ) {
                instance.setBaseValue( plugin.getConfig().getDouble( key, instance.getBaseValue() ) );
            }

        }
        // end for loop

    }

}
