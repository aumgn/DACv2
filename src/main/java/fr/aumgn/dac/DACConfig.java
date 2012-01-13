package fr.aumgn.dac;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

public class DACConfig {
	
	private boolean resetOnStart = false;
	private boolean resetOnEnd = false;
	private boolean tpAfterJump;
	private boolean tpAfterFail;
	private int tpAfterJumpDelay = 0;
	private int tpAfterFailDelay = 0;
	private DACColors colors;
	
	public DACConfig(Configuration config) {
		String autoReset = config.getString("auto-reset");
		if (autoReset.equals("start")) {
			resetOnStart = true;
		} else if (autoReset.equals("end")) {
			resetOnEnd = true;
		} else if (autoReset.equals("both")) {
			resetOnStart = true;
			resetOnEnd = true;
		}
		int _tpAfterJump = config.getInt("tp-after-jump");
		int _tpAfterFail = config.getInt("tp-after-fail");
		tpAfterJump = _tpAfterJump >= 0;
		tpAfterFail = _tpAfterFail >= 0;
		if (tpAfterJump) { tpAfterJumpDelay = _tpAfterJump; }
		if (tpAfterFail) { tpAfterFailDelay = _tpAfterFail; }
		ConfigurationSection colorsConfig = config.getConfigurationSection("colors");
		ConfigurationSection defColorsConfig = config.getDefaults().getConfigurationSection("colors");
		colors = new DACColors(colorsConfig, defColorsConfig);
	}
	
	public boolean getResetOnStart() {
		return resetOnStart;
	}

	public boolean getResetOnEnd() {
		return resetOnEnd;
	}

	public boolean getTpAfterJump() {
		return tpAfterJump;
	}
	
	public boolean getTpAfterFail() {
		return tpAfterFail;
	}
	
	public int getTpAfterSuccessDelay() {
		return tpAfterJumpDelay;
	}

	public int getTpAfterFailDelay() {
		return tpAfterFailDelay;
	}
	
	public int getMaxPlayers() {
		return colors.size();
	}
	
	public DACColors getColors() {
		return colors;
	}

}
