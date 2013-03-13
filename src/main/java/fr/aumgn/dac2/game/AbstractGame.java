package fr.aumgn.dac2.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.aumgn.bukkitutils.playerref.set.PlayersRefHashSet;
import fr.aumgn.bukkitutils.playerref.set.PlayersRefSet;
import fr.aumgn.bukkitutils.timer.Timer;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.shape.column.Column;

/**
 * Common base implementation of most game mode.
 */
public abstract class AbstractGame<T extends GamePlayer> implements Game {

    protected final DAC dac;
    protected final Arena arena;
    protected final Listener listener;
    protected final GameParty<T> party;
    protected final PlayersRefSet spectators;

    private final Runnable turnTimedOutRunnable;
    private Timer turnTimer;

    public AbstractGame(DAC dac, GameStartData data,
            GamePlayer.Factory<T> factory) {
        this(dac, data, factory, true);
    }

    public AbstractGame(DAC dac, GameStartData data,
            GamePlayer.Factory<T> factory, boolean useTimer) {
        this.dac = dac;
        this.arena = data.getArena();
        this.listener = new GameListener(this);
        this.party = new GameParty<T>(this, data.getPlayers(), factory);
        this.spectators = new PlayersRefHashSet();
        spectators.addAll(data.getSpectators());

        if (useTimer) {
            this.turnTimedOutRunnable = new Runnable() {
                @Override
                public void run() {
                    turnTimedOut();
                }
            };
        } else {
            this.turnTimedOutRunnable = null;
        }
        this.turnTimer = null;
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public Listener[] getListeners() {
        return new Listener[] { listener };
    }

    @Override
    public boolean contains(Player player) {
        return party.contains(player);
    }

    /**
     * Send a message which is recorded in the main PluginMessages instance.
     */
    protected void send(String key, Object... arguments) {
        sendMessage(dac.getMessages().get(key, arguments));
    }

    @Override
    public void sendMessage(String message) {
        for (GamePlayer player : party.iterable()) {
            player.sendMessage(message);
        }
        sendSpectators(message);
    }

    /**
     * Sends a message to this game's spectators by prefixing
     * the message with the arena's name as specified in the config.
     */
    protected void sendSpectators(String message) {
        String spectatorMessage = dac.getConfig().getSpectatorsMsg()
                .format(new String[] { arena.getName(), message });
        for (Player spectator : spectators.players()) {
            spectator.sendMessage(spectatorMessage);
        }
    }

    @Override
    public boolean isSpectator(Player player) {
        return spectators.contains(player);
    }

    @Override
    public void addSpectator(Player player) {
        spectators.add(player);
    }

    @Override
    public void removeSpectator(Player player) {
        spectators.remove(player);
    }

    @Override
    public void onNewTurn() {
    }

    /**
     * Check if it's the given player turn.
     *
     * This is used by {@link GameListener} to check if events need to be
     * processed. So this method should be optimized as much as possible
     * because some events (like {@link PlayerMoveEvent}) are heavy.
     */
    public boolean isPlayerTurn(Player player) {
        return party.isTurn(player);
    }

    public void startTurnTimer() {
        if (turnTimedOutRunnable == null) {
            return;
        }

        turnTimer = new GameTimer(dac, this, turnTimedOutRunnable);
        turnTimer.start();
    }

    public void cancelTurnTimer() {
        if (turnTimer != null) {
            turnTimer.cancel();
        }
    }

    protected void turnTimedOut() {
    }

    /**
     * Callback called when a player succeed.
     */
    public abstract void onJumpSuccess(Player player);

    /**
     * Callback called when a player failed.
     */
    public abstract void onJumpFail(Player player);

    /**
     * Resets the pool if configured to do so on game start.
     */
    protected void resetPoolOnStart() {
        if (dac.getConfig().getResetOnStart()) {
            arena.getPool().reset(arena.getWorld());
        }
    }

    /**
     * Resets the pool if configured to do so on game end.
     */
    protected void resetPoolOnEnd() {
        if (dac.getConfig().getResetOnEnd()) {
            arena.getPool().reset(arena.getWorld());
        }
    }

    /**
     * Teleport the player if configured to do so on player's turn.
     */
    protected void tpBeforeJump(GamePlayer player) {
        if (dac.getConfig().getTpBeforeJump()) {
            player.teleport(arena.getDiving().toLocation(arena.getWorld()));
        }
    }

    /**
     * Teleport the player if configured to do so after player's success.
     */
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

    /**
     * Teleport the player if configured to do so after player's failed jump.
     */
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
