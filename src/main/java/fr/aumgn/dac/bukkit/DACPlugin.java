package fr.aumgn.dac.bukkit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.command.DACCommand;
import fr.aumgn.dac.exception.WorldEditNotLoaded;
import fr.aumgn.dac.game.mode.DACGameModeProvider;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.game.mode.classic.ClassicGameMode;
import fr.aumgn.dac.game.mode.suddendeath.SuddenDeathGameMode;
import fr.aumgn.dac.game.mode.training.TrainingGameMode;

public class DACPlugin extends JavaPlugin implements DACGameModeProvider {

	@Override
	public List<Class<? extends GameMode>> getGameModes() {
		List<Class<? extends GameMode>> list = new ArrayList<Class<? extends GameMode>>(3);
		list.add(ClassicGameMode.class);
		list.add(TrainingGameMode.class);
		list.add(SuddenDeathGameMode.class);
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

		DACCommand dacCommand = new DACCommand();
		Bukkit.getPluginCommand("dac").setExecutor(dacCommand);

		DACListener dacPlayerListener = new DACListener();
		pm.registerEvents(dacPlayerListener, this);

		DAC.init(this, (WorldEditPlugin)worldEdit);
		
		getLogger().info(getDescription().getName() + " loaded.");
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		DAC.getArenas().dump();
		getLogger().info(getDescription().getName() + " unloaded.");
	}

}
