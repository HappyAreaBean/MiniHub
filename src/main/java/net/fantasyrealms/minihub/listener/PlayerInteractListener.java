package net.fantasyrealms.minihub.listener;

import net.fantasyrealms.minihub.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        var world = event.getPlayer().getWorld();
        var player = event.getPlayer();

        if (!Utils.isEnabledWorlds(world)) return;
        if (player.getGameMode() == GameMode.CREATIVE) return;

        event.setCancelled(true);
    }
}
