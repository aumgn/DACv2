package fr.aumgn.dac.plugin.arena;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.ColumnPattern;
import fr.aumgn.dac.api.arena.Pool;
import fr.aumgn.dac.api.config.DACColor;
import fr.aumgn.dac.api.fillstrategy.FillStrategy;
import fr.aumgn.dac.api.fillstrategy.defaults.FillFully;
import fr.aumgn.dac.api.util.DACUtil;
import fr.aumgn.dac.plugin.area.DACAreaColumn;
import fr.aumgn.dac.plugin.area.DACVerticalArea;
import fr.aumgn.dac.plugin.area.region.DACRegion;

@SerializableAs("dac-pool")
public class DACPool extends DACVerticalArea implements Pool {

    private static final Material DEFAULT_MATERIAL = Material.STATIONARY_WATER;
    private static final Material SIGN_MATERIAL = Material.SIGN_POST;
    private static final Material AIR = Material.AIR;
    private static final FillFully WATER_FILLER = new FillFully() {
        @Override
        protected BaseBlock getBlock(String[] args) {
            return new BaseBlock(Material.STATIONARY_WATER.getId());
        }
    };

    private static final int ABOVE_REGION_HEIGHT = 5;
    private static final int ABOVE_REGION_MARGIN = 5;

    private CuboidRegion aboveRegion;

    public DACPool(DACArena arena) {
        super(arena);
        updateAboveRegion();
    }

    private void removeSigns() {
        World world = getArena().getWorld();
        for (BlockVector vec : getAboveRegion()) {
            Block block = world.getBlockAt(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ());
            if (block.getType() == SIGN_MATERIAL) {
                block.setType(AIR);
            }
        }
    }

    @Override
    public void fillWith(FillStrategy strategy, String[] args) {
        removeSigns();
        super.fillWith(strategy, args);
    }

    @Override
    public void reset() {
        fillWith(WATER_FILLER, new String[0]);
    }

    private void updateAboveRegion() {
        int minY, maxY;
        Vector poolMinPt, poolMaxPt, minPt, maxPt;
        Region region = getWERegion();
        poolMinPt = region.getMinimumPoint();
        poolMaxPt = region.getMaximumPoint();
        
        minY = poolMaxPt.getBlockY() + 1;
        maxY = minY + ABOVE_REGION_HEIGHT;

        minPt = poolMinPt.subtract(ABOVE_REGION_MARGIN, 0, ABOVE_REGION_MARGIN).setY(minY);
        maxPt = poolMaxPt.add(ABOVE_REGION_MARGIN, 0, ABOVE_REGION_MARGIN).setY(maxY);

        aboveRegion = new CuboidRegion(region.getWorld(), minPt, maxPt);
    }

    private CuboidRegion getAboveRegion() {
        return aboveRegion;
    }

    @Override
    public boolean isAbove(Player player) {
        Vector vec = new BlockVector(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
        return getAboveRegion().contains(vec);
    }

    @Override
    public void update(Region region) {
        super.update(region);
        updateAboveRegion();
    }
    
    @Override
    public void setRegion(DACRegion region) {
        super.setRegion(region);
        updateAboveRegion();
    }

    @Override
    public void setColumn(ColumnPattern pattern, DACColor color, int x, int z) {
        pattern.set(new DACAreaColumn(this, x, z), color);
    }

    public void putRIPSign(org.bukkit.util.Vector vec) {
        Location diving = getArena().getDivingBoard().getLocation();

        Vector2D dir = new Vector2D(diving.getX() - vec.getX(), diving.getZ() - vec.getZ());
        BlockFace face = DACUtil.getHorizontalFaceFor(dir);

        Block block = getArena().getWorld().getBlockAt(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ());
        block.setType(Material.SIGN_POST);

        org.bukkit.material.Sign signBlock = new org.bukkit.material.Sign(Material.SIGN_POST, block.getData());
        signBlock.setFacingDirection(face);
        block.setData(signBlock.getData());

        Sign sign = (Sign) block.getState();
        sign.setLine(0, DAC.getConfig().getDeathSignFirstLine());
        sign.update();
    }

    @Override
    public void rip(org.bukkit.util.Vector vec, String name) {
        if (vec == null) {
            return;
        }
        Block block = getArena().getWorld().getBlockAt(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ());
        if (block.getType() != Material.SIGN_POST) {
            putRIPSign(vec);
        }
        for (int i = 1; i < DACUtil.SIGN_LINES; i++) {
            Sign sign = (Sign) block.getState();
            if (sign.getLine(i).isEmpty()) {
                sign.setLine(i, name);
                break;
            }
            sign.update();
        }
    }

    private boolean isColumnAt(int x, int z) {
        Block blk = getArena().getWorld().getBlockAt(x, getWERegion().getMinimumPoint().getBlockY(), z);
        return !blk.getType().equals(DEFAULT_MATERIAL);
    }

    @Override
    public boolean isADACPattern(int x, int z) {
        boolean dac;
        dac = isColumnAt(x - 1, z);
        dac &= isColumnAt(x, z - 1);
        dac &= isColumnAt(x + 1, z);
        dac &= isColumnAt(x, z + 1);
        return dac;
    }

}