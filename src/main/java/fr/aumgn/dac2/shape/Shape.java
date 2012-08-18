package fr.aumgn.dac2.shape;

import org.bukkit.World;

import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.aumgn.bukkitutils.geom.Vector;

public interface Shape {

    boolean contains(Vector pt);

    Selection getSelection(World world);
}
