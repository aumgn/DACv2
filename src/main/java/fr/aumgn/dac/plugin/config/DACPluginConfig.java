package fr.aumgn.dac.plugin.config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACColors;
import fr.aumgn.dac.api.config.DACConfig;
import fr.aumgn.dac.api.util.DACUtil;

public class DACPluginConfig implements DACConfig {

    private boolean resetOnStart;
    private boolean resetOnEnd;
    private boolean cancelJumpDamage;
    private boolean putColumn;
    private boolean tpAfterJump; 
    private boolean tpAfterFail;
    private int tpAfterFailDelay;
    private int safeRegionHeight;
    private int safeRegionMargin;
    private int turnTimeOut;
    private String deathSignFirstLine;
    private DACColors colors;

    public DACPluginConfig() {}

    @Override
    public void load(Configuration config) {
        String autoReset = config.getString("auto-reset");
        if (autoReset.equals("start")) {
            resetOnStart = true;
            resetOnEnd = false;
        } else if (autoReset.equals("end")) {
            resetOnStart = false;
            resetOnEnd = true;
        } else if (autoReset.equals("both")) {
            resetOnStart = true;
            resetOnEnd = true;
        } else {
            resetOnStart = false; 
            resetOnEnd = false;
        }
        cancelJumpDamage = config.getBoolean("cancel-jump-damage", true);
        putColumn = config.getBoolean("put-column", true);
        turnTimeOut = config.getInt("turn-timeout", 0);
        // Backward compatibility.
        if (config.isInt("tp-after-jump")) {
            tpAfterJump = config.getInt("tp-after-jump") > 0;
        } else {
            tpAfterJump = config.getBoolean("tp-after-jump", true);
        }
        int tpAfterFailConfig = config.getInt("tp-after-fail", 60);
        tpAfterFail = tpAfterFailConfig >= 0;
        if (tpAfterFail) {
            tpAfterFailDelay = tpAfterFailConfig;
        }
        safeRegionHeight = config.getInt("safe-region-height");
        safeRegionMargin = config.getInt("safe-region-margin");
        deathSignFirstLine = DACUtil.parseColorsMarkup(config.getString("death-sign-first-line"));
        if (deathSignFirstLine.length() > DACUtil.SIGN_MAX_CHAR) {
            deathSignFirstLine = DACUtil.parseColorsMarkup(config.getDefaults().getString("death-sign-first-line"));
            DAC.getLogger().warning("Config parameter 'death-sign-first-line' is longer than 16. Falling back to defaut value.");
        }
        ConfigurationSection colorsConfig = config.getConfigurationSection("colors");
        ConfigurationSection defColorsConfig = config.getDefaults().getConfigurationSection("colors");
        colors = new DACPluginColors(colorsConfig, defColorsConfig);

        // Update cached safe regions. 
        for (Arena arena : DAC.getArenas()) {
            arena.getPool().updateSafeRegion();
        }
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
    public boolean getCancelJumpDamage() {
        return cancelJumpDamage;
    }
    
    @Override
    public boolean getPutColumn() {
        return putColumn;
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
    public int getTpAfterFailDelay() {
        return tpAfterFailDelay;
    }

    @Override
    public int getSafeRegionHeight() {
        return safeRegionHeight;
    }

    @Override
    public int getSafeRegionMargin() {
        return safeRegionMargin;
    }

    @Override
    public int getTurnTimeOut() {
        return turnTimeOut;
    }

    @Override
    public int getMaxPlayers() {
        return colors.amount();
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
