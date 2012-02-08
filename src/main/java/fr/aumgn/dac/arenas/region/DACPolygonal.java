package fr.aumgn.dac.arenas.region;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.configuration.serialization.SerializableAs;

import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.bukkit.selections.Polygonal2DSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.arenas.vector.DACBlockVector2D;

@SerializableAs("dac-poly")
public class DACPolygonal extends DACBasicRegion {
	
	private int minY;
	private int maxY;
	private List<DACBlockVector2D> points;
	
	public DACPolygonal(int minY, int maxY, List<DACBlockVector2D> points) {
		this.minY = minY;
		this.maxY = maxY;
		this.points = points;
	}
	
	public DACPolygonal() {
		this(0, 0, new ArrayList<DACBlockVector2D>());
	}
	
	public DACPolygonal(Polygonal2DRegion region) {
		this.minY = region.getMininumY();
		this.maxY = region.getMaximumY();
		this.points = new ArrayList<DACBlockVector2D>(region.getPoints().size());
		for (BlockVector2D pt : region.getPoints()) {
			points.add(new DACBlockVector2D(pt));
		}
		
	}
	
	public static DACPolygonal deserialize(Map<String, Object> map) {
		int i = 1;
		DACPolygonal poly = new DACPolygonal();
		poly.minY = (Integer) map.get("y-min");
		poly.maxY = (Integer) map.get("y-max");
		while (map.containsKey("point-" + i)) {
			poly.points.add((DACBlockVector2D) map.get("point-" + i));
			i++;
		}
		return poly;
	}

	@Override
	public Map<String, Object> serialize() {
		int i = 1;
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("y-min", minY);
		map.put("y-max",  maxY);
		for (DACBlockVector2D point : points) {
			map.put("point-" + i, point);
			i++;
		}
		return map;
	}
	
	private List<BlockVector2D> getWEPoints() {
		List<BlockVector2D> wePoints = new ArrayList<BlockVector2D>();
		for (DACBlockVector2D point : points) {
			wePoints.add(point.getVector());
		}
		return wePoints;
	}

	@Override
	public Region createWERegion(LocalWorld world) {
		return new Polygonal2DRegion(world, getWEPoints(), minY, maxY);
	}

	@Override
	public Selection getSelection(World world) {
		return new Polygonal2DSelection(world, getWEPoints(), minY, maxY);
	}
	
}
