package net.fantasyrealms.minihub.utils;

import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import net.fantasyrealms.minihub.MiniHub;
import net.fantasyrealms.minihub.common.Constants;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

import static net.fantasyrealms.minihub.utils.Utils.MINI_MESSAGE;

@UtilityClass
public class MessageUtil {

    public static Component commandPrefix(String message) {
        return Constants.COMMAND_PREFIX.appendSpace().append(MINI_MESSAGE.deserialize(message));
    }

    public static Component componentPAPI(String string, Player player) {
        if (MiniHub.INSTANCE.isPAPI())
            string = PlaceholderAPI.setPlaceholders(player, string);

        return MINI_MESSAGE.deserialize(string);
    }

    public static List<Component> componentListPAPI(List<String> strings, Player player) {
        return strings.stream()
                .map(s -> MiniHub.INSTANCE.isPAPI() ? PlaceholderAPI.setPlaceholders(player, s) : s)
                .map(MINI_MESSAGE::deserialize)
                .toList();
    }

}
