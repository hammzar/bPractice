package me.hamza.blaze.match.impl;

import me.hamza.blaze.arenas.Arena;
import me.hamza.blaze.kits.Kit;
import me.hamza.blaze.match.Match;
import me.hamza.blaze.participant.Participant;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Hammzar
 * @since 16.04.2025
 */
public class SoloMatch extends Match {

    private final Participant participantA;
    private final Participant participantB;

    public SoloMatch(Participant participantA, Participant participantB, Kit kit, Arena arena, boolean ranked) {
        super(kit, arena, ranked, false);
        this.participantA = participantA;
        this.participantB = participantB;
    }

    @Override
    public List<Participant> getAllParticipants() {
        return Arrays.asList(participantA, participantB);
    }

    @Override
    public void onStart() {
        participantA.reset();
        participantB.reset();

        participantA.getAllPlayers().forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) setupPlayer(player);
        });

        participantB.getAllPlayers().forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) setupPlayer(player);
        });

        broadcastMessage("&aThe match has started!");
    }

    @Override
    public void onEnd() {
        broadcastMessage("&7Match ended.");
    }

    @Override
    public void onDeath(Player player) {
        Participant participant = getParticipant(player.getUniqueId());
        if (participant != null) {
            participant.markDead(player.getUniqueId());
            if (participant.isAllDead()) {
                endMatch(getOpponent(participant));
            }
        }
    }

    @Override
    public void onDisconnect(Player player) {
        Participant participant = getParticipant(player.getUniqueId());
        if (participant != null) {
            participant.markDisconnected(player.getUniqueId());
            if (participant.isAllDead()) {
                endMatch(getOpponent(participant));
            }
        }
    }

    @Override
    public boolean isInMatch(UUID uuid) {
        return participantA.contains(uuid) || participantB.contains(uuid);
    }

    @Override
    public Participant getParticipant(UUID uuid) {
        if (participantA.contains(uuid)) return participantA;
        if (participantB.contains(uuid)) return participantB;
        return null;
    }

    private Participant getOpponent(Participant participant) {
        return participant == participantA ? participantB : participantA;
    }
}
