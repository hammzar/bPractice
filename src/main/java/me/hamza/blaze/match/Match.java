package me.hamza.blaze.match;

import lombok.Getter;
import lombok.Setter;
import me.hamza.blaze.Blaze;
import me.hamza.blaze.arenas.Arena;
import me.hamza.blaze.kits.Kit;
import me.hamza.blaze.participant.Participant;
import me.hamza.blaze.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Hammzar
 * @since 16.04.2025
 */
@Getter
@Setter
public abstract class Match {

    private final Kit kit;
    private final Arena arena;
    private final boolean ranked;
    private final boolean party;

    private MatchState state = MatchState.STARTING;
    private final List<UUID> spectators = new ArrayList<>();
    private long startedAt;

    public Match(Kit kit, Arena arena, boolean ranked, boolean party) {
        this.kit = kit;
        this.arena = arena;
        this.ranked = ranked;
        this.party = party;

        Blaze.getINSTANCE().getMatchHandler().addMatch(this);
    }

    public void startMatch() {
        setState(MatchState.STARTING);
        int countdown = 5;

        new BukkitRunnable() {
            int ticks = countdown;

            @Override
            public void run() {
                if (ticks == 0) {
                    this.cancel();
                    setState(MatchState.IN_GAME);
                    startedAt = System.currentTimeMillis();
                    onStart();
                    broadcastMessage("&aMatch Started. Good luck!");
                    return;
                }

                broadcastMessage("&eMatch starts in &c" + ticks + "&e...");
                ticks--;
            }
        }.runTaskTimer(Blaze.getINSTANCE(), 0L, 20L);
    }

    public void endMatch(Participant winner) {
        setState(MatchState.END);
        winner.sendMessage(CC.translate("&aYou won."));

        getAllParticipants().forEach(p -> {
            if (p != winner) {
                p.sendMessage(CC.translate("&cYou lost the duel."));
            }
        });

        new BukkitRunnable() {
            @Override
            public void run() {
                getAllParticipants().forEach(participant -> {
                    participant.getAllPlayers().forEach(uuid -> {
                        Player player = Bukkit.getPlayer(uuid);
                        if (player != null) {
                            // tp spawn, give hotbar
                        }
                    });
                });

                onEnd();
            }
        }.runTaskLater(Blaze.getINSTANCE(), 60L);
    }

    protected void setupPlayer(Player player) {
        player.getInventory().setContents(kit.getItems().getInventoryItems());
        player.getInventory().setArmorContents(kit.getItems().getArmorItems());
        player.updateInventory();
    }

    protected void broadcastMessage(String message) {
        getAllParticipants().forEach(p -> p.sendMessage(CC.translate(message)));
    }

    public abstract List<Participant> getAllParticipants();

    public abstract void onStart();

    public abstract void onEnd();

    public abstract void onDeath(Player player);

    public abstract void onDisconnect(Player player);

    public abstract boolean isInMatch(UUID uuid);

    public abstract Participant getParticipant(UUID uuid);
}
