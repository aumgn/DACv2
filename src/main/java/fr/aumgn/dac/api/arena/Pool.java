package fr.aumgn.dac.api.arena;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import fr.aumgn.dac.api.area.VerticalArea;

public interface Pool extends VerticalArea {

    boolean isAbove(Player player);

    void reset();

    void rip(Vector value, String displayName);

    boolean isADACPattern(int x, int z);

}
