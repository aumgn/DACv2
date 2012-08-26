package fr.aumgn.dac2.config;

import java.util.Locale;

import fr.aumgn.bukkitutils.timer.TimerConfig;
import fr.aumgn.bukkitutils.util.Util;
import fr.aumgn.dac2.utils.DACUtil;

public class DACConfig {

    private String language = Locale.getDefault().toString();
    private PoolReset poolReset = PoolReset.START;
    private String timerFormat = "%02d:%02d";
    private int timeOut = 60;
    private boolean tpBeforeJump = true;
    private int tpAfterJumpSuccessDelay = 0;
    private int tpAfterJumpFailDelay = 3;

    public Locale getLocale() {
        return Util.parseLocale(language);
    }

    public boolean getResetOnStart() {
        return (poolReset.flag() & PoolReset.START.flag()) != 0;
    }

    public boolean getResetOnEnd() {
        return (poolReset.flag() & PoolReset.END.flag()) != 0;
    }

    public TimerConfig getTimerConfig() {
        return new TimerConfig(timeOut / 2, timeOut / 4, timerFormat, "");
    }

    public int getTimeOut() {
        return timeOut;
    }

    public boolean getTpBeforeJump() {
        return tpBeforeJump;
    }

    public int getTpAfterJumpSuccessDelay() {
        return tpAfterJumpSuccessDelay * DACUtil.TICKS_PER_SECONDS;
    }

    public int getTpAfterJumpFailDelay() {
        return tpAfterJumpFailDelay * DACUtil.TICKS_PER_SECONDS;
    }
}
