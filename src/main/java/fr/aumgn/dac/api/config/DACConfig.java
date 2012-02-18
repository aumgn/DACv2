package fr.aumgn.dac.api.config;

import org.bukkit.configuration.Configuration;

public interface DACConfig {

    void load(Configuration config);

    boolean getResetOnStart();

    boolean getResetOnEnd();

    int getTurnTimeOut();

    boolean getTpAfterFail();

    int getTpAfterFailDelay();

    int getMaxPlayers();

    String getDeathSignFirstLine();

    DACColors getColors();

}