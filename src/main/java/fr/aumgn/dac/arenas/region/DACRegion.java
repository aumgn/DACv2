package fr.aumgn.dac.arenas.region;

import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Region;

public interface DACRegion<T extends Region> extends ConfigurationSerializable {
	
	T getRegion(LocalWorld world);
	
	Selection getSelection(World world);
	
}
