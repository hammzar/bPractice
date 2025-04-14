package me.hamza.blaze.kits;

import lombok.Getter;
import lombok.Setter;
import me.hamza.blaze.kits.meta.KitDetails;
import me.hamza.blaze.kits.meta.KitIcon;
import me.hamza.blaze.kits.meta.KitItems;
import me.hamza.blaze.kits.meta.KitMechanics;
import org.bukkit.Material;

/**
 * @author Hammzar
 * @since 14.04.2025
 */
@Getter
@Setter
public class Kit {

    private String name;
    private KitDetails details;
    private KitIcon icon;
    private KitItems items;
    private KitMechanics mechanics;
    private KitType type;

    public Kit(String cName, Material cMaterial, int cData, KitType cType) {
        this.name = cName;
        this.details = new KitDetails(cName);
        this.icon = new KitIcon(cMaterial, cData);
        this.items = new KitItems();
        this.mechanics = new KitMechanics();
        this.type = cType;
    }

}
