package fr.aumgn.dac.arenas;

import org.bukkit.configuration.serialization.SerializableAs;

import fr.aumgn.dac.arenas.areas.DACArea;

@SerializableAs("dac-start")
public class StartArea extends DACArea {

	public StartArea(DACArena arena) {
		super(arena);
	}

}
