package fr.aumgn.dac.api.stage;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;

public abstract class SimpleStage implements Stage {

    protected Arena arena;

    public SimpleStage(Arena arena) {
        this.arena = arena;
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public void registerAll() {
        StagePlayerManager playerManager = DAC.getPlayerManager();
        for (StagePlayer player : getPlayers()) {
            playerManager.register(player);
        }
    }

    @Override
    public void unregisterAll() {
        StagePlayerManager playerManager = DAC.getPlayerManager();
        for (StagePlayer player : getPlayers()) {
            playerManager.unregister(player, true);
        }
    }

    @Override
    public void send(Object msg) {
        for (StagePlayer player : getPlayers()) {
            player.getPlayer().sendMessage(msg.toString());
        }
    }

}
