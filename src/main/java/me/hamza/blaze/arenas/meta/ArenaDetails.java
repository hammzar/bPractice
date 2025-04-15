package me.hamza.blaze.arenas.meta;

import lombok.Getter;
import lombok.Setter;
import me.hamza.blaze.utils.CC;
import org.bukkit.ChatColor;

/**
 * @author Hammzar
 * @since 15.04.2025
 */
@Getter
@Setter
public class ArenaDetails {

    private String displayName;
    private ChatColor color = ChatColor.WHITE;

    public ArenaDetails(String kitName) {
        displayName = CC.translate(color + kitName);
    }

}
