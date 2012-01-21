package fr.aumgn.dac.arenas.region;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.configuration.serialization.SerializableAs;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CuboidRegion;

import fr.aumgn.dac.arenas.vector.DACBlockVector;

@SerializableAs("dac-cuboid")
public class DACCuboid extends CuboidRegion implements DACRegion {
	
	private DACCuboid(Vector pos1, Vector pos2) {
		super(pos1, pos2);
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
		Vector pos1 = (DACBlockVector) map.get("pos-1");
		Vector pos2 = (DACBlockVector) map.get("pos-2");
		return new DACCuboid(pos1, pos2);
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("pos-1", new DACBlockVector(getPos1()));
		map.put("pos-2", new DACBlockVector(getPos2()));
		return map;
	}
	
	@Override
	public Selection getSelection(World world) {
		return new CuboidSelection(world, getPos1(), getPos2());
	}

}
