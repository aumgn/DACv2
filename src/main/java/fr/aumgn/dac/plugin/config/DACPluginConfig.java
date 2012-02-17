package fr.aumgn.dac.plugin.config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACColors;
import fr.aumgn.dac.api.config.DACConfig;
import fr.aumgn.dac.api.util.DACUtil;

public class DACPluginConfig implements DACConfig {

    private boolean resetOnStart = false;
    private boolean resetOnEnd = false;
    private int turnTimeOut;
    private boolean tpAfterJump;
    private boolean tpAfterFail;
    private int tpAfterJumpDelay = 0;
    private int tpAfterFailDelay = 0;
    private String deathSignFirstLine;
    private DACColors colors;

    public DACPluginConfig() {}

    @Override
    public void load(Configuration config) {
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
        if (tpAfterJump) {
            tpAfterJumpDelay = tpAfterJumpConfig;
        }
        if (tpAfterFail) {
            tpAfterFailDelay = tpAfterFailConfig;
        }
        deathSignFirstLine = DACUtil.parseColorsMarkup(config.getString("death-sign-first-line"));
        if (deathSignFirstLine.length() > DACUtil.SIGN_MAX_CHAR) {
            deathSignFirstLine = DACUtil.parseColorsMarkup(config.getDefaults().getString("death-sign-first-line"));
            DAC.getLogger().warning("Config parameter 'death-sign-first-line' is longer than 16. Falling back to defaut value.");
        }
        ConfigurationSection colorsConfig = config.getConfigurationSection("colors");
        ConfigurationSection defColorsConfig = config.getDefaults().getConfigurationSection("colors");
        colors = new DACPluginColors(colorsConfig, defColorsConfig);
    }

    @Override
    public boolean getResetOnStart() {
        return resetOnStart;
    }

    @Override
    public boolean getResetOnEnd() {
        return resetOnEnd;
    }

    @Override
    public int getTurnTimeOut() {
        return turnTimeOut;
    }

    @Override
    public boolean getTpAfterJump() {
        return tpAfterJump;
    }

    @Override
    public boolean getTpAfterFail() {
        return tpAfterFail;
    }

    @Override
    public int getTpAfterSuccessDelay() {
        return tpAfterJumpDelay;
    }

    @Override
    public int getTpAfterFailDelay() {
        return tpAfterFailDelay;
    }

    @Override
    public int getMaxPlayers() {
        return colors.size();
    }

    @Override
    public String getDeathSignFirstLine() {
        return deathSignFirstLine;
    }

    @Override
    public DACColors getColors() {
        return colors;
    }

}
