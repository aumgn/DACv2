package fr.aumgn.dac2.game.colonnisation;

import java.util.Arrays;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.localization.PluginMessages;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.regions.Pool;
import fr.aumgn.dac2.game.AbstractGame;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.shape.column.Column;
import fr.aumgn.dac2.shape.column.ColumnPattern;
import fr.aumgn.dac2.shape.column.GlassyPattern;

public class Colonnisation extends AbstractGame<ColonnPlayer> {

    private boolean finished;
    private int setupTurns;

    public Colonnisation(DAC dac, GameStartData data) {
        super(dac, data, "colonnisation", new ColonnPlayer.Factory());
        finished = false;
    }

    @Override
    public void start() {
        autoGameMode();
        resetPoolOnStart();

        double size = arena.getPool().size2D();
        double setupColumns = size * dac.getConfig().getColonnisationRatio();
        setupTurns = (int) Math.ceil(setupColumns / party.size());

        send("start");
        send("playerslist");
        for (ColonnPlayer player : party.iterable()) {
            send("start.playerentry", player.getIndex() + 1,
                    player.getDisplayName());
        }
        send("setup.turns", setupTurns);
        nextTurn();
    }

    @Override
    public void onNewTurn() {
        setupTurns--;
        if (setupTurns == 0) {
            send("setup.finished");
            send("enjoy");
        }
    }

    private void nextTurn() {
        ColonnPlayer player = party.nextTurn();

        cancelTurnTimer();
        if (!player.isOnline()) {
            send("playerturn.notconnected",
                    player.getDisplayName());
            removePlayer(player);
            if (!finished) {
                nextTurn();
            }
            return;
        }

        send("playerturn", player.getDisplayName());
        tpBeforeJump(player);
        startTurnTimer();
    }

    @Override
    protected void turnTimedOut() {
        ColonnPlayer player = party.getCurrent();
        removePlayer(player);
        if (!finished) {
            nextTurn();
        }
    }

    private void removePlayer(ColonnPlayer player) {
        party.remove(player);
        spectators.add(player.getRef());
        if (party.size() < 2) {
            dac.getStages().stop(this);
        }
    }

    @Override
    public void stop(boolean force) {
        cancelTurnTimer();
        finished = true;
        resetPoolOnEnd();

        if (force) {
            send("stopped");
        } else {
            send("finished");
        }

        ColonnPlayer[] ranking = party.iterable().clone();
        Arrays.sort(ranking);

        int index = ranking.length - 1;
        send("winner", ranking[index].getDisplayName(),
                ranking[index].getScore());
        index--;
        for (; index >= 0; index--) {
            send("rank", ranking.length - index,
                    ranking[index].getDisplayName(),
                    ranking[index].getScore());
        }
    }

    @Override
    public void onJumpSuccess(Player player) {
        ColonnPlayer gamePlayer = party.get(player);
        World world = arena.getWorld();
        Pool pool = arena.getPool();

        Column column = pool.getColumn(player);
        ColumnPattern pattern;
        if (setupTurns > 0) {
            pattern = dac.getConfig().getNeutralPattern();
            send("setup.success", gamePlayer.getDisplayName());
        } else {
            pattern = gamePlayer.getColumnPattern();
            boolean isADAC = column.isADAC(world);
            if (isADAC) {
                gamePlayer.incrementMultiplier();
                pattern = new GlassyPattern(pattern);
            }

            PoolVisitor visitor = new PoolVisitor(world, pool,
                    gamePlayer.getColor());
            int points = visitor.visit(column.getPos());
            points *= gamePlayer.getMultiplier();
            gamePlayer.addPoints(points);

            if (isADAC) {
                send("multiplier.increment",
                        gamePlayer.getMultiplier());
            }
            send("jump.success", gamePlayer.getDisplayName(),
                    points, gamePlayer.getScore());
        }

        column.set(world, pattern);
        tpAfterJumpSuccess(gamePlayer, column);
        if (pool.isFilled(world)) {
            dac.getStages().stop(this);
        } else {
            nextTurn();
        }
    }

    @Override
    public void onJumpFail(Player player) {
        ColonnPlayer gamePlayer = party.get(player);

        send("jump.fail", gamePlayer.getDisplayName());
        if (gamePlayer.getMultiplier() > 1) {
            gamePlayer.resetMultiplier();
            send("multiplier.reset");
        }

        tpAfterJumpFail(gamePlayer);
        nextTurn();
    }

    @Override
    public void onQuit(Player player) {
        ColonnPlayer gamePlayer = party.get(player);
        removePlayer(gamePlayer);
        send("player.quit", gamePlayer.getDisplayName(),
                gamePlayer.getScore());
        if (!finished) {
            nextTurn();
        }
    }

    @Override
    public void list(CommandSender sender) {
        PluginMessages messages = dac.getMessages();

        sender.sendMessage(messages.get("colonnisation.playerslist"));
        for (ColonnPlayer player : party.iterable()) {
            sender.sendMessage(messages.get("colonnisation.playerentry",
                    player.getIndex(), player.getDisplayName(),
                    player.getScore()));
        }
    }
}
