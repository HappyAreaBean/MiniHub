package net.fantasyrealms.minihub.listener;

import net.fantasyrealms.minihub.MiniHub;
import net.fantasyrealms.minihub.utils.Utils;
import net.kyori.adventure.util.TriState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        var cause = e.getCause();
        var entity = e.getEntity();
        var world = entity.getWorld();

        if (!Utils.isEnabledWorlds(world)) return;
        if (!(entity instanceof Player player)) return;
        var config = MiniHub.INSTANCE.configManager().config().config();

        switch (cause) {
            case ENTITY_ATTACK, ENTITY_SWEEP_ATTACK, ENTITY_EXPLOSION -> {
                var source = e.getDamageSource();
                var causing = source.getCausingEntity();

                if (config.feature().disablePvp() && causing instanceof Player) {
                    e.setCancelled(false);
                    return;
                }
            }
            case FIRE_TICK -> {
                if (!player.isInLava() && config.feature().instantExtinguishFire()) {
                    player.setFireTicks(0);
                    player.setVisualFire(TriState.FALSE);
                }
            }
            case VOID -> {
                if (config.feature().voidTeleport()) {
                    Utils.teleportToSpawn(player);
                }
            }
        }

        e.setCancelled(true);
    }

}
