package fr.aumgn.dac.config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;


public class DACConfig {

	private boolean resetOnStart = false;
	private boolean resetOnEnd = false;
	private int turnTimeOut;
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
		turnTimeOut = config.getInt("turn-timeout");
		int tpAfterJumpConfig = config.getInt("tp-after-jump");
		int tpAfterFailConfig = config.getInt("tp-after-fail");
		tpAfterJump = tpAfterJumpConfig >= 0;
		tpAfterFail = tpAfterFailConfig >= 0;
		if (tpAfterJump) { tpAfterJumpDelay = tpAfterJumpConfig; }
		if (tpAfterFail) { tpAfterFailDelay = tpAfterFailConfig; }
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

	public int getTurnTimeOut() {
		return turnTimeOut;
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
