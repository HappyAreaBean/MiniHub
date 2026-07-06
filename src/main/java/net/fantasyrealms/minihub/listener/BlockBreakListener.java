package net.fantasyrealms.minihub.listener;

import net.fantasyrealms.minihub.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        var world = event.getBlock().getWorld();
        var player = event.getPlayer();

        if (!Utils.isEnabledWorlds(world)) return;
        if (player.getGameMode() == GameMode.CREATIVE) return;

        event.setCancelled(true);
    }

}
