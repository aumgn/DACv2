package fr.aumgn.dac.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.aumgn.bukkitutils.command.messages.FrenchMessages;
import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.exception.WorldEditNotLoaded;
import fr.aumgn.dac.api.fillstrategy.FillAllButOne;
import fr.aumgn.dac.api.fillstrategy.FillDAC;
import fr.aumgn.dac.api.fillstrategy.FillFully;
import fr.aumgn.dac.api.fillstrategy.FillRandomly;
import fr.aumgn.dac.api.fillstrategy.FillStrategies;
import fr.aumgn.dac.api.game.mode.DACGameModeProvider;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.plugin.DACConfigLoader.Error;
import fr.aumgn.dac.plugin.arena.DACArenas;
import fr.aumgn.dac.plugin.command.AdminCommands;
import fr.aumgn.dac.plugin.command.ArenasCommands;
import fr.aumgn.dac.plugin.command.GameCommands;
import fr.aumgn.dac.plugin.command.ModesCommands;
import fr.aumgn.dac.plugin.command.OptionsCommands;
import fr.aumgn.dac.plugin.command.PlayerCommands;
import fr.aumgn.dac.plugin.command.PoolCommands;
import fr.aumgn.dac.plugin.command.SetupCommands;
import fr.aumgn.dac.plugin.command.SpectatorCommands;
import fr.aumgn.dac.plugin.config.DACPluginConfig;
import fr.aumgn.dac.plugin.mode.classic.ClassicGameMode;
import fr.aumgn.dac.plugin.mode.suddendeath.SuddenDeathGameMode;
import fr.aumgn.dac.plugin.mode.training.TrainingGameMode;

public class DACPlugin extends JavaPlugin implements DACGameModeProvider {

    private FileConfiguration dacConfig;

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

        registerCommands();

        DACListener listener = new DACListener();

        DACGameModes gameModes = new DACGameModes();
        DACArenas arenas = new DACArenas();
        DAC.init(this, (WorldEditPlugin) worldEdit, listener, new DACPluginConfig(), gameModes, arenas);

        FillStrategies fillStrategies = DAC.getFillStrategies();
        fillStrategies.register(new FillFully(), "fully");
        fillStrategies.register(new FillRandomly(), "randomly", "rd");
        fillStrategies.register(new FillAllButOne(), "all-but-one", "abo");
        fillStrategies.register(new FillDAC(), "dac");

        getLogger().info(getDescription().getName() + " Enabled.");
    }

    private void registerCommands() {
        CommandsRegistration registration = new CommandsRegistration(
                this, new FrenchMessages());
        registration.register(new AdminCommands());
        registration.register(new ArenasCommands());
        registration.register(new GameCommands());
        registration.register(new ModesCommands());
        registration.register(new OptionsCommands());
        registration.register(new PlayerCommands());
        registration.register(new PoolCommands());
        registration.register(new SetupCommands());
        registration.register(new SpectatorCommands());
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        DAC.getArenas().dump();
        getLogger().info(getDescription().getName() + " Disabled.");
    }

    @Override
    public FileConfiguration getConfig() {
        if (dacConfig == null) {
            reloadConfig();
        }

        return dacConfig;
    }

    @Override
    public void reloadConfig() {
        try {
            dacConfig = new DACConfigLoader().loadWithDefaults(this, "config.yml");
        } catch (Error exc) {
            getLogger().warning("An error occured while loading config.yml.");
            exc.printStackTrace();
        }
    }
}
