package net.fantasyrealms.minihub.listener;

import net.fantasyrealms.minihub.MiniHub;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GameModeOnJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();
        var gameMode = MiniHub.INSTANCE.configManager().config().config().feature().gameModeOnJoinValue();

        player.setGameMode(gameMode);
    }

}
