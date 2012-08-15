package fr.aumgn.dac2;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.aumgn.bukkitutils.gconf.GConfLoadException;
import fr.aumgn.bukkitutils.gconf.GConfLoader;
import fr.aumgn.dac2.commands.AdminCommands;
import fr.aumgn.dac2.config.DACConfig;

public class DACPlugin extends JavaPlugin {

    private final static double GSON_VERSION = 1.0;

    @Override
    public void onEnable() {
        DAC dac = new DAC(this);

        CommandsRegistration registration = new CommandsRegistration(
                this, dac.getConfig().getLocale());
        registration.register(new AdminCommands(dac));

        getLogger().info("Enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled.");
    }

    private GConfLoader getGsonLoader() {
        Gson gson = new GsonBuilder()
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
