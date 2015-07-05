package fr.aumgn.dac2.stage;

import fr.aumgn.bukkitutils.util.Util;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.event.stage.DACStageStartEvent;
import fr.aumgn.dac2.event.stage.DACStageStopEvent;
import fr.aumgn.dac2.exceptions.IncompleteArena;
import fr.aumgn.dac2.exceptions.StageAlreadyRunning;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class Stages {

    private final DAC dac;
    private final List<Stage> stages;

    public Stages(DAC dac) {
        this.dac = dac;
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

    public void start(Stage stage) {
        Arena arena = stage.getArena();
        if (get(stage.getArena()) != null) {
            throw new StageAlreadyRunning(dac.getMessages());
        }

        if (!arena.isComplete()) {
            throw new IncompleteArena(dac, arena);
        }

        DACStageStartEvent event = new DACStageStartEvent(stage);
        Util.callEvent(event);

        stages.add(stage);
        registerListeners(stage);
        stage.start();
    }

    public void stop(Stage stage) {
        doStop(stage, false);
    }

    public void forceStop(Stage stage) {
        doStop(stage, true);
    }

    private void doStop(Stage stage, boolean force) {
        DACStageStopEvent event = new DACStageStopEvent(stage, force);
        Util.callEvent(event);

        unregisterListeners(stage);
        stage.stop(force);
        stages.remove(stage);
    }

    public void switchTo(Stage stage) {
        doSwitchTo(stage, false);
    }

    public void silentlySwitchTo(Stage stage) {
        doSwitchTo(stage, true);
    }

    private void doSwitchTo(Stage stage, boolean silent) {
        Stage oldStage = get(stage.getArena());
        if (oldStage == null) {
            stages.add(stage);
        }
        else {
            if (!silent) {
                DACStageStopEvent event = new DACStageStopEvent(stage, false);
                Util.callEvent(event);
            }

            unregisterListeners(oldStage);
            oldStage.stop(false);
            int index = stages.indexOf(oldStage);
            stages.set(index, stage);
        }

        if (!silent) {
            DACStageStartEvent event = new DACStageStartEvent(stage);
            Util.callEvent(event);
        }

        registerListeners(stage);
        stage.start();
    }

    private void registerListeners(Stage stage) {
        for (Listener listener : stage.getListeners()) {
            Bukkit.getPluginManager()
                    .registerEvents(listener, dac.getPlugin());
        }
    }

    private void unregisterListeners(Stage stage) {
        for (Listener listener : stage.getListeners()) {
            HandlerList.unregisterAll(listener);
        }
    }
}
