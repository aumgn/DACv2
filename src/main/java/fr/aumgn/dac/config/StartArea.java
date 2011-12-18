package fr.aumgn.dac.config;

import org.bukkit.configuration.ConfigurationSection;

public class StartArea extends DACArea {

	public StartArea(DACArena arena) {
		super(arena);
	}
	
	public StartArea(DACArena arena, ConfigurationSection section) {
		super(arena, section);
	}

}
