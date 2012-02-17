package fr.aumgn.dac.api.arena;

import java.util.List;

import org.bukkit.World;

import com.sk89q.worldedit.LocalWorld;

import fr.aumgn.dac.api.game.GameOptions;

public interface Arena {

    LocalWorld getWEWorld();

    World getWorld();

    String getName();

    DivingBoard getDivingBoard();

    Pool getPool();

    StartArea getStartArea();

    List<String> getModes();

    boolean hasMode(String string);

    void addMode(String modeName);

    void removeMode(String modeName);

    GameOptions getOptions();

}
