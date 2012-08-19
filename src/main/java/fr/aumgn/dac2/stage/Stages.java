package fr.aumgn.dac2.stage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;

public class Stages {

    private final List<Stage> stages;

    public Stages(DAC dac) {
        this.stages = new ArrayList<Stage>(dac.getArenas().length());
    }

    public Stage get(Arena arena) {
        for (Stage stage : stages) {
            if (stage.getArena() == arena) {
                return stage;
            }
        }

        return null;
    }

    public Stage get(Player player) {
        for (Stage stage : stages) {
            if (stage.contains(player)) {
                return stage;
            }
        }

        return null;
    }
}
