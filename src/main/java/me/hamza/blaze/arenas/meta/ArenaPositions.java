package me.hamza.blaze.arenas.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

/**
 * @author Hammzar
 * @since 15.04.2025
 */
@Getter
@Setter
public class ArenaPositions {

    private Location spawnBlue;
    private Location spawnRed;

    private Location corner1;
    private Location corner2;

    public  ArenaPositions(Location cSpawnBlue, Location cSpawnRed, Location cCorner1, Location cCorner2) {
        spawnBlue = cSpawnBlue;
        spawnRed = cSpawnRed;
        corner1 = cCorner1;
        corner2 = cCorner2;
    }

}
