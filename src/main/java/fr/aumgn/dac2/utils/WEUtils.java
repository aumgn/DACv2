package fr.aumgn.dac2.utils;

import org.bukkit.World;

import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.bukkit.BukkitWorld;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;

public class WEUtils {

    public static Vector we2bu(com.sk89q.worldedit.Vector vector) {
        return new Vector(vector.getX(), vector.getY(), vector.getZ());
    }

    public static com.sk89q.worldedit.Vector bu2we(Vector vector) {
        return new com.sk89q.worldedit.Vector(
                vector.getX(), vector.getY(), vector.getZ());
    }

    public static Vector2D we2bu(com.sk89q.worldedit.Vector2D vector) {
        return new Vector2D(vector.getX(), vector.getZ());
    }

    public static com.sk89q.worldedit.Vector2D bu2we(Vector2D vector) {
        return new com.sk89q.worldedit.Vector2D(vector.getX(), vector.getZ());
    }

    public static World weWorld2World(LocalWorld world) {
        return ((BukkitWorld) world).getWorld();
    }

    private WEUtils() {
    }
}
