package fr.aumgn.dac.arenas.region;

import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Region;

public interface DACRegion extends Region, ConfigurationSerializable {
	
	public Selection getSelection(World world);
	
}
