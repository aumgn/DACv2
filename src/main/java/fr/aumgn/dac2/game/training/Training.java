package fr.aumgn.dac2.game.training;

import fr.aumgn.bukkitutils.localization.PluginMessages;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.regions.Pool;
import fr.aumgn.dac2.game.AbstractGame;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.shape.column.Column;
import fr.aumgn.dac2.shape.column.ColumnPattern;
import fr.aumgn.dac2.shape.column.GlassyPattern;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Training extends AbstractGame<TrainingPlayer> {

    public Training(DAC dac, GameStartData data) {
        super(dac, data, "training", new TrainingPlayer.Factory(), false);
    }

    @Override
    public void start() {
        autoGameMode();
        resetPoolOnStart();
        send("start");
        nextTurn();
    }

    private void nextTurn() {
        TrainingPlayer player = party.nextTurn();
        tpBeforeJump(player);
        send("playerturn", player.getDisplayName());
    }

    @Override
    public void stop(boolean force) {
        resetPoolOnEnd();
        for (TrainingPlayer player : party.iterable()) {
            player.sendStats(dac.getMessages());
        }
    }

    @Override
    public void onJumpSuccess(Player player) {
        TrainingPlayer trainingPlayer = party.get(player);
        World world = arena.getWorld();
        Pool pool = arena.getPool();
        Column column = pool.getColumn(player);
        ColumnPattern pattern = trainingPlayer.getColumnPattern();
        boolean isADAC = column.isADAC(world);
        callJumpSuccessEvent(trainingPlayer, column, isADAC);

        if (isADAC) {
            send("jump.dac", trainingPlayer.getDisplayName());
            trainingPlayer.incrementDacs();
            pattern = new GlassyPattern(pattern);
        }
        else {
            send("jump.success", trainingPlayer.getDisplayName());
            trainingPlayer.incrementSuccesses();
        }

        column.set(world, pattern);
        if (pool.isFilled(world)) {
            pool.reset(world);
        }
        tpAfterJumpSuccess(trainingPlayer, column);
        nextTurn();
    }

    @Override
    public void onJumpFail(Player player) {
        TrainingPlayer trainingPlayer = party.get(player);
        callJumpFailEvent(trainingPlayer);

        send("jump.fail", trainingPlayer.getDisplayName());
        trainingPlayer.incrementFails();

        if (party.size() != 1) {
            tpAfterJumpFail(trainingPlayer);
        }
        nextTurn();
    }

    @Override
    public void onQuit(Player player) {
        TrainingPlayer trainingPlayer = party.get(player);
        callPlayerQuitEvent(trainingPlayer);
        party.remove(trainingPlayer);
        trainingPlayer.sendStats(dac.getMessages());

        if (party.size() == 0) {
            stop(true);
        }
    }

    @Override
    public void list(CommandSender sender) {
        PluginMessages messages = dac.getMessages();

        sender.sendMessage(messages.get("training.playerslist"));
        for (TrainingPlayer player : party.iterable()) {
            sender.sendMessage(messages.get("training.playerentry",
                    player.getIndex(), player.getDisplayName(),
                    player.getSuccesses(), player.getDacs(),
                    player.getFails()));
        }
    }
}
