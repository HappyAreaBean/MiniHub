package net.fantasyrealms.minihub.config.typeadapter.impl;


import net.fantasyrealms.minihub.config.typeadapter.CustomTypeAdapter;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LocationTypeAdapter extends CustomTypeAdapter<Location> {

    @NotNull
    @Override
    public Map<Object, Object> serialize(@NotNull Location location) {
        Map<Object, Object> map = new HashMap<>();

        map.put("world", location.getWorld().getName());
        map.put("x", location.x());
        map.put("y", location.y());
        map.put("z", location.z());
        map.put("pitch", location.getPitch());
        map.put("yaw", location.getYaw());

        return map;
    }

    @NotNull
    @Override
    public Location deserialize(@NotNull Map<Object, Object> map) {
        String worldName = (String) map.get("world");
        double x = (double) map.get("x");
        double y = (double) map.get("y");
        double z = (double) map.get("z");
        float pitch = ((Number) map.get("pitch")).floatValue();
        float yaw = ((Number) map.get("yaw")).floatValue();

        return new Location(
                org.bukkit.Bukkit.getWorld(worldName),
                x, y, z,
                yaw, pitch
        );
    }

}
