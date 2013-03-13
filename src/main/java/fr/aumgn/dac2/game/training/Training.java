package fr.aumgn.dac2.game.training;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.localization.PluginMessages;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.regions.Pool;
import fr.aumgn.dac2.game.AbstractGame;
import fr.aumgn.dac2.game.GameParty;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.shape.column.Column;
import fr.aumgn.dac2.shape.column.ColumnPattern;
import fr.aumgn.dac2.shape.column.GlassyPattern;

public class Training extends AbstractGame<TrainingPlayer> {

    private GameParty<TrainingPlayer> party;

    public Training(DAC dac, GameStartData data) {
        super(dac, data, new TrainingPlayer.Factory());
    }

    @Override
    public void start() {
        resetPoolOnStart();
        send("training.start");
        nextTurn();
    }

    private void nextTurn() {
        TrainingPlayer player = party.nextTurn();
        tpBeforeJump(player);
        send("training.playerturn", player.getDisplayName());
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

        if (column.isADAC(world)) {
            send("training.jump.dac", trainingPlayer.getDisplayName());
            trainingPlayer.incrementDacs();
            pattern = new GlassyPattern(pattern);
        } else {
            send("training.jump.success", trainingPlayer.getDisplayName());
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
        send("training.jump.fail", trainingPlayer.getDisplayName());
        trainingPlayer.incrementFails();

        if (party.size() != 1) {
            tpAfterJumpFail(trainingPlayer);
        }
        nextTurn();
    }

    @Override
    public void onQuit(Player player) {
        TrainingPlayer trainingPlayer = party.get(player);
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
