package fr.aumgn.dac.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.aumgn.bukkitutils.gconf.GConfLoadException;
import fr.aumgn.bukkitutils.gconf.GConfLoader;
import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACColors;
import fr.aumgn.dac.api.config.DACConfig;
import fr.aumgn.dac.api.exception.WorldEditNotLoaded;
import fr.aumgn.dac.api.fillstrategy.FillAllButOne;
import fr.aumgn.dac.api.fillstrategy.FillDAC;
import fr.aumgn.dac.api.fillstrategy.FillFully;
import fr.aumgn.dac.api.fillstrategy.FillRandomly;
import fr.aumgn.dac.api.fillstrategy.FillStrategies;
import fr.aumgn.dac.api.game.mode.DACGameModeProvider;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.plugin.arena.DACArenas;
import fr.aumgn.dac.plugin.command.AdminCommands;
import fr.aumgn.dac.plugin.command.ArenasCommands;
import fr.aumgn.dac.plugin.command.GameCommands;
import fr.aumgn.dac.plugin.command.InfoCommands;
import fr.aumgn.dac.plugin.command.ModesCommands;
import fr.aumgn.dac.plugin.command.OptionsCommands;
import fr.aumgn.dac.plugin.command.PlayerCommands;
import fr.aumgn.dac.plugin.command.PoolCommands;
import fr.aumgn.dac.plugin.command.SetupCommands;
import fr.aumgn.dac.plugin.command.SpectatorCommands;
import fr.aumgn.dac.plugin.config.DACPluginColors;
import fr.aumgn.dac.plugin.config.DACPluginConfig;
import fr.aumgn.dac.plugin.mode.classic.ClassicGameMode;
import fr.aumgn.dac.plugin.mode.suddendeath.SuddenDeathGameMode;
import fr.aumgn.dac.plugin.mode.training.TrainingGameMode;

public class DACPlugin extends JavaPlugin implements DACGameModeProvider {

    private static final double GSON_VERSION = 0.0;

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
        DAC.init(this, (WorldEditPlugin) worldEdit, listener, gameModes, arenas);

        FillStrategies fillStrategies = DAC.getFillStrategies();
        fillStrategies.register(new FillFully(), "fully");
        fillStrategies.register(new FillRandomly(), "randomly", "rd");
        fillStrategies.register(new FillAllButOne(), "all-but-one", "abo");
        fillStrategies.register(new FillDAC(), "dac");

        getLogger().info(getDescription().getName() + " Enabled.");
    }

    private void registerCommands() {
        CommandsRegistration registration = new CommandsRegistration(
                this, Locale.FRENCH);
        registration.register(new AdminCommands());
        registration.register(new ArenasCommands());
        registration.register(new GameCommands());
        registration.register(new InfoCommands());
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

    private GConfLoader getLoader() {
        Gson gson = new GsonBuilder()
            .setVersion(GSON_VERSION)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
            .setPrettyPrinting()
            .create();
        return new GConfLoader(gson, this);
    }

    public DACConfig reloadDACConfig() {
        try {
           GConfLoader loader = getLoader();
           return loader.loadOrCreate("config.json", DACPluginConfig.class);
        } catch (GConfLoadException exc) {
            getLogger().warning(
                    "Impossible de charger le fichier de configuration.");
            getLogger().warning("Utilisation des valeurs par défaut.");
            return new DACPluginConfig();
        }
    }

    public DACColors reloadDACColors() {
        try {
            GConfLoader loader = getLoader();
            DACPluginColors colors = loader.loadOrCreate("colors.json", DACPluginColors.class);
            if (colors.size() < 2) {
                getLogger().warning("Il y a n'y a pas assez de couleurs.");
                getLogger().warning("Utilisation des couleurs par défaut.");
                if (colors.size() > 0) {
                    loader.write("colors.old.json", colors);
                }
                colors = DACPluginColors.getDefaults();
                loader.write("colors.json", colors);
            }
            return colors;
         } catch (GConfLoadException exc) {
             getLogger().warning(
                     "Impossible de charger le fichier de configuration.");
             getLogger().warning("Utilisation des couleurs par défaut.");
             return DACPluginColors.getDefaults();
         }
    }
}
