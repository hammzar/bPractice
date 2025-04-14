package me.hamza.blaze;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Blaze extends JavaPlugin {

    public static Blaze INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

    }

    @Override
    public void onDisable() {
        
    }
}