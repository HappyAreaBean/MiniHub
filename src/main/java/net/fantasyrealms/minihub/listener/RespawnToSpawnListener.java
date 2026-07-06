package net.fantasyrealms.minihub.listener;

import net.fantasyrealms.minihub.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnToSpawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        var player = event.getPlayer();

        Utils.teleportToSpawn(player);
    }

}
