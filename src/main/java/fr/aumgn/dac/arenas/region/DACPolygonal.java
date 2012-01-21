package fr.aumgn.dac.arenas.region;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.configuration.serialization.SerializableAs;

import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.bukkit.selections.Polygonal2DSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Polygonal2DRegion;

import fr.aumgn.dac.arenas.vector.DACBlockVector2D;

@SerializableAs("dac-poly")
public class DACPolygonal extends Polygonal2DRegion implements DACRegion {
	
	public DACPolygonal() {
		super();
	}
	
	public DACPolygonal(LocalWorld world) {
		super();
	}
	
	public DACPolygonal(Polygonal2DRegion region) {
		super(region.getWorld());
		for (BlockVector2D pt : region.getPoints()) {
			addPoint(new DACBlockVector2D(pt));
		}
		setMaximumY(region.getMaximumPoint().getBlockY());
		setMinimumY(region.getMinimumPoint().getBlockY());
	}
	
	public static DACPolygonal deserialize(Map<String, Object> map) {
		int i = 1;
		DACPolygonal poly = new DACPolygonal();
		poly.setMinimumY((Integer) map.get("y-min"));
		poly.setMaximumY((Integer) map.get("y-max"));
		while (map.containsKey("point-" + i)) {
			poly.addPoint((BlockVector2D) map.get("point-" + i));
			i++;
		}
		return poly;
	}

	@Override
	public Map<String, Object> serialize() {
		int i = 1;
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("y-min", getMinimumPoint().getBlockY());
		map.put("y-max",  getMaximumPoint().getBlockY());
		for (BlockVector2D point : getPoints()) {
			map.put("point-" + i, point);
			i++;
		}
		return map;
	}

	@Override
	public Selection getSelection(World world) {
		return new Polygonal2DSelection(
			world,
			getPoints(),
			getMinimumPoint().getBlockY(),
			getMaximumPoint().getBlockY()
		);
	}
	
}
