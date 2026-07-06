package net.fantasyrealms.minihub.listener;

import net.fantasyrealms.minihub.MiniHub;
import net.fantasyrealms.minihub.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class RegionExitListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        var config = MiniHub.INSTANCE.configManager().config().config();
        var cuboid = MiniHub.INSTANCE.cuboid();
        var player = event.getPlayer();
        var world = player.getWorld();

        if (!Utils.isEnabledWorlds(world)) return;
        if (!config.regionBorder().enable()) return;
        if (cuboid == null) return;
        if (config.regionBorder().allowExit()) return;
        if (!event.hasChangedBlock()) return;
        if (player.getGameMode() == GameMode.CREATIVE) return;
        if (player.getGameMode() == GameMode.SPECTATOR) return;

        if (!cuboid.isIn(player))
            Utils.teleportToSpawn(player);
    }

}
