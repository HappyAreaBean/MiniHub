package net.fantasyrealms.minihub.listener;

import net.fantasyrealms.minihub.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerChangeListener implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        var entity = event.getEntity();
        var world = entity.getWorld();

        if (!Utils.isEnabledWorlds(world)) return;

        if (entity instanceof Player player) {
            if (player.getGameMode() == GameMode.CREATIVE) return;

            event.setCancelled(true);
        }
    }

}
