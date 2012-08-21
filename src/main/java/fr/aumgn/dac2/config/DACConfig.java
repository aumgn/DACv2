package fr.aumgn.dac2.config;

import java.util.Locale;

import fr.aumgn.bukkitutils.timer.TimerConfig;
import fr.aumgn.bukkitutils.util.Util;

public class DACConfig {

    private String language = Locale.getDefault().toString();
    private PoolReset poolReset = PoolReset.START;
    private TimerConfig timer = TimerConfig.DEFAULT;
    private int timeOut = 60;
    private boolean tpBeforeJump = true;
    private boolean tpAfterJump = true;
    private int tpAfterSuccessDelay = 0;

    public Locale getLocale() {
        return Util.parseLocale(language);
    }

    public boolean getResetOnStart() {
        return (poolReset.flag() & PoolReset.START.flag()) != 0;
    }

    public boolean getResetOnEnd() {
        return (poolReset.flag() & PoolReset.END.flag()) != 0;
    }

    public TimerConfig getTimer() {
        return timer;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public boolean getTpBeforeJump() {
        return tpBeforeJump;
    }

    public boolean getTpAfterJump() {
        return tpAfterJump;
    }

    public int getTpAfterSuccessDelay() {
        return tpAfterSuccessDelay;
    }
}
