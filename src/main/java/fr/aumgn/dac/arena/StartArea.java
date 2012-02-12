package fr.aumgn.dac.arena;

import org.bukkit.configuration.serialization.SerializableAs;

import fr.aumgn.dac.area.Area;

@SerializableAs("dac-start")
public class StartArea extends Area {

	public StartArea(DACArena arena) {
		super(arena);
	}

}
