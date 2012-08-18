package fr.aumgn.dac2.utils;

import org.bukkit.World;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.bukkit.BukkitUtil;
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

    public static BlockVector bu2blockwe(Vector pt) {
        return new BlockVector(pt.getX(), pt.getY(), pt.getZ());
    }

    public static com.sk89q.worldedit.Vector2D bu2we(Vector2D vector) {
        return new com.sk89q.worldedit.Vector2D(vector.getX(), vector.getZ());
    }

    public static BlockVector2D bu2blockwe(Vector2D pt) {
        return new BlockVector2D(pt.getX(), pt.getZ());
    }

    public static World we2bk(LocalWorld world) {
        return ((BukkitWorld) world).getWorld();
    }

    public static LocalWorld bk2we(World world) {
        return BukkitUtil.getLocalWorld(world);
    }

    private WEUtils() {
    }
}
