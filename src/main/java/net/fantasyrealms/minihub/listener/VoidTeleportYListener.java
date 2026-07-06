package net.fantasyrealms.minihub.listener;

import net.fantasyrealms.minihub.MiniHub;
import net.fantasyrealms.minihub.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class VoidTeleportYListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        var config = MiniHub.INSTANCE.configManager().config().config();
        var player = event.getPlayer();
        var world = player.getWorld();

        if (!Utils.isEnabledWorlds(world)) return;
        if (!config.feature().voidTeleportY()) return;
        if (!event.hasChangedBlock()) return;

        int y = config.feature().voidTeleportYValue();
        var to = event.getTo();

        if (to.getBlockY() <= y) {
            Utils.teleportToSpawn(player);
        }
    }

}
