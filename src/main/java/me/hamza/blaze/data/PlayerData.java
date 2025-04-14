package me.hamza.blaze.data;

import lombok.Getter;
import lombok.Setter;
import me.hamza.blaze.Blaze;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * @author Hammzar
 * @since 14.04.2025
 */
@Getter
@Setter
public class PlayerData {

    private final UUID uuid;
    private String username;
    private int wins;
    private int losses;
    private PlayerState state = PlayerState.SPAWN;

    private boolean buildMode = false;

    public PlayerData(UUID cUuid) {
        this.uuid = cUuid;
        this.username = Bukkit.getOfflinePlayer(cUuid).getName();
        this.wins = 0;
        this.losses = 0;
    }

    public void addWins(int amount) {
        this.wins += amount;
    }

    public void addLosses(int amount) {
        this.losses += amount;
    }

    public void store() {
        Blaze.getINSTANCE().getPlayerHandler().storeData(this);
    }

    public void load() {
        Blaze.getINSTANCE().getPlayerHandler().loadData(this);
    }

}
