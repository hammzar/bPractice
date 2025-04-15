package me.hamza.blaze.arenas.generator;

import lombok.Getter;
import me.hamza.blaze.arenas.Arena;
import me.hamza.blaze.arenas.meta.ArenaPositions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * @author Hammzar
 * @since 15.04.2025
 */
@Getter
public class ArenaGenerator {

    private final int PASTE_OFFSET = 10000;

    public Arena cloneArena(Arena cOriginalArena, int cArenaId) {
        Arena newArena = new Arena();
        newArena.setName(cOriginalArena.getName() + "_instance_" + cArenaId);
        newArena.setIcon(cOriginalArena.getIcon());

        ArenaPositions originalPos = cOriginalArena.getPositions();
        World world = originalPos.getCorner1().getWorld();

        int width = Math.abs(originalPos.getCorner1().getBlockX() - originalPos.getCorner2().getBlockX());
        int length = Math.abs(originalPos.getCorner1().getBlockZ() - originalPos.getCorner2().getBlockZ());
        int height = Math.abs(originalPos.getCorner1().getBlockY() - originalPos.getCorner2().getBlockY());

        Location pasteCorner1 = findSafePasteLocation(world, width, height, length, 30);
        Location pasteCorner2 = pasteCorner1.clone().add(width, height, length);

        copyRegion(originalPos.getCorner1(), originalPos.getCorner2(), pasteCorner1);

        Location newSpawnBlue = translateLocation(originalPos.getSpawnBlue(), originalPos.getCorner1(), pasteCorner1);
        Location newSpawnRed = translateLocation(originalPos.getSpawnRed(), originalPos.getCorner1(), pasteCorner1);

        ArenaPositions newPositions = new ArenaPositions(newSpawnBlue, newSpawnRed, pasteCorner1, pasteCorner2);
        newArena.setPositions(newPositions);

        return newArena;
    }

    private void copyRegion(Location cCorner1, Location cCorner2, Location cTargetCorner) {
        World world = cCorner1.getWorld();

        int minX = Math.min(cCorner1.getBlockX(), cCorner2.getBlockX());
        int minY = Math.min(cCorner1.getBlockY(), cCorner2.getBlockY());
        int minZ = Math.min(cCorner1.getBlockZ(), cCorner2.getBlockZ());

        int maxX = Math.max(cCorner1.getBlockX(), cCorner2.getBlockX());
        int maxY = Math.max(cCorner1.getBlockY(), cCorner2.getBlockY());
        int maxZ = Math.max(cCorner1.getBlockZ(), cCorner2.getBlockZ());

        for (int x = 0; x <= (maxX - minX); x++) {
            for (int y = 0; y <= (maxY - minY); y++) {
                for (int z = 0; z <= (maxZ - minZ); z++) {
                    Block source = world.getBlockAt(minX + x, minY + y, minZ + z);
                    Block target = world.getBlockAt(cTargetCorner.getBlockX() + x, minY + y, cTargetCorner.getBlockZ() + z);

                    target.setType(source.getType(), false);
                    target.setData(source.getData());
                }
            }
        }
    }

    private Location translateLocation(Location cOriginal, Location cFromCorner, Location cToCorner) {
        int dx = cOriginal.getBlockX() - cFromCorner.getBlockX();
        int dy = cOriginal.getBlockY() - cFromCorner.getBlockY();
        int dz = cOriginal.getBlockZ() - cFromCorner.getBlockZ();

        return new Location(
                cToCorner.getWorld(),
                cToCorner.getBlockX() + dx,
                cToCorner.getBlockY() + dy,
                cToCorner.getBlockZ() + dz,
                cOriginal.getYaw(),
                cOriginal.getPitch()
        );
    }

    public Location findSafePasteLocation(World cWorld, int cWidth, int cHeight, int cLength, int maxAttempts) {
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int offsetX = attempt * (cWidth + 50);
            Location tryCorner = new Location(cWorld, PASTE_OFFSET + offsetX, 50, PASTE_OFFSET);

            if (isRegionAir(tryCorner, cWidth, cHeight, cLength)) {
                return tryCorner;
            }
        }

        throw new RuntimeException("No empty space found for arena paste.");
    }

    private boolean isRegionAir(Location cCorner, int cWidth, int cHeight, int cLength) {
        World world = cCorner.getWorld();

        for (int x = 0; x <= cWidth; x++) {
            for (int y = 0; y <= cHeight; y++) {
                for (int z = 0; z <= cLength; z++) {
                    if (!world.getBlockAt(
                            cCorner.getBlockX() + x,
                            cCorner.getBlockY() + y,
                            cCorner.getBlockZ() + z
                    ).getType().equals(Material.AIR)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public void removeArenaRegion(Arena cArena) {
        ArenaPositions pos = cArena.getPositions();
        Location c1 = pos.getCorner1();
        Location c2 = pos.getCorner2();
        World world = c1.getWorld();

        int minX = Math.min(c1.getBlockX(), c2.getBlockX());
        int minY = Math.min(c1.getBlockY(), c2.getBlockY());
        int minZ = Math.min(c1.getBlockZ(), c2.getBlockZ());

        int maxX = Math.max(c1.getBlockX(), c2.getBlockX());
        int maxY = Math.max(c1.getBlockY(), c2.getBlockY());
        int maxZ = Math.max(c1.getBlockZ(), c2.getBlockZ());

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    block.setType(Material.AIR, false);
                }
            }
        }
    }

}