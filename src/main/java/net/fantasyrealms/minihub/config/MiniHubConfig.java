package net.fantasyrealms.minihub.config;

import org.bukkit.GameMode;
import org.bukkit.Location;
import revxrsal.spec.annotation.Comment;
import revxrsal.spec.annotation.ConfigSpec;
import revxrsal.spec.annotation.Order;

import java.util.List;

@ConfigSpec
public interface MiniHubConfig {

    @Order(1000)
    Location spawnLocation();

    void setSpawnLocation(Location location);

    @Order(1100)
    default boolean spawnOnJoin() {
        return true;
    }

    @Order(1200)
    default boolean respawnToSpawn() {
        return true;
    }

    @Order(2000)
    List<String> enableWorlds();

    void setEnableWorlds(List<String> worlds);

    @Order(3000)
    Feature feature();

    @ConfigSpec
    interface Feature {

        @Order(100)
        default boolean disablePvp() {
            return true;
        }

        @Order(200)
        default boolean preventBlockPlace() {
            return true;
        }

        @Order(300)
        default boolean preventBlockBreak() {
            return true;
        }

        @Order(310)
        default boolean preventExplosion() {
            return true;
        }

        @Order(400)
        @Comment({
                "Should the player take any damage"
        })
        default boolean preventPlayerTakeDamage() {
            return true;
        }

        @Order(500)
        default boolean preventAllInteract() {
            return true;
        }

        @Order(600)
        default boolean instantExtinguishFire() {
            return true;
        }

        @Order(700)
        @Comment({
                "Teleport player back to spawn if player receive void damage"
        })
        default boolean voidTeleport() {
            return true;
        }

        @Order(710)
        @Comment({
                "Teleport player back to spawn if it below or at Y level",
                "Set the Y level below."
        })
        default boolean voidTeleportY() {
            return false;
        }

        @Order(720)
        @Comment({
                "The Y level to teleport player back to spawn",
        })
        default int voidTeleportYValue() {
            return 60;
        }

        @Order(800)
        default boolean preventNaturalWeatherChange() {
            return true;
        }

        @Order(900)
        default boolean noHungerChange() {
            return true;
        }

        @Order(1000)
        default boolean maxHungerOnJoin() {
            return true;
        }

        @Order(1100)
        default boolean gameModeOnJoin() {
            return true;
        }

        @Order(1110)
        default GameMode gameModeOnJoinValue() {
            return GameMode.ADVENTURE;
        }

        void setDisablePvp(boolean disablePvp);

        void setPreventBlockPlace(boolean preventBlockPlace);

        void setPreventBlockBreak(boolean preventBlockBreak);

        void setPreventPlayerTakeDamage(boolean preventPlayerTakeDamage);

        void setPreventAllInteract(boolean preventAllInteract);

        void setInstantExtinguishFire(boolean instantExtinguishFire);

        void setVoidTeleport(boolean voidTeleport);

        void setVoidTeleportY(boolean voidTeleportY);

        void setVoidTeleportYValue(int voidTeleportYValue);

        void setPreventNaturalWeatherChange(boolean preventNaturalWeatherChange);

    }

    @Order(4000)
    RegionBorder regionBorder();

    @ConfigSpec
    interface RegionBorder {

        @Order(0)
        default boolean enable() {
            return false;
        }

        @Order(100)
        Location corner1();

        @Order(200)
        Location corner2();

        @Order(300)
        boolean useWorldBorder();

        @Order(400)
        @Comment("Recommend to disable this if using world border.")
        boolean allowExit();

        void setCorner1(Location corner1);

        void setCorner2(Location corner2);

        void setUseWorldBorder(boolean useWorldBorder);

        void setAllowExit(boolean allowExit);

    }

}
