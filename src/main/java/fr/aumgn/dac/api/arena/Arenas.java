package fr.aumgn.dac.api.arena;

import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.aumgn.dac.plugin.arena.DACArena;

public interface Arenas extends Iterable<DACArena> {

	void load();
	
	void dump();

	Arena get(String name);

	Arena get(Player player);

	void createArena(String name, World world);

	void removeArena(Arena arena);

}
