package net.fantasyrealms.minihub.config.commands;

import net.fantasyrealms.minihub.MiniHub;
import net.fantasyrealms.minihub.utils.CommandUtils;
import net.fantasyrealms.minihub.utils.MessageUtil;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.CommandPlaceholder;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Range;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.help.Help;

@Command("minihub")
@CommandPermission("minihub.admin")
public class MiniHubCommand {

    @CommandPlaceholder
    @Description("MiniHub commands help")
    public void help(
            BukkitCommandActor actor,
            @Range(min = 1) @Default("1") int page,
            Help.RelatedCommands<BukkitCommandActor> commands
    ) {
        CommandUtils.handleHelpMenu(actor, page, commands, 8, null, "/minihub %s");
    }

    @Subcommand("reload")
    @Description("Reload MiniHub config")
    public void reload(BukkitCommandActor actor) {
        MiniHub.INSTANCE.configManager().reloadAll();
        actor.reply(MessageUtil.commandPrefix("<green>Reloaded MiniHub config!"));
    }

    @Subcommand("reloadlistener")
    @Description("Attempt to re-register listener, useful when some of the features are toggled after config update")
    public void reloadListener(BukkitCommandActor actor) {
        MiniHub.INSTANCE.unregisterAllListeners();
        MiniHub.INSTANCE.registerListeners();
        actor.reply(MessageUtil.commandPrefix("<green>Re-registered MiniHub listeners!"));
    }

    @Subcommand("updatecuboid")
    @Description("Attempt to update cuboid, useful when new corner set")
    public void updateCuboid(BukkitCommandActor actor) {
        MiniHub.INSTANCE.setupCuboid();
        actor.reply(MessageUtil.commandPrefix("<green>Recreating Cuboid!"));
    }

    @Subcommand("setspawn")
    @Description("Set the spawn location for MiniHub")
    public void setSpawn(BukkitCommandActor actor) {
        var player = actor.requirePlayer();
        var location = player.getLocation();

        var config = MiniHub.INSTANCE.configManager().config();
        config.config().setSpawnLocation(location);
        config.saveAndReload();

        actor.reply(MessageUtil.commandPrefix("<green>Spawn location has been set and saved!"));
    }

    @Subcommand("spawn")
    @Description("Teleport you to the MiniHub spawn location")
    public void spawn(BukkitCommandActor actor) {
        var player = actor.requirePlayer();
        var config = MiniHub.INSTANCE.configManager().config();
        var spawn = config.config().spawnLocation();

        if (spawn == null) {
            actor.reply(MessageUtil.commandPrefix("<color:#ffd900>Spawn location has not been set, go set one!"));
            return;
        }

        player.teleport(spawn);
        actor.reply(MessageUtil.commandPrefix("<green>Teleported you to the spawn!"));
    }

    @Subcommand("corner")
    @Description("Set the corner for region border")
    public void corner(BukkitCommandActor actor, Corner corner) {
        var player = actor.requirePlayer();
        var location = player.getLocation();

        var config = MiniHub.INSTANCE.configManager().config();
        switch (corner) {
            case CORNER_1 -> config.config().regionBorder().setCorner1(location);
            case CORNER_2 -> config.config().regionBorder().setCorner2(location);
        }
        config.saveAndReload();

        actor.reply(MessageUtil.commandPrefix("<green>%s location has been set and saved!".formatted(corner.name())));
    }

    public enum Corner {
        CORNER_1,
        CORNER_2
    }

}
