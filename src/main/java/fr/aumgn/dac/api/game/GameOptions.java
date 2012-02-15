package fr.aumgn.dac.api.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("dac-game-options")
public class GameOptions implements ConfigurationSerializable, Iterable<Map.Entry<String, String>> {
	
	private Map<String, String> options;
	
	public static GameOptions parse(String[] options) {
		Map<String, String> map = new HashMap<String, String>();
		for (String option : options) {
			String[] parsed = option.split(":");
			if (parsed.length == 2) {
				map.put(parsed[0], parsed[1]);
			}
		}
		return new GameOptions(map);
	}
	
	public GameOptions() {
		this(new HashMap<String, String>());
	}
	
	public GameOptions(Map<String, String> options) {
		this.options = options;
	}
	
	public String get(String name, String def) {
		if (options.containsKey(name)) {
			return options.get(name);
		} else {
			return def;
		}
	}
	
	public String get(String name) {
		return get(name, "");
	}
	
	public GameOptions merge(GameOptions gameOptions) {
		HashMap<String, String> newOptions = new HashMap<String, String>();
		newOptions.putAll(options);
		newOptions.putAll(gameOptions.options);
		return new GameOptions(newOptions);
	}
	
	public static GameOptions deserialize(Map<String, Object> map) {
		HashMap<String, String> options = new HashMap<String, String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (!entry.getKey().equals(ConfigurationSerialization.SERIALIZED_TYPE_KEY)) {
				if (entry.getValue() instanceof String) {
					options.put(entry.getKey(), (String) entry.getValue());
				}
			}
		}
		return new GameOptions(options);
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Map.Entry<String, String> option : options.entrySet()) {
			map.put(option.getKey(), option.getValue());
		}
		return map; 
	}

	public void set(String name, String value) {
		options.put(name, value);
	}

	public void remove(String name) {
		options.remove(name);
	}

	@Override
	public Iterator<Entry<String, String>> iterator() {
		return options.entrySet().iterator();
	}
}
