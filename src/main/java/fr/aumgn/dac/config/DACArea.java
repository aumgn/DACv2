package fr.aumgn.dac.config;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Polygonal2DSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.Region;

public class DACArea implements ConfigurationSerializable {
	
	public interface RegionSerialization extends ConfigurationSerializable {
		public Region getRegion();
	}
	
	public static class CuboidSerialization implements RegionSerialization {
		
		public static class VectorSerialization implements ConfigurationSerializable {
			
			private Vector vector;
			
			public VectorSerialization(Vector vector) {
				this.vector = vector; 
			}
			
			public Vector getVector() {
				return vector; 
			}
			
			public static VectorSerialization deserialize(Map<String, Object> map) {
				int x, y, z;
				x = (Integer) map.get("x");
				y = (Integer) map.get("y");
				z = (Integer) map.get("z");
				return new VectorSerialization(new Vector(x, y, z));
			}
			
			public Map<String, Object> serialize() {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("x", vector.getBlockX());
				map.put("y", vector.getBlockY());
				map.put("z", vector.getBlockZ());
				return map;
			}
			
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
	
	public static class InvalidRegionType extends RuntimeException {
		private static final long serialVersionUID = 1L;
		
		public InvalidRegionType(String message) {
			super(message);
		}
		
		public InvalidRegionType(String expected1, String expected2, String given) {
			this("Expected " + expected1 + " or " + expected2 + " got " + given);
		}
		
		public InvalidRegionType(String expected1, String expected2, Class<? extends Region> given) {
			this(expected1, expected2, given.getSimpleName());
		}
		
	}
	
	protected DACArena arena;
	protected Region region;
	
	// Workaround
	protected Polygonal2DSerialization polygonSerialization;
	
	public DACArea(DACArena arena) {
		this.arena = arena;
		this.region = new CuboidRegion(new BlockVector(0,0,0), new BlockVector(0,0,0));
	}
	
	@SuppressWarnings("unchecked")
	public void load(Object data) {
		if (data instanceof Map) {
			Map<String, Object> map = (Map<String, Object>)data;
			String type = (String)map.get("type");
			RegionSerialization regionSerialization;
			if (type.equals("cuboid")) {
				regionSerialization = CuboidSerialization.deserialize(arena, map); 
			} else if (type.equals("polygonal")) {
				// Workaround
				polygonSerialization = Polygonal2DSerialization.deserialize(arena, map); 
				regionSerialization = polygonSerialization; 
			} else {
				throw new InvalidRegionType("cuboid", "polygonal", type);
			}
			this.region = regionSerialization.getRegion(); 
		}
	}
	
	public void update(Region region) {
		if (region instanceof CuboidRegion) {
			CuboidRegion cuboid = (CuboidRegion)region;
			this.region = new CuboidRegion(cuboid.getPos1(), cuboid.getPos2());
		} else if (region instanceof Polygonal2DRegion) {
			Polygonal2DRegion poly = (Polygonal2DRegion)region;
			// Workaround
			polygonSerialization = new Polygonal2DSerialization(poly);
			this.region = new Polygonal2DRegion(
				arena.getWEWorld(),
				poly.getPoints(),
				poly.getMinimumPoint().getBlockY(),
				poly.getMaximumPoint().getBlockY()
			);
		} else {
			throw new InvalidRegionType("CuboidRegion", "Polygonal2DRegion", region.getClass());			
		}
		arena.updated();
	}
	
	public Selection getSelection() {
		Selection selection;
		if (region instanceof CuboidRegion) {
			CuboidRegion cuboid = (CuboidRegion)region;
			selection = new CuboidSelection(arena.getWorld(), cuboid.getMinimumPoint(), cuboid.getMaximumPoint());
		} else if (region instanceof Polygonal2DRegion) {
			Polygonal2DRegion poly = (Polygonal2DRegion)region;
			try {
				selection = new Polygonal2DSelection(
					arena.getWorld(),
					poly.getPoints(),
					poly.getMinimumPoint().getBlockY(),
					poly.getMaximumPoint().getBlockY()
				);	
			} catch (IndexOutOfBoundsException exc) {
				throw new InvalidRegionType(
					"La reselection des zones polygonales n'est pas supportée "
					+ "avec votre version de WorldEdit a cause d'un bug (resolu dans les builds plus récent)."
				);
			}
		} else {
			throw new InvalidRegionType("CuboidRegion", "Polygonal2DRegion", region.getClass());			
		}
		return selection;
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map;
		if (region instanceof CuboidRegion) {
			map = new CuboidSerialization((CuboidRegion)region).serialize();
		} else if (region instanceof Polygonal2DRegion) {
			/*
			 * Workaround
			 * Should be :
			 * map = new Polygonal2DSerialization((Polygonal2DRegion)region).serialize();
			 */
			map = polygonSerialization.serialize();
		} else {
			throw new InvalidRegionType("CuboidRegion", "Polygonal2DRegion", region.getClass());
		}
		return map;
	}
	
	public boolean contains(Player player) {
		return contains(player.getLocation());
	}
	
	public boolean contains(Location location) {
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		return region.contains(new BlockVector(x, y, z));
	}
	
	public boolean contains(org.bukkit.util.Vector v) {
		return region.contains(new BlockVector(v.getX(), v.getY(), v.getZ()));
	}

}
