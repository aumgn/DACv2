package fr.aumgn.dac.game.options;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("dac-game-options")
public class GameOptions implements ConfigurationSerializable{
	
	private Map<String, String> options;
	
	public GameOptions() {
		this(new HashMap<String, String>());
	}
	
	public GameOptions(Map<String, String> options) {
		this.options = options;
	}
	
	public String get(String name, String default_) {
		if (options.containsKey(name)) {
			return options.get(name);
		} else {
			return default_;
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
			if (entry.getValue() instanceof String) {
				options.put(entry.getKey(), (String) entry.getValue());
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

}
