package net.fantasyrealms.minihub.listener;

import net.fantasyrealms.minihub.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        var world = event.getWorld();

        if (!Utils.isEnabledWorlds(world)) return;
        if (event.getCause() != WeatherChangeEvent.Cause.NATURAL) return; // Ignore if it is not natural
        
        event.setCancelled(true);
    }

}
