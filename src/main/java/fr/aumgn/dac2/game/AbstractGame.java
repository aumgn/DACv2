package fr.aumgn.dac2.game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.aumgn.bukkitutils.timer.Timer;
import fr.aumgn.bukkitutils.util.Util;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.event.player.DACJumpDACEvent;
import fr.aumgn.dac2.event.player.DACJumpFailEvent;
import fr.aumgn.dac2.event.player.DACJumpSuccessEvent;
import fr.aumgn.dac2.event.player.DACPlayerEliminatedEvent;
import fr.aumgn.dac2.event.player.DACPlayerQuitEvent;
import fr.aumgn.dac2.event.player.DACPlayerWinEvent;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.shape.column.Column;
import fr.aumgn.dac2.stage.Spectators;

/**
 * Common base implementation of most game mode.
 */
public abstract class AbstractGame<T extends GamePlayer> implements Game {

    private static final String DEFAULT_L18N_NAMESPACE = "game.";

    protected final DAC dac;
    protected final Arena arena;
    protected final Listener listener;
    private final String l18nNamespace;
    protected final GameParty<T> party;
    protected final Spectators spectators;

    private final Runnable turnTimedOutRunnable;
    private Timer turnTimer;

    public AbstractGame(DAC dac, GameStartData data, String l18nNamespace,
            GamePlayer.Factory<T> factory) {
        this(dac, data, l18nNamespace, factory, true);
    }

    public AbstractGame(DAC dac, GameStartData data, String l18nNamespace,
            GamePlayer.Factory<T> factory, boolean useTimer) {
        this.dac = dac;
        this.arena = data.getArena();
        this.listener = new GameListener(this);
        this.l18nNamespace = l18nNamespace + ".";
        this.party = new GameParty<T>(this, data.getPlayers(), factory);
        this.spectators = new Spectators(data.getSpectators());

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
        String[] keys = { l18nNamespace + key, DEFAULT_L18N_NAMESPACE + key };
        sendMessage(dac.getMessages().get(keys, arguments));
    }

    @Override
    public void sendMessage(String message) {
        for (GamePlayer player : party.iterable()) {
            player.sendMessage(message);
        }
        spectators.send(dac, arena, message);
    }

    @Override
    public Spectators getSpectators() {
        return spectators;
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

    protected void autoGameMode() {
        if (dac.getConfig().getAutoGameMode()) {
            for (T gamePlayer : party.iterable()) {
                Player player = gamePlayer.getRef().getPlayer();
                if (player != null) {
                    player.setGameMode(GameMode.SURVIVAL);
                }
            }
        }
    }

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

    protected DACJumpSuccessEvent callJumpSuccessEvent(T player, Column column,
            boolean dac) {
        DACJumpSuccessEvent event;
        if (dac) {
            event = new DACJumpDACEvent(this, player, column);
        } else {
            event = new DACJumpSuccessEvent(this, player, column);
        }
        Util.callEvent(event);
        return event;
    }

    protected DACJumpFailEvent callJumpFailEvent(T player) {
        DACJumpFailEvent event = new DACJumpFailEvent(this, player);
        Util.callEvent(event);
        return event;
    }

    protected DACPlayerQuitEvent callPlayerQuitEvent(T player) {
        DACPlayerQuitEvent event = new DACPlayerQuitEvent(this, player);
        Util.callEvent(event);
        return event;
    }

    protected DACPlayerEliminatedEvent callPlayerEliminatedEvent(T player) {
        DACPlayerEliminatedEvent event = new DACPlayerEliminatedEvent(this, player);
        Util.callEvent(event);
        return event;
    }

    protected DACPlayerWinEvent callPlayerWinEvent(T player) {
        DACPlayerWinEvent event = new DACPlayerWinEvent(this, player);
        Util.callEvent(event);
        return event;
    }
}
