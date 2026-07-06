package net.fantasyrealms.minihub.listener;

import net.fantasyrealms.minihub.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class ExplosionListener implements Listener {

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        var world = event.getEntity().getWorld();

        if (!Utils.isEnabledWorlds(world)) return;

        event.setCancelled(true);
    }

}
