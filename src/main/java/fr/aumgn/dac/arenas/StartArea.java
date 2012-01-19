package fr.aumgn.dac.arenas;

import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("dac-start")
public class StartArea extends DACArea {

	public StartArea(DACArena arena) {
		super(arena);
	}

}
