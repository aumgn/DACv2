package fr.aumgn.dac.plugin.area.region;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.configuration.serialization.SerializableAs;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.selections.RegionSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CylinderRegion;
import com.sk89q.worldedit.regions.CylinderRegionSelector;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.plugin.area.vector.DACBlockVector;
import fr.aumgn.dac.plugin.area.vector.DACBlockVector2D;

@SerializableAs("dac-cylinder")
public class DACCylinder extends DACSimpleRegion {

	public static class DACCylinderSelection extends RegionSelection {

		public DACCylinderSelection(World world, BlockVector center, BlockVector2D radius, int minY, int maxY) {
			super(world);

			CylinderRegionSelector sel = new CylinderRegionSelector(new BukkitWorld(world));
			sel.selectPrimary(center);
			sel.selectSecondary(center.setY(minY).add(radius.getBlockX(), 0, 0));
			sel.selectSecondary(center.setY(maxY).add(0, 0, radius.getBlockZ()));

			CylinderRegion cyl = sel.getIncompleteRegion();
			setRegionSelector(sel);
			setRegion(cyl);
		}

	}

	private int minY;
	private int maxY;
	private DACBlockVector center;
	private DACBlockVector2D radius;

	public DACCylinder(int minY, int maxY, DACBlockVector center, DACBlockVector2D radius) {
		this.minY = minY;
		this.maxY = maxY;
		this.center = center;
		this.radius = radius;
	}

	public DACCylinder() {
		this(0, 0, new DACBlockVector(), new DACBlockVector2D());
	}

	public DACCylinder(CylinderRegion region) {
		this(
				region.getMinimumY(), region.getMaximumY(), 
				new DACBlockVector(region.getCenter()),
				new DACBlockVector2D(region.getRadius())
				);
	}

	public static DACCylinder deserialize(Map<String, Object> map) {
		DACCylinder cylinder = new DACCylinder();
		cylinder.minY = (Integer) map.get("y-min");
		cylinder.maxY = (Integer) map.get("y-max");
		cylinder.center = (DACBlockVector) map.get("center");
		cylinder.radius = (DACBlockVector2D) map.get("radius");
		return cylinder;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("y-min", minY);
		map.put("y-max", maxY);
		map.put("center", center);
		map.put("radius", radius);
		return map;
	}

	@Override
	public Region createWERegion(LocalWorld world) {
		CylinderRegion cyl = new CylinderRegion(world, center.getVector(), radius.getVector(), minY, maxY);
		// Workaround
		cyl.setCenter(cyl.getCenter());
		return cyl;
	}

	@Override
	public Selection getSelection(World world) {
		return new DACCylinderSelection(world, center.getVector(), radius.getVector(), minY, maxY);
	}

}
