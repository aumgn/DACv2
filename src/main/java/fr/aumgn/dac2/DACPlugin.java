package fr.aumgn.dac2;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.aumgn.bukkitutils.command.arg.CommandArgFactory;
import fr.aumgn.bukkitutils.gconf.GConfLoadException;
import fr.aumgn.bukkitutils.gconf.GConfLoader;
import fr.aumgn.bukkitutils.gconf.typeadapter.DirectionTypeAdapterFactory;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.arena.regions.GsonRegionFactory;
import fr.aumgn.dac2.commands.AdminCommands;
import fr.aumgn.dac2.commands.ArenasCommands;
import fr.aumgn.dac2.commands.SetupCommands;
import fr.aumgn.dac2.commands.arg.ArenaArg;
import fr.aumgn.dac2.config.DACConfig;

public class DACPlugin extends JavaPlugin {

    private final static double GSON_VERSION = 1.0;

    private DAC dac;

    @Override
    public void onEnable() {
        dac = new DAC(this);

        CommandArgFactory.register(Arena.class, new ArenaArg.Factory(dac));
        CommandsRegistration registration = new CommandsRegistration(
                this, dac.getConfig().getLocale());
        registration.register(new AdminCommands(dac));
        registration.register(new ArenasCommands(dac));
        registration.register(new SetupCommands(dac));

        getLogger().info("Enabled.");
    }

    @Override
    public void onDisable() {
        dac.getArenas().save(dac);
        getLogger().info("Disabled.");
    }

    public GConfLoader getGsonLoader() {
        Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new DirectionTypeAdapterFactory())
            .registerTypeAdapterFactory(new GsonRegionFactory())
            .setVersion(GSON_VERSION)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
            .setPrettyPrinting()
            .create();
        return new GConfLoader(gson, this);
    }

    public DACConfig reloadDACConfig() {
        try {
            GConfLoader loader = getGsonLoader();
            return loader.loadOrCreate("config.json", DACConfig.class);
         } catch (GConfLoadException exc) {
             getLogger().warning(
                     "Impossible de charger le fichier de configuration.");
             getLogger().warning("Utilisation des valeurs par défaut.");
             return new DACConfig();
         }
    }
}
