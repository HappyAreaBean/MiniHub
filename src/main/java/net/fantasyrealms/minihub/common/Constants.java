package net.fantasyrealms.minihub.common;

import net.fantasyrealms.minihub.utils.Utils;
import net.kyori.adventure.text.Component;

import java.util.List;

public final class Constants {
    public Constants() {
    }

    public static final List<String> LOGO = List.of(
            "  __  __ _      _ _  _      _    ",
            " |  \\/  (_)_ _ (_) || |_  _| |__ ",
            " | |\\/| | | ' \\| | __ | || | '_ \\",
            " |_|  |_|_|_||_|_|_||_|\\_,_|_.__/",
            "                                 "
    );


    public static final Component COMMAND_PREFIX = Utils.MINI_MESSAGE.deserialize("<dark_gray>[</dark_gray><b><gradient:#39e1e4:#69dee4>MiniHub</b><dark_gray>]</dark_gray>");

}
