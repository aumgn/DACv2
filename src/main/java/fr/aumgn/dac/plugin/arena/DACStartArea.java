package fr.aumgn.dac.plugin.arena;

import org.bukkit.configuration.serialization.SerializableAs;

import fr.aumgn.dac.api.arena.StartArea;
import fr.aumgn.dac.plugin.area.DACArea;

@SerializableAs("dac-start")
public class DACStartArea extends DACArea implements StartArea {

	public DACStartArea(DACArena arena) {
		super(arena);
	}

}
