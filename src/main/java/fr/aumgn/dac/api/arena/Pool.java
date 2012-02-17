package fr.aumgn.dac.api.arena;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import fr.aumgn.dac.api.area.ColumnPattern;
import fr.aumgn.dac.api.area.VerticalArea;
import fr.aumgn.dac.api.config.DACColor;

public interface Pool extends VerticalArea {

    boolean isAbove(Player player);

    void reset();

    void rip(Vector value, String displayName);

    boolean isADACPattern(int x, int z);

    void setColumn(ColumnPattern pattern, DACColor color, int x, int z);

}
