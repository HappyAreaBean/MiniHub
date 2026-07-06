package net.fantasyrealms.minihub.utils;

import net.fantasyrealms.minihub.MiniHub;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Utils {

    public static MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public static boolean isEnabledWorlds(World world) {
        var config = MiniHub.INSTANCE.configManager().config();

        return config.config().enableWorlds().contains(world.getName());
    }

    /**
     * Gets the spawn location from config, with first world on server as fallback if not set.
     * @return The spawn location from config, or the first world's spawn location if not configured
     */
    public static Location getSpawnLocation() {
        var config = MiniHub.INSTANCE.configManager().config().config();
        var spawn = config.spawnLocation();

        if (spawn == null) {
            return Bukkit.getWorlds().getFirst().getSpawnLocation();
        }

        return spawn;
    }

    /**
     * Teleport to spawn location in the config, with first world on server as fallback
     * @param player The player to teleport to
     */
    public static void teleportToSpawn(Player player) {
        player.teleport(getSpawnLocation());
    }

}


