package fr.aumgn.dac.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.exception.WorldEditNotLoaded;
import fr.aumgn.dac.api.fillstrategy.DACFillStrategyProvider;
import fr.aumgn.dac.api.fillstrategy.FillStrategy;
import fr.aumgn.dac.api.fillstrategy.defaults.FillAllButOne;
import fr.aumgn.dac.api.fillstrategy.defaults.FillDAC;
import fr.aumgn.dac.api.fillstrategy.defaults.FillFully;
import fr.aumgn.dac.api.fillstrategy.defaults.FillRandomly;
import fr.aumgn.dac.api.game.mode.DACGameModeProvider;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.plugin.arena.DACArenas;
import fr.aumgn.dac.plugin.command.DACPluginCommand;
import fr.aumgn.dac.plugin.config.DACPluginConfig;
import fr.aumgn.dac.plugin.mode.classic.ClassicGameMode;
import fr.aumgn.dac.plugin.mode.suddendeath.SuddenDeathGameMode;
import fr.aumgn.dac.plugin.mode.training.TrainingGameMode;

public class DACPlugin extends JavaPlugin implements DACGameModeProvider, DACFillStrategyProvider {

    @Override
    public List<Class<? extends GameMode<?>>> getGameModes() {
        List<Class<? extends GameMode<?>>> list = new ArrayList<Class<? extends GameMode<?>>>(3);
        list.add(ClassicGameMode.class);
        list.add(TrainingGameMode.class);
        list.add(SuddenDeathGameMode.class);
        return list;
    }

    @Override
    public List<Class<? extends FillStrategy>> getFillStrategies() {
        List<Class<? extends FillStrategy>> list = new ArrayList<Class<? extends FillStrategy>>(4);
        list.add(FillFully.class);
        list.add(FillRandomly.class);
        list.add(FillDAC.class);
        list.add(FillAllButOne.class);
        return list;
    }

    @Override
    public void onEnable() {
        PluginManager pm = Bukkit.getPluginManager();

        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }
        if (!new File(getDataFolder(), "messages.yml").exists()) {
            saveResource("messages.yml", false);
        }

        Plugin worldEdit = pm.getPlugin("WorldEdit");
        if (!(worldEdit instanceof WorldEditPlugin)) {
            throw new WorldEditNotLoaded();
        }

        DACPluginCommand dacCommand = new DACPluginCommand();
        Bukkit.getPluginCommand("dac").setExecutor(dacCommand);

        DACListener dacPlayerListener = new DACListener();
        pm.registerEvents(dacPlayerListener, this);

        DACGameModes gameModes = new DACGameModes();
        DACArenas arenas = new DACArenas();
        DACFillStrategies fillStrategies = new DACFillStrategies();
        DAC.init(this, (WorldEditPlugin) worldEdit, new DACPluginConfig(), gameModes, arenas, fillStrategies);

        getLogger().info(getDescription().getName() + " loaded.");
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        DAC.getArenas().dump();
        getLogger().info(getDescription().getName() + " unloaded.");
    }

}
