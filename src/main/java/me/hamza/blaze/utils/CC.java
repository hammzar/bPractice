package me.hamza.blaze.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * @author Hammzar
 * @since 14.04.2025
 */
public class CC {

    public static String translate(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static void log(String input) {
        Bukkit.getConsoleSender().sendMessage(translate(input));
    }
}
