package fr.aumgn.dac.plugin.arena;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.api.arena.Pool;
import fr.aumgn.dac.api.fillstrategy.FillStrategy;
import fr.aumgn.dac.api.fillstrategy.defaults.FillFully;
import fr.aumgn.dac.plugin.area.DACVerticalArea;
import fr.aumgn.dac.plugin.area.region.DACRegion;

@SerializableAs("dac-pool")
public class DACPool extends DACVerticalArea implements Pool {

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
        Vector vec = new BlockVector(
                player.getLocation().getBlockX(),
                player.getLocation().getBlockY(),
                player.getLocation().getBlockZ());
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
    public boolean isADACPattern(int x, int z) {
        boolean water;
        water =  getColumn(x - 1, z).isWater();
        water |= getColumn(x, z - 1).isWater();
        water |= getColumn(x + 1, z).isWater();
        water |= getColumn(x, z + 1).isWater();
        return !water;
    }

}