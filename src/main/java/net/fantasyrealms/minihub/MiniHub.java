package net.fantasyrealms.minihub;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.fantasyrealms.minihub.common.Constants;
import net.fantasyrealms.minihub.config.ConfigManager;
import net.fantasyrealms.minihub.config.commands.MiniHubCommand;
import net.fantasyrealms.minihub.listener.BlockBreakListener;
import net.fantasyrealms.minihub.listener.BlockPlaceListener;
import net.fantasyrealms.minihub.listener.ExplosionListener;
import net.fantasyrealms.minihub.listener.GameModeOnJoinListener;
import net.fantasyrealms.minihub.listener.HungerChangeListener;
import net.fantasyrealms.minihub.listener.MaxHungerJoinListener;
import net.fantasyrealms.minihub.listener.PlayerDamageListener;
import net.fantasyrealms.minihub.listener.PlayerDamagePlayerListener;
import net.fantasyrealms.minihub.listener.PlayerInteractListener;
import net.fantasyrealms.minihub.listener.RegionExitListener;
import net.fantasyrealms.minihub.listener.RespawnToSpawnListener;
import net.fantasyrealms.minihub.listener.SpawnOnJoinListener;
import net.fantasyrealms.minihub.listener.VoidTeleportYListener;
import net.fantasyrealms.minihub.listener.WeatherChangeListener;
import net.fantasyrealms.minihub.scoreboard.manager.ScoreboardManager;
import net.fantasyrealms.minihub.utils.Cuboid;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitLamp;

@Getter
@Accessors(fluent = true)
public final class MiniHub extends JavaPlugin {
    public static MiniHub INSTANCE;

    private ConfigManager configManager;
    private ScoreboardManager scoreboardManager;
    private Cuboid cuboid;

    @Override
    public void onEnable() {
        INSTANCE = this;

        printLogo();
        getSLF4JLogger().info("Welcome to MiniHub! :)");

        var lampBuilder = BukkitLamp.builder(this);
        var lamp = lampBuilder.build();
        lamp.register(new MiniHubCommand());

        configManager = new ConfigManager(this);
        scoreboardManager = new ScoreboardManager(this);
        scoreboardManager.init();

        setupCuboid();
        registerListeners();
    }

    private void printLogo() {
        Constants.LOGO.forEach(s -> {
            getServer().getConsoleSender().sendMessage(Component.text(s).color(TextColor.fromCSSHexString("#39e1e4")));
        });
    }

    public void setupCuboid() {
        var config = configManager.config().config();
        var corner1 = config.regionBorder().corner1();
        var corner2 = config.regionBorder().corner2();

        if (corner1 == null || corner2 == null) return;

        cuboid = new Cuboid(corner1, corner2);
    }

    public void registerListeners() {
        var config = configManager.config().config();
        var feature = config.feature();
        var pm = Bukkit.getPluginManager();

        if (feature.disablePvp())
            pm.registerEvents(new PlayerDamagePlayerListener(), this);

        if (feature.preventBlockPlace())
            pm.registerEvents(new BlockPlaceListener(), this);

        if (feature.preventBlockBreak())
            pm.registerEvents(new BlockBreakListener(), this);

        if (feature.preventPlayerTakeDamage())
            pm.registerEvents(new PlayerDamageListener(), this);

        if (feature.preventAllInteract())
            pm.registerEvents(new PlayerInteractListener(), this);

        if (feature.instantExtinguishFire())
            pm.registerEvents(new ExplosionListener(), this);

        if (feature.voidTeleportY())
            pm.registerEvents(new VoidTeleportYListener(), this);

        if (feature.preventNaturalWeatherChange())
            pm.registerEvents(new WeatherChangeListener(), this);

        if (feature.preventExplosion())
            pm.registerEvents(new ExplosionListener(), this);

        if (feature.noHungerChange())
            pm.registerEvents(new HungerChangeListener(), this);

        if (feature.maxHungerOnJoin())
            pm.registerEvents(new MaxHungerJoinListener(), this);

        if (feature.gameModeOnJoin())
            pm.registerEvents(new GameModeOnJoinListener(), this);

        if (config.regionBorder().enable())
            pm.registerEvents(new RegionExitListener(), this);

        if (config.spawnOnJoin())
            pm.registerEvents(new SpawnOnJoinListener(), this);

        if (config.respawnToSpawn())
            pm.registerEvents(new RespawnToSpawnListener(), this);

        scoreboardManager.registerListeners();
    }

    public void unregisterAllListeners() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean isPAPI() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }
}
