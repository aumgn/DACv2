package fr.aumgn.dac.areas.region;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.configuration.serialization.SerializableAs;

import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.areas.vector.DACBlockVector;

@SerializableAs("dac-cuboid")
public class DACCuboid extends DACSimpleRegion {

	private DACBlockVector pos1;
	private DACBlockVector pos2;


	private DACCuboid(DACBlockVector pos1, DACBlockVector pos2) {
		this.pos1 = pos1;
		this.pos2 = pos2;
	}

	public DACCuboid() {
		this(new DACBlockVector(), new DACBlockVector());
	}

	public DACCuboid(CuboidRegion region) {
		this(
				new DACBlockVector(region.getPos1()), 
				new DACBlockVector(region.getPos2())
				);
	}

	public static DACCuboid deserialize(Map<String, Object> map) {
		DACCuboid cuboid = new DACCuboid();
		cuboid.pos1 = (DACBlockVector) map.get("pos-1");
		cuboid.pos2 = (DACBlockVector) map.get("pos-2");
		return cuboid;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("pos-1", pos1);
		map.put("pos-2", pos2);
		return map;
	}

	@Override
	public Region createWERegion(LocalWorld world) {
		return new CuboidRegion(world, pos1.getVector(), pos2.getVector());
	}

	@Override
	public Selection getSelection(World world) {
		return new CuboidSelection(world, pos1.getVector(), pos2.getVector());
	}

}
