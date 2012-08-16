package fr.aumgn.dac2.regions.worldedit;

import static fr.aumgn.dac2.utils.WEUtils.bu2we;

import javax.persistence.Transient;

import org.bukkit.World;

import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.dac2.regions.DACRegion;

public abstract class DACWERegion implements DACRegion {

    @Transient
    private Region weRegion;

    public DACWERegion() {
        weRegion = createRegion();
    }

    protected abstract Region createRegion();

    protected abstract Selection getSelection(World world);

    public boolean isWERegion() {
        return true;
    }

    private Region getWERegion() {
        return weRegion;
    }

    @Override
    public boolean contains(Vector vector) {
        return getWERegion().contains(bu2we(vector));
    }
}
