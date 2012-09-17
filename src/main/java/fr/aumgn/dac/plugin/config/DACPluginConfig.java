package fr.aumgn.dac.plugin.config;

import fr.aumgn.dac.api.config.DACConfig;

public class DACPluginConfig implements DACConfig {

    private String autoReset = "start";
    private boolean cancelJumpDamage = true;
    private boolean putColumn = true;
    private int turnTimeOut = 1200;
    private boolean tpBeforeJump = true;
    private boolean tpAfterJump = true; 
    private int tpAfterFail = 60;
    private int safeRegionHeight = 5;
    private int safeRegionMargin = 5;
    private boolean prefixSpectatorsMessages = true;
    private String deathSignFirstLine;

    public DACPluginConfig() {}

    @Override
    public boolean getResetOnStart() {
        return autoReset.equals("start")
                || autoReset.equals("both");
    }

    @Override
    public boolean getResetOnEnd() {
        return autoReset.equals("end")
                || autoReset.equals("both");
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
    public boolean getTpBeforeJump() {
        return tpBeforeJump;
    }

    @Override
    public boolean getTpAfterJump() {
        return tpAfterJump;
    }

    @Override
    public boolean getTpAfterFail() {
        return tpAfterFail >= 0;
    }

    @Override
    public int getTpAfterFailDelay() {
        return tpAfterFail;
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
    public boolean getPrefixSpectatorsMessages() {
        return prefixSpectatorsMessages;
    }

    @Override
    public String getDeathSignFirstLine() {
        return deathSignFirstLine;
    }

}
