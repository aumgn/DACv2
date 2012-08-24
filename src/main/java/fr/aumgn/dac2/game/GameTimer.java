package fr.aumgn.dac2.game;

import fr.aumgn.bukkitutils.timer.Timer;
import fr.aumgn.dac2.DAC;

public class GameTimer extends Timer {

    private final Game game;

    public GameTimer(DAC dac, Game game) {
        super(dac.getPlugin(), dac.getConfig().getTimerConfig(),
                dac.getConfig().getTimeOut());
        this.game = game;
    }

    public GameTimer(DAC dac, Game game, Runnable runnable) {
        super(dac.getPlugin(), dac.getConfig().getTimerConfig(),
                dac.getConfig().getTimeOut(), runnable);
        this.game = game;
    }

    @Override
    public void sendTimeMessage(String string) {
        game.sendMessage(string);
    }
}
