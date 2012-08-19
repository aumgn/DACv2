package fr.aumgn.dac2.stage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import fr.aumgn.dac2.arena.Arena;

public interface Stage {

    Arena getArena();

    void start();

    void stop();

    Listener[] getListeners();

    boolean contains(Player player);
}
