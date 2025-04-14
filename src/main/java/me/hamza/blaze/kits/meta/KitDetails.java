package me.hamza.blaze.kits.meta;

import lombok.Getter;
import lombok.Setter;
import me.hamza.blaze.utils.CC;
import org.bukkit.ChatColor;

/**
 * @author Hammzar
 * @since 14.04.2025
 */
@Getter
@Setter
public class KitDetails {

    private String description;
    private String displayName;
    private ChatColor color = ChatColor.WHITE;

    public KitDetails(String kitName) {
        description = kitName + " | Sword, Armor and Gapples.";
        displayName = CC.translate(color + kitName);
    }

}
