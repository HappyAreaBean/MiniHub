package net.fantasyrealms.minihub.listener;

import net.fantasyrealms.minihub.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamagePlayerListener implements Listener {

    @EventHandler
    public void onPlayerDamagePlayer(EntityDamageByEntityEvent e) {
        var damager = e.getDamager();
        var entity = e.getEntity();
        var world = damager.getWorld();

        if (!Utils.isEnabledWorlds(world)) return;
        if (!(damager instanceof Player && entity instanceof Player)) return;

        e.setCancelled(true);
    }

}
