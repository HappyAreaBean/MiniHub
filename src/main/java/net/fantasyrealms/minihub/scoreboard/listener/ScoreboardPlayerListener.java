package net.fantasyrealms.minihub.scoreboard.listener;

import lombok.RequiredArgsConstructor;
import net.fantasyrealms.minihub.scoreboard.manager.ScoreboardManager;
import net.fantasyrealms.minihub.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class ScoreboardPlayerListener implements Listener {
    private final ScoreboardManager manager;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();
        var world = player.getWorld();

        if (!Utils.isEnabledWorlds(world)) return;

        manager.createScoreboard(player);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        var player = event.getPlayer();

        manager.removeScoreboard(player.getUniqueId());
    }

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent event) {
        var player = event.getPlayer();
        var world = player.getWorld();

        if (Utils.isEnabledWorlds(world)) {
            manager.createScoreboard(player);
        } else {
            manager.removeScoreboard(player.getUniqueId());
        }
    }

}
