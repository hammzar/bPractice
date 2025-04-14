package me.hamza.blaze.data;

import me.hamza.blaze.Blaze;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * @author Hammzar
 * @since 14.04.2025
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uniqueID = player.getUniqueId();

        PlayerData playerData = Blaze.getINSTANCE().getPlayerHandler().getPlayer(uniqueID);
        playerData.setUsername(player.getName());

        if (player.isDead()) {
            player.spigot().respawn();
        }

        player.setGameMode(GameMode.SURVIVAL);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        UUID uniqueId = player.getUniqueId();
        if (Blaze.getINSTANCE().getPlayerHandler().getPlayer(uniqueId) != null) {
            return;
        }

        PlayerData playerData = new PlayerData(uniqueId);
        playerData.load();
        Blaze.getINSTANCE().getPlayerHandler().getPlayerDataMap().put(uniqueId, playerData);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Blaze.getINSTANCE().getPlayerHandler().getPlayer(e.getPlayer().getUniqueId()).store();
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        Blaze.getINSTANCE().getPlayerHandler().getPlayer(e.getPlayer().getUniqueId()).store();
    }

}