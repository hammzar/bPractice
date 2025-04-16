package me.hamza.blaze.participant;

import lombok.Getter;
import lombok.Setter;
import me.hamza.blaze.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Hammzar
 * @since 16.04.2025
 */
@Getter
@Setter
public class Participant {

    private final UUID leaderUUID;
    private final List<UUID> memberUUIDs = new ArrayList<>();
    private final List<UUID> deadPlayers = new ArrayList<>();
    private final List<UUID> disconnectedPlayers = new ArrayList<>();

    public Participant(UUID leaderUUID) {
        this.leaderUUID = leaderUUID;
    }

    public void addMember(UUID uuid) {
        if (!memberUUIDs.contains(uuid)) {
            memberUUIDs.add(uuid);
        }
    }

    public List<UUID> getAllPlayers() {
        List<UUID> all = new ArrayList<>();
        all.add(leaderUUID);
        all.addAll(memberUUIDs);
        return all;
    }

    public boolean contains(UUID uuid) {
        return getAllPlayers().contains(uuid);
    }

    public boolean isAllDead() {
        return getAliveCount() == 0;
    }

    public int getAliveCount() {
        return (int) getAllPlayers().stream()
                .filter(uuid -> !deadPlayers.contains(uuid) && !disconnectedPlayers.contains(uuid))
                .count();
    }

    public void markDead(UUID uuid) {
        if (!deadPlayers.contains(uuid)) {
            deadPlayers.add(uuid);
        }
    }

    public void markDisconnected(UUID uuid) {
        if (!disconnectedPlayers.contains(uuid)) {
            disconnectedPlayers.add(uuid);
        }
    }

    public void reset() {
        deadPlayers.clear();
        disconnectedPlayers.clear();
    }

    public void sendMessage(String message) {
        for (UUID uuid : getAllPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                player.sendMessage(CC.translate(message));
            }
        }
    }

    public String getNameString() {
        Player leader = Bukkit.getPlayer(leaderUUID);
        if (memberUUIDs.isEmpty()) {
            return leader != null ? leader.getName() : "Unknown";
        }

        List<String> names = new ArrayList<>();
        if (leader != null) names.add(leader.getName());
        for (UUID uuid : memberUUIDs) {
            Player member = Bukkit.getPlayer(uuid);
            if (member != null) names.add(member.getName());
        }
        return String.join(", ", names);
    }
}
