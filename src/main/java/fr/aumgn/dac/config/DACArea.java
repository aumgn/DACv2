package fr.aumgn.dac.config;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;

public class DACArea extends CuboidRegion {

	protected DACArena arena;
	
	public DACArea(DACArena arena) {
		super(new Vector(), new Vector());
		this.arena = arena;
	}
	
	public DACArea(DACArena arena, ConfigurationSection section) {
		this(arena);
		if (section != null) {
			org.bukkit.util.Vector vec = section.getVector("pos1");
			if (vec != null) {
				setPos1(new Vector(vec.getX(), vec.getY(), vec.getZ()));
			}
			vec = section.getVector("pos2");
			if (vec != null) {
				setPos2(new Vector(vec.getX(), vec.getY(), vec.getZ()));
			}
		}
	}
	
	public boolean contains(Player player) {
		return contains(player.getLocation());
	}
	
	public boolean contains(Location location) {
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		return contains(new Vector(x, y, z));
	}
	
	public boolean contains(org.bukkit.util.Vector v) {
		return contains(new Vector(v.getX(), v.getY(), v.getZ()));
	}
	
	public void update(CuboidRegion region) {
		setPos1(region.getPos1());
		setPos2(region.getPos2());
		arena.updated();
	}
	
	public void dump(ConfigurationSection section) {
		org.bukkit.util.Vector vec;
		Vector weVec;
		
		vec = new org.bukkit.util.Vector();
		weVec = getPos1();
		vec.setX(weVec.getX());
		vec.setY(weVec.getY());
		vec.setZ(weVec.getZ());
		section.set("pos1", vec);
		
		vec = new org.bukkit.util.Vector();
		weVec = getPos2();
		vec.setX(weVec.getX());
		vec.setY(weVec.getY());
		vec.setZ(weVec.getZ());
		section.set("pos2", vec);
	}

}
