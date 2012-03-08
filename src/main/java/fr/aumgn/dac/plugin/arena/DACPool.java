package fr.aumgn.dac.plugin.arena;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Pool;
import fr.aumgn.dac.api.fillstrategy.FillStrategy;
import fr.aumgn.dac.plugin.area.DACVerticalArea;
import fr.aumgn.dac.plugin.area.region.DACRegion;
import fr.aumgn.dac.plugin.fillstrategy.FillFully;

public class DACPool extends DACVerticalArea implements Pool {

    private static final Material SIGN_MATERIAL = Material.SIGN_POST;
    private static final Material AIR = Material.AIR;
    private static final FillFully WATER_FILLER = new FillFully() {
        @Override
        protected BaseBlock getBlock(String[] args) {
            return new BaseBlock(Material.STATIONARY_WATER.getId());
        }
    };

    private CuboidRegion safeRegion;

    public DACPool(DACArena arena) {
        super(arena);
        updateSafeRegion();
    }

    private CuboidRegion getSafeRegion() {
        return safeRegion;
    }

    private void removeSigns() {
        World world = getArena().getWorld();
        for (Vector vec : getSafeRegion()) {
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

    @Override
    public void updateSafeRegion() {
        int minY, maxY;
        Vector poolMinPt, poolMaxPt, minPt, maxPt;
        int height = DAC.getConfig().getSafeRegionHeight();
        int margin = DAC.getConfig().getSafeRegionMargin();
        Region region = getWERegion();
        poolMinPt = region.getMinimumPoint();
        poolMaxPt = region.getMaximumPoint();

        minY = poolMaxPt.getBlockY() + 1;
        maxY = minY + height;

        minPt = poolMinPt.subtract(margin, 0, margin).setY(minY);
        maxPt = poolMaxPt.add(margin, 0, margin).setY(maxY);

        safeRegion = new CuboidRegion(region.getWorld(), minPt, maxPt);
    }

    @Override
    public boolean isSafe(Player player) {
        Vector vec = new Vector(
                player.getLocation().getBlockX(),
                player.getLocation().getBlockY(),
                player.getLocation().getBlockZ());
        return getSafeRegion().contains(vec);
    }

    @Override
    public void update(Region region) {
        super.update(region);
        updateSafeRegion();
    }

    @Override
    public void setRegion(DACRegion region) {
        super.setRegion(region);
        updateSafeRegion();
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