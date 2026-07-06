package net.fantasyrealms.minihub.utils;

import lombok.experimental.UtilityClass;
import net.fantasyrealms.minihub.common.Constants;
import net.kyori.adventure.text.Component;

@UtilityClass
public class MessageUtil {

    public static Component commandPrefix(String message) {
        return Constants.COMMAND_PREFIX.appendSpace().append(Utils.MINI_MESSAGE.deserialize(message));
    }

}
