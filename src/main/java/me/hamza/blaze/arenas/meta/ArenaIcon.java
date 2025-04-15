package me.hamza.blaze.arenas.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

/**
 * @author Hammzar
 * @since 15.04.2025
 */
@Getter
@Setter
public class ArenaIcon {

    private Material material;
    private int data;

    public ArenaIcon(Material cMaterial, int cData) {
        this.material = cMaterial;
        this.data = cData;
    }

}
