package me.hamza.blaze.kits.meta;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

/**
 * @author Hammzar
 * @since 14.04.2025
 */
@Getter
@Setter
public class KitItems {

    private ItemStack[] inventoryItems;
    private ItemStack[] armorItems;

    public KitItems() {
        inventoryItems = new ItemStack[36];
        armorItems = new ItemStack[4];
    }

}
