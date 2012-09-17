package fr.aumgn.dac.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class DACConfigLoader {

    public static class Error extends Exception {

        public Error(Throwable throwable) {
            super(throwable);
        }
    }

    private static final Charset UTF8 = Charset.forName("UTF-8");

    public FileConfiguration loadWithDefaults(Plugin plugin, String filename) throws Error {
        FileConfiguration config = load(plugin, filename);
        FileConfiguration defaults = loadDefaults(plugin, filename);
        config.setDefaults(defaults);
        return config;
    }

    public FileConfiguration load(Plugin plugin, String filename) throws Error {
        File file = new File(plugin.getDataFolder(), filename);
        if (!file.exists()) {
            createDefaultConfig(plugin, filename, file);
        }

        try {
            return load(new FileInputStream(file));
        } catch (FileNotFoundException exc) {
            throw new Error(exc);
        }
    }

    public FileConfiguration loadDefaults(Plugin plugin, String filename) throws Error {
       return load(plugin.getResource(filename));
    }

    private FileConfiguration load(InputStream istream) throws Error {
        try {
            InputStreamReader reader = new InputStreamReader(istream, UTF8);
            StringWriter writer = new StringWriter();

            readStream(reader, writer);
            String configString = writer.toString();
            reader.close();
            writer.close();
            YamlConfiguration config = new YamlConfiguration();
            config.loadFromString(configString);
            return config;
        } catch (FileNotFoundException exc) {
            throw new Error(exc);
        } catch (IOException exc) {
            throw new Error(exc);
        } catch (InvalidConfigurationException exc) {
            throw new Error(exc);
        }
    }

    public void createDefaultConfig(Plugin plugin, String filename, File file) throws Error {
        try {
            FileOutputStream ostream = new FileOutputStream(file);
            InputStream istream = plugin.getResource(filename);
            InputStreamReader reader = new InputStreamReader(istream, UTF8);
            OutputStreamWriter writer = new OutputStreamWriter(ostream, UTF8);
            readStream(reader, writer);
            reader.close();
            writer.close();
        } catch (FileNotFoundException exc) {
            throw new Error(exc);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void readStream(Reader reader, Writer writer) throws Error {
        try {
            int len;
            char[] buf = new char[1024];
            while ((len = reader.read(buf)) > 0) {
                writer.write(buf, 0, len);
            }
        } catch (IOException exc) {
            throw new Error(exc);
        }
    }
}
