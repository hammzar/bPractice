package me.hamza.blaze.arenas;

import lombok.Getter;
import me.hamza.blaze.Blaze;
import me.hamza.blaze.arenas.meta.ArenaDetails;
import me.hamza.blaze.arenas.meta.ArenaIcon;
import me.hamza.blaze.arenas.meta.ArenaPositions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Hammzar
 * @since 15.04.2025
 */
@Getter
public class ArenaHandler {

    private final List<Arena> baseArenas = new ArrayList<>();
    private final Map<String, Arena> activeArenas = new HashMap<>();
    private final AtomicInteger cloneCounter = new AtomicInteger(0);

    public void registerBaseArena(Arena arena) {
        baseArenas.add(arena);
    }

    public Collection<Arena> getActiveArenas() {
        return activeArenas.values();
    }

    public Arena cloneArena(String baseArenaName) {
        for (Arena base : baseArenas) {
            if (base.getName().equalsIgnoreCase(baseArenaName)) {
                int id = cloneCounter.incrementAndGet();
                Arena cloned = Blaze.getINSTANCE().getArenaGenerator().cloneArena(base, id);
                activeArenas.put(cloned.getName(), cloned);
                return cloned;
            }
        }
        return null;
    }

    public void removeArena(String clonedArenaName) {
        Arena arena = activeArenas.remove(clonedArenaName);
        if (arena != null) {
            Blaze.getINSTANCE().getArenaGenerator().removeArenaRegion(arena);
        }
    }

    public void loadBaseArenasFromConfig() {
        FileConfiguration config = Blaze.getINSTANCE().getConfig();

        if (!config.contains("arenas")) {
            Bukkit.getLogger().warning("[Blaze] No arenas found in config.");
            return;
        }

        List<Map<?, ?>> arenaList = config.getMapList("arenas");

        for (Map<?, ?> arenaMap : arenaList) {
            try {
                String name = (String) arenaMap.get("name");

                Map<String, Object> iconMap = (Map<String, Object>) arenaMap.get("icon");
                Material material = Material.getMaterial((String) iconMap.get("material"));
                int data = (int) iconMap.get("data");
                ArenaIcon icon = new ArenaIcon(material, data);

                Map<String, Object> posMap = (Map<String, Object>) arenaMap.get("positions");
                Location corner1 = parseLocation((String) posMap.get("corner1"));
                Location corner2 = parseLocation((String) posMap.get("corner2"));
                Location spawnRed = parseLocation((String) posMap.get("spawnRed"));
                Location spawnBlue = parseLocation((String) posMap.get("spawnBlue"));

                Map<String, Object> detailsMap = (Map<String, Object>) arenaMap.get("details");
                String displayName = (String) detailsMap.get("displayName");
                String colorName = (String) detailsMap.getOrDefault("color", "WHITE");
                ChatColor chatColor = ChatColor.valueOf(colorName.toUpperCase());

                ArenaDetails details = new ArenaDetails(displayName);
                details.setColor(chatColor);

                Arena arena = new Arena();
                arena.setName(name);
                arena.setIcon(icon);
                arena.setDetails(details);
                arena.setPositions(new ArenaPositions(spawnBlue, spawnRed, corner1, corner2));

                registerBaseArena(arena);
                Bukkit.getLogger().info("[Blaze] Loaded base arena: " + name);
            } catch (Exception e) {
                Bukkit.getLogger().warning("[Blaze] Failed to load an arena from config: " + e.getMessage());
            }
        }
    }

    private Location parseLocation(String locString) {
        String[] parts = locString.split(",");
        World world = Bukkit.getWorld(parts[0]);
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        float yaw = parts.length > 4 ? Float.parseFloat(parts[4]) : 0f;
        float pitch = parts.length > 5 ? Float.parseFloat(parts[5]) : 0f;

        return new Location(world, x, y, z, yaw, pitch);
    }

}
