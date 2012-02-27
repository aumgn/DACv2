package fr.aumgn.dac.api.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.util.Vector;

import com.sk89q.worldedit.Vector2D;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Pool;

/**
 * Utility class responsible for placing and adding line to sign
 */
public class RIPSign {

    private static final int SIGN_HORIZONTAL_FACES = 16;
    private static final double SIGN_FACES_ANGLE = Math.PI / SIGN_HORIZONTAL_FACES;
    private static final double MOD_0_TO_1 = Math.cos(7 * SIGN_FACES_ANGLE);
    private static final double MOD_1_TO_2 = Math.cos(3 * SIGN_FACES_ANGLE);
    private static final double MOD_2_TO_1 = Math.cos(SIGN_FACES_ANGLE);

    private Pool pool;
    private Vector vector;

    public RIPSign(Pool pool, Vector vec) {
        this.pool = pool;
        this.vector = vec;
    }

    /**
     * Add player name to this the sign this instance represents.
     * <p/>
     * If the sign do not exists it will be automatically created.  
     * 
     * @param name
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

        for (int i = 1; i < DACUtil.SIGN_LINES; i++) {
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
        } else if (i < -MOD_2_TO_1) {
            return -1;
        } else if (i < -MOD_1_TO_2) {
            return -2;
        } else if (i < -MOD_0_TO_1) {
            return -1;
        } else if (i > MOD_2_TO_1) {
            return 1;
        } else if (i > MOD_1_TO_2) {
            return 2;
        } else if (i > MOD_0_TO_1) {
            return 1;
        } else {
            return 0;
        }
    }

    private BlockFace getHorizontalFaceFor(Vector2D vec) {
        try {
            Vector2D dir = vec.normalize();
            int modX = getBlockFaceModValue(dir.getX());
            int modZ = getBlockFaceModValue(dir.getZ());
            for (BlockFace face : BlockFace.values()) {
                if (face.getModY() == 0 && face.getModX() == modX && face.getModZ() == modZ) {
                    return face;
                }
            }
            return BlockFace.SELF;
        } catch (IllegalArgumentException exc) {
            return BlockFace.SELF;
        }
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
