package net.fantasyrealms.minihub.scoreboard.manager;

import fr.mrmicky.fastboard.adventure.FastBoard;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.fantasyrealms.minihub.MiniHub;
import net.fantasyrealms.minihub.scoreboard.listener.ScoreboardPlayerListener;
import net.fantasyrealms.minihub.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Accessors(fluent = true)
@RequiredArgsConstructor
public class ScoreboardManager {

    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private final MiniHub plugin;
    private BukkitTask updateTask;

    public void init() {
        updateTask = new BukkitRunnable() {
            @Override
            public void run() {

                boards.forEach((uuid, board) -> {
                    var player = Bukkit.getPlayer(uuid);

                    if (player == null) {
                        removeScoreboard(uuid);
                        return;
                    }

                    var config = plugin.configManager().scoreboard().config();
                    board.updateTitle(MessageUtil.componentPAPI(config.title(), player));
                    board.updateLines(MessageUtil.componentListPAPI(config.lines(), player));
                });

            }
        }.runTaskTimerAsynchronously(plugin, 0, plugin.configManager().scoreboard().config().updateInterval());
    }

    public void registerListeners() {
        var pm = plugin.getServer().getPluginManager();

        if (plugin.configManager().scoreboard().config().enable())
            pm.registerEvents(new ScoreboardPlayerListener(this), plugin);
    }

    public void cancel() {
        if (updateTask != null) updateTask.cancel();
    }

    public void recreate() {
        cancel();
        init();
    }

    public void createScoreboard(Player player) {
        if (boards.containsKey(player.getUniqueId())) return;
        boards.put(player.getUniqueId(), new FastBoard(player));
    }

    public void removeScoreboard(Player player) {
        removeScoreboard(player.getUniqueId());
    }

    public void removeScoreboard(UUID uuid) {
        var board = getBoard(uuid);
        if (board != null) {
            boards.remove(uuid);
            board.delete();
        }
    }

    public FastBoard getBoard(UUID uuid) {
        return boards.get(uuid);
    }

}