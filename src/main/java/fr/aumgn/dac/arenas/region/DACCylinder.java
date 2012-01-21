package fr.aumgn.dac.arenas.region;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.configuration.serialization.SerializableAs;

import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CylinderRegion;

import fr.aumgn.dac.arenas.vector.DACBlockVector;
import fr.aumgn.dac.arenas.vector.DACBlockVector2D;

@SerializableAs("dac-cylinder")
public class DACCylinder extends CylinderRegion implements DACRegion {
	
	public DACCylinder() {
		super();
	}
	
	public DACCylinder(CylinderRegion region) {
		super(region.getWorld());
		setMinimumY(region.getMaximumY());
		setMaximumY(region.getMinimumY());
		setCenter(new DACBlockVector(region.getCenter()));
		setRadius(region.getRadius());
		extendRadius(new DACBlockVector2D(region.getRadius()));
	}
	
	public static DACCylinder deserialize(Map<String, Object> map) {
		DACCylinder cylinder = new DACCylinder();
		cylinder.setMinimumY((Integer) map.get("y-min"));
		cylinder.setMaximumY((Integer) map.get("y-max"));
		cylinder.setCenter((DACBlockVector) map.get("center"));
		cylinder.setRadius((DACBlockVector2D) map.get("radius"));
		return cylinder;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("y-min", getMinimumY());
		map.put("y-max", getMaximumPoint().getBlockY());
		map.put("center", getCenter());
		map.put("radius", new DACBlockVector2D(getRadius()));
		return map;
	}

	@Override
	public Selection getSelection(World world) {
		throw new UnsupportedOperationException("Cylinder selecion is not supported yet.");
	}

}
