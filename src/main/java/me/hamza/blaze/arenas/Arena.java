package me.hamza.blaze.arenas;

import lombok.Getter;
import lombok.Setter;
import me.hamza.blaze.arenas.meta.ArenaDetails;
import me.hamza.blaze.arenas.meta.ArenaIcon;
import me.hamza.blaze.arenas.meta.ArenaPositions;

/**
 * @author Hammzar
 * @since 15.04.2025
 */
@Getter
@Setter
public class Arena {

    private String name;
    private ArenaDetails details;
    private ArenaIcon icon;
    private ArenaPositions positions;

}