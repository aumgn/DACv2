package fr.aumgn.dac2.game;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.shape.column.Column;

public abstract class AbstractGame implements Game {

    protected final DAC dac;
    protected final Arena arena;
    protected final Listener listener;

    public AbstractGame(DAC dac, GameStartData data) {
        this.dac = dac;
        this.arena = data.getArena();
        this.listener = new GameListener(this);
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public Listener[] getListeners() {
        return new Listener[] { listener };
    }

    protected void send(String key, Object... arguments) {
        sendMessage(dac.getMessages().get(key, arguments));
    }

    @Override
    public void onNewTurn() {
    }

    protected void resetPoolOnStart() {
        if (dac.getConfig().getResetOnStart()) {
            arena.getPool().reset(arena.getWorld());
        }
    }

    protected void resetPoolOnEnd() {
        if (dac.getConfig().getResetOnEnd()) {
            arena.getPool().reset(arena.getWorld());
        }
    }

    protected void tpBeforeJump(GamePlayer player) {
        if (dac.getConfig().getTpBeforeJump()) {
            player.teleport(arena.getDiving().toLocation(arena.getWorld()));
        }
    }

    protected void tpAfterJumpSuccess(final GamePlayer player, Column column) {
        int delay = dac.getConfig().getTpAfterJumpSuccessDelay();
        if (delay == 0) {
            player.tpToStart();
            return;
        }

        player.teleport(arena.getWorld(), column.getTop().addY(1));
        if (delay > 0) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(dac.getPlugin(),
                    player.delayedTpToStart(), delay);
        }
    }

    protected void tpAfterJumpFail(final GamePlayer player) {
        int delay = dac.getConfig().getTpAfterJumpFailDelay();
        if (delay == 0) {
            player.tpToStart();
        } else if (delay > 0) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(dac.getPlugin(),
                    player.delayedTpToStart(), delay);
        }
    }
}
