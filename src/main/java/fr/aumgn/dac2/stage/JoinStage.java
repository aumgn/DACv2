package fr.aumgn.dac2.stage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.aumgn.dac2.arena.Arena;

public class JoinStage implements Stage, Listener {

    private final Arena arena;

    public JoinStage(Arena arena) {
        this.arena = arena;
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public Listener[] getListeners() {
        return new Listener[] { this };
    }

    @Override
    public boolean contains(Player player) {
        return false;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
    }
}
