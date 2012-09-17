package fr.aumgn.dac.api.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Pool;

/**
 * Utility class responsible for placing and adding line to sign
 */
public class RIPSign {

    private static final double ANGLE_7_PI_16;
    private static final double ANGLE_3_PI_16;
    private static final double ANGLE_PI_16;

    static {
        double sign_faces_angle_unit = Math.PI / 16;
        ANGLE_7_PI_16 = Math.cos(7 * sign_faces_angle_unit);
        ANGLE_3_PI_16 = Math.cos(3 * sign_faces_angle_unit);
        ANGLE_PI_16 = Math.cos(sign_faces_angle_unit);
    }

    private Pool pool;
    private Vector vector;
    /** Maximum number of character per sign line. */
    public static final int SIGN_MAX_CHAR = 16;
    /** Number of lines per sign. */
    public static final int SIGN_LINES = 4;

    public RIPSign(Pool pool, Vector vector) {
        this.pool = pool;
        this.vector = vector;
    }

    /**
     * Add player name to the sign this instance represents.
     * If the sign do not exists it will be automatically created.
     */
    public void rip(String name) {
        World world = pool.getArena().getWorld();
        if (vector == null) {
            return;
        }

        Block block = world.getBlockAt(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
        if (block.getType() != Material.SIGN_POST) {
            createRIPSign(block);
        }

        for (int i = 1; i < SIGN_LINES; i++) {
            Sign sign = (Sign) block.getState();
            if (sign.getLine(i).isEmpty()) {
                sign.setLine(i, name);
                sign.update();
                return;
            }
        }
    }

    private int getBlockFaceModValue(double i) {
        if (i < -1 || i > 1) {
            throw new IllegalArgumentException("Value must be between -1 and 1");
        } else if (i < -ANGLE_PI_16) {
            return -1;
        } else if (i < -ANGLE_3_PI_16) {
            return -2;
        } else if (i < -ANGLE_7_PI_16) {
            return -1;
        } else if (i > ANGLE_PI_16) {
            return 1;
        } else if (i > ANGLE_3_PI_16) {
            return 2;
        } else if (i > ANGLE_7_PI_16) {
            return 1;
        } else {
            return 0;
        }
    }

    private BlockFace getHorizontalFaceFor(Vector2D vec) {
        Vector2D dir = vec.normalize();
        int modX = getBlockFaceModValue(dir.getX());
        int modZ = getBlockFaceModValue(dir.getZ());
        for (BlockFace face : BlockFace.values()) {
            if (face.getModY() == 0 && face.getModX() == modX && face.getModZ() == modZ) {
                return face;
            }
        }
        return BlockFace.SELF;
    }

    private void createRIPSign(Block block) {
        Location diving = pool.getArena().getDivingBoard().getLocation();

        Vector2D dir = new Vector2D(diving.getX() - vector.getX(), diving.getZ() - vector.getZ());
        BlockFace face = getHorizontalFaceFor(dir);

        block.setType(Material.SIGN_POST);

        org.bukkit.material.Sign signBlock = new org.bukkit.material.Sign(Material.SIGN_POST, block.getData());
        signBlock.setFacingDirection(face);
        block.setData(signBlock.getData());

        Sign sign = (Sign) block.getState();
        sign.setLine(0, DAC.getConfig().getDeathSignFirstLine());
        sign.update();
    }

}
