package net.fantasyrealms.minihub.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.fantasyrealms.minihub.MiniHub;
import net.fantasyrealms.minihub.config.typeadapter.impl.LocationTypeAdapter;
import net.fantasyrealms.minihub.scoreboard.ScoreboardConfig;
import org.bukkit.Location;
import revxrsal.spec.SpecAdapterFactory;

import java.io.File;

@Getter
@Accessors(fluent = true)
public final class ConfigManager {

    public static Gson GSON = new GsonBuilder()
            .registerTypeAdapterFactory(SpecAdapterFactory.INSTANCE)
            .registerTypeAdapter(Location.class, new LocationTypeAdapter().nullSafe())
            .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
            .setPrettyPrinting()
            .create();
    private final SpecConfig<MiniHubConfig> config;
    private final SpecConfig<ScoreboardConfig> scoreboard;

    public ConfigManager(MiniHub plugin) {
        this.config = SpecConfig.create(MiniHubConfig.class, new File(plugin.getDataFolder(), "config.yml"), GSON);
        this.scoreboard = SpecConfig.create(ScoreboardConfig.class, new File(plugin.getDataFolder(), "scoreboard.yml"), GSON);

        init(plugin);
        reloadAll();
    }

    public void reloadAll() {
        config.reload();
        scoreboard.reload();
    }

    public void init(MiniHub plugin) {
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();

        createIfNotExist(config);
        createIfNotExist(scoreboard);
    }

    private void createIfNotExist(SpecConfig<?> spec) {
        var file = spec.file();

        if (!file.exists())
            spec.saveAndReload();
        else
            spec.loadAndSave();
    }

}