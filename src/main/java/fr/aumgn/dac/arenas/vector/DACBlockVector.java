package fr.aumgn.dac.arenas.vector;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;

@SerializableAs("dac-blockvector")
public class DACBlockVector extends BlockVector implements ConfigurationSerializable {

	public DACBlockVector() {
		super(0, 0, 0);
	}

	public DACBlockVector(Vector vector) {
		super(vector); 
	}

	public static DACBlockVector deserialize(Map<String, Object> map) {
		int x, y, z;
		x = (Integer) map.get("x");
		y = (Integer) map.get("y");
		z = (Integer) map.get("z");
		return new DACBlockVector(new BlockVector(x, y, z));
	}

	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("x", getBlockX());
		map.put("y", getBlockY());
		map.put("z", getBlockZ());
		return map;
	}

	/*public static class CuboidSerialization implements RegionSerialization {

		public static class VectorSerialization implements ConfigurationSerializable {


		}

		private CuboidRegion cuboid;

		public CuboidSerialization(CuboidRegion cuboid) {
			this.cuboid = cuboid;
		}

		@Override
		public Region getRegion() {
			return cuboid;
		}

		@SuppressWarnings("unchecked")
		public static CuboidSerialization deserialize(DACArena arena, Map<String, Object> map) {
			VectorSerialization min, max;
			min = VectorSerialization.deserialize((Map<String, Object>) map.get("min"));
			max = VectorSerialization.deserialize((Map<String, Object>) map.get("max"));
			CuboidRegion cuboid = new CuboidRegion(arena.getWEWorld(), min.getVector(), max.getVector());
			return new CuboidSerialization(cuboid);
		}

		@Override
		public Map<String, Object> serialize() {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "cuboid");
			map.put("min", new VectorSerialization(cuboid.getMinimumPoint()));
			map.put("max", new VectorSerialization(cuboid.getMaximumPoint()));
			return map;
		}

	}

	public static class Polygonal2DSerialization implements RegionSerialization {

		public static class BlockVector2DSerialization implements ConfigurationSerializable {

			private int x, z;

			public BlockVector2DSerialization(BlockVector2D vector) {
				x = vector.getBlockX();
				z = vector.getBlockZ();
			}

			public BlockVector2D getVector() {
				return new BlockVector2D(x, z);
			}

			public static BlockVector2DSerialization deserialize(Map<String, Object> map) {
				int x, z;
				x = (Integer) map.get("x");
				z = (Integer) map.get("z");
				return new BlockVector2DSerialization(new BlockVector2D(x, z));
			}

			public Map<String, Object> serialize() {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("x", x);
				map.put("z", z);
				return map;
			}

		}

		private Polygonal2DRegion polygonal;
		private List<BlockVector2DSerialization> points;

		public Polygonal2DSerialization(Polygonal2DRegion polygonal) {
			this.polygonal = polygonal;
			points = new ArrayList<BlockVector2DSerialization>();
			for (BlockVector2D point : polygonal.getPoints()) {
				points.add(new BlockVector2DSerialization(point));
			}
		}

		@Override
		public Region getRegion() {
			return polygonal;
		}

		@SuppressWarnings("unchecked")
		public static Polygonal2DSerialization deserialize(DACArena arena, Map<String, Object> map) {
			int i = 1, yMin = 0, yMax = 0;
			List<BlockVector2D> points = new ArrayList<BlockVector2D>();
			yMin = (Integer) map.get("min-y");
			yMax = (Integer) map.get("max-y");
			BlockVector2DSerialization point;
			while (map.containsKey("vector" + i)) {
				point = BlockVector2DSerialization.deserialize((Map<String, Object>) map.get("vector" + i));
				points.add(point.getVector());
				i++;
			}
			Polygonal2DRegion polygonal = new Polygonal2DRegion(arena.getWEWorld(), points, yMin, yMax);
			return new Polygonal2DSerialization(polygonal);
		}

		public Map<String, Object> serialize() {
			int i = 1;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "polygonal");
			map.put("min-y", polygonal.getMinimumPoint().getBlockY());
			map.put("max-y", polygonal.getMaximumPoint().getBlockY());
			for (BlockVector2DSerialization point : points) {
				map.put("vector" + i, point);
				i++;
			}
			return map;
		}

	}

	@Override
	public Map<String, Object> serialize() {
		// TODO Auto-generated method stub
		return null;
	}*/

}
