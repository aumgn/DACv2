package fr.aumgn.dac2;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.aumgn.bukkitutils.command.arg.CommandArgFactory;
import fr.aumgn.bukkitutils.gson.GsonLoadException;
import fr.aumgn.bukkitutils.gson.GsonLoader;
import fr.aumgn.bukkitutils.gson.typeadapter.DirectionTypeAdapterFactory;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.arena.regions.GsonRegionFactory;
import fr.aumgn.dac2.commands.AdminCommands;
import fr.aumgn.dac2.commands.ArenasCommands;
import fr.aumgn.dac2.commands.SetupCommands;
import fr.aumgn.dac2.commands.StageCommands;
import fr.aumgn.dac2.commands.arg.ArenaArg;
import fr.aumgn.dac2.commands.arg.StageArg;
import fr.aumgn.dac2.commands.worldedit.DisabledWorldEditCommands;
import fr.aumgn.dac2.commands.worldedit.SelectCommands;
import fr.aumgn.dac2.commands.worldedit.SetupWECommands;
import fr.aumgn.dac2.config.DACConfig;
import fr.aumgn.dac2.stage.Stage;

public class DACPlugin extends JavaPlugin {

    private final static double GSON_VERSION = 1.0;

    private DAC dac;

    @Override
    public void onEnable() {
        dac = new DAC(this);

        CommandArgFactory.register(Arena.class, new ArenaArg.Factory(dac));
        CommandArgFactory.register(Stage.class, new StageArg.Factory(dac));
        CommandsRegistration registration = new CommandsRegistration(
                this, dac.getConfig().getLocale());
        registration.register(new AdminCommands(dac));
        registration.register(new ArenasCommands(dac));
        registration.register(new StageCommands(dac));
        registration.register(new SetupCommands(dac));
        if (isWorldEditEnabled()) {
            registration.register(new SetupWECommands(dac));
            registration.register(new SelectCommands(dac));
        } else {
            registration.register(new DisabledWorldEditCommands(dac));
        }

        getLogger().info("Enabled.");
    }

    public boolean isWorldEditEnabled() {
        return Bukkit.getPluginManager().isPluginEnabled("WorldEdit");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled.");
    }

    public GsonLoader getGsonLoader() {
        Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new DirectionTypeAdapterFactory())
            .registerTypeAdapterFactory(new GsonRegionFactory())
            .setVersion(GSON_VERSION)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
            .setPrettyPrinting()
            .create();
        return new GsonLoader(gson, this);
    }

    public DACConfig reloadDACConfig() {
        try {
            GsonLoader loader = getGsonLoader();
            return loader.loadOrCreate("config.json", DACConfig.class);
         } catch (GsonLoadException exc) {
             getLogger().warning(
                     "Unable to load configuration file.");
             getLogger().warning("Using default values instead.");
             return new DACConfig();
         }
    }
}
