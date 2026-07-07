package net.fantasyrealms.minihub.scoreboard;

import revxrsal.spec.annotation.Comment;
import revxrsal.spec.annotation.ConfigSpec;
import revxrsal.spec.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@ConfigSpec
public interface ScoreboardConfig {

    @Order(1000)
    @Comment("Scoreboard title")
    default String title() {
        return "<b><gradient:#39e1e4:#69dee4>MiniHub</b>";
    }

    @Order(2000)
    @Comment("Scoreboard lines")
    default List<String> lines() {
        return initLines();
    }

    private List<String> initLines() {
        var list = new ArrayList<String>();

        list.add("");
        list.add("<white>ᴡᴇʟᴄᴏᴍᴇ ᴛᴏ ᴍɪɴɪʜᴜʙ");
        list.add("");
        list.add("<color:gray>● <white>Name:</white> %player_name%");
        list.add("<color:gray>● <white>Rank:</white> %luckperms_primary_group_name%");
        list.add("");
        list.add("<color:#7bbcfc>● <white>Lobby:</white> ♯1");
        list.add("<color:#7bbcfc>● <white>Players:</white> %bungee_total%");
        list.add("");
        list.add("<gray>  %server_time_yyyy.MM.dd% %server_time_HH:mm%");
        list.add("     <aqua>ᴍɪɴɪʜᴜʙ.ɴᴇᴛ");

        return list;
    }

    @Order(3000)
    @Comment("Scoreboard update interval, in ticks (20 ticks = 1 second)")
    default int updateInterval() {
        return 20;
    }

    @Order(4000)
    @Comment("Enable scoreboard?")
    default boolean enable() {
        return true;
    }
}
