package me.hamza.blaze.kits.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

/**
 * @author Hammzar
 * @since 14.04.2025
 */
@Getter
@Setter
public class KitIcon {

    private Material material;
    private int data;

    public KitIcon(Material cMaterial, int cData) {
        this.material = cMaterial;
        this.data = cData;
    }

}
