package me.hamza.blaze;

import lombok.Getter;
import me.hamza.blaze.arenas.ArenaHandler;
import me.hamza.blaze.arenas.generator.ArenaGenerator;
import me.hamza.blaze.data.PlayerHandler;
import me.hamza.blaze.kits.KitHandler;
import me.hamza.blaze.mongo.MongoHandler;
import me.hamza.blaze.utils.CC;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Hammzar
 * @since 14.04.2025
 */
@Getter
public class Blaze extends JavaPlugin {

    @Getter
    public static Blaze INSTANCE;
    private MongoHandler mongoHandler;
    private PlayerHandler playerHandler;
    private KitHandler kitHandler;
    private ArenaGenerator arenaGenerator;
    private ArenaHandler arenaHandler;

    @Override
    public void onEnable() {
        INSTANCE = this;

        mongoHandler = new MongoHandler(getConfig());
        playerHandler = new PlayerHandler();
        kitHandler = new KitHandler();
        kitHandler.loadKitsFromConfig();
        arenaGenerator = new ArenaGenerator();
        arenaHandler = new ArenaHandler();
        arenaHandler.loadBaseArenasFromConfig();

        CC.log("");
        CC.log("&f&m----------------------------");
        CC.log("&4&lBLAZE PRACTICE &7made by &4&l" + getDescription().getAuthors().get(0).toUpperCase());
        CC.log(" &7- &4Version: &f" + getDescription().getVersion());
        CC.log(" &7- &4Discord: &fdiscord.gg/hammzar");
        CC.log("&f&m----------------------------");
        CC.log("");
    }

    @Override
    public void onDisable() {
        mongoHandler.getMongoClient().close();
    }
}