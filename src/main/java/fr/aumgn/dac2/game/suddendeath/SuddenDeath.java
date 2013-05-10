package fr.aumgn.dac2.game.suddendeath;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.localization.PluginMessages;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.regions.PoolFilling;
import fr.aumgn.dac2.arena.regions.PoolFilling.AllButOne;
import fr.aumgn.dac2.game.AbstractGame;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.shape.column.Column;

public class SuddenDeath extends AbstractGame<SuddenDeathPlayer> {

    private boolean finished;

    public SuddenDeath(DAC dac, GameStartData data) {
        super(dac, data, "suddendeath", new SuddenDeathPlayer.Factory());
        finished = false;
    }

    @Override
    public void start() {
        resetPool();

        send("start");
        send("playerslist");
        for (SuddenDeathPlayer player : party.iterable()) {
            send("start.playerentry", player.getIndex() + 1,
                    player.getDisplayName());
        }

        nextTurn();
    }

    private void resetPool() {
        AllButOne filling = new PoolFilling.AllButOne();
        filling.fill(arena.getWorld(), arena.getPool(),
                dac.getConfig().getNeutralPattern());
    }

    @Override
    public void onNewTurn() {
        int remaining = 0;
        SuddenDeathPlayer winner = null;
        for (SuddenDeathPlayer player : party.iterable()) {
            if (player.hasSucceeded()) {
                remaining++;
                winner = player;
            }
        }

        if (remaining == 0) {
            send("allfailed");
        } else if (remaining == 1) {
            onPlayerWin(winner);
            return;
        } else {
            eliminatePlayersWhoFailed();
        }

        for (SuddenDeathPlayer player : party.iterable()) {
            player.resetStatus();
        }

        resetPool();
    }

    private void nextTurn() {
        SuddenDeathPlayer player = party.nextTurn();
        if (finished) {
            return;
        }

        cancelTurnTimer();
        send("playerturn", player.getDisplayName());
        tpBeforeJump(player);
        startTurnTimer();
    }

    @Override
    public void stop(boolean force) {
        cancelTurnTimer();
        resetPoolOnEnd();
    }

    @Override
    public void onJumpSuccess(Player player) {
        SuddenDeathPlayer sdPlayer = party.get(player);
        Column column = arena.getPool().getColumn(player);

        send("jump.success", sdPlayer.getDisplayName());
        sdPlayer.setSuccess();
        tpAfterJumpSuccess(sdPlayer, column);
        nextTurn();
    }

    @Override
    public void onJumpFail(Player player) {
        SuddenDeathPlayer sdPlayer = party.get(player);

        send("jump.fail", sdPlayer.getDisplayName());
        sdPlayer.setFail();
        tpAfterJumpFail(sdPlayer);
        nextTurn();
    }

    @Override
    public void onQuit(Player player) {
        SuddenDeathPlayer sdPlayer = party.get(player);
        party.remove(sdPlayer);
    }

    public void onPlayerWin(SuddenDeathPlayer player) {
        send("winner", player.getDisplayName());
        finished = true;
        dac.getStages().stop(this);
        resetPoolOnEnd();
    }

    public void eliminatePlayersWhoFailed() {
        for (SuddenDeathPlayer player : party.iterable().clone()) {
            if (!player.hasSucceeded()) {
                send("elimination", player.getDisplayName());
                party.remove(player);
                spectators.add(player.getRef());
            }
        }
    }

    @Override
    public void list(CommandSender sender) {
        PluginMessages messages = dac.getMessages();

        sender.sendMessage(messages.get("suddendeath.playerslist"));
        for (SuddenDeathPlayer player : party.iterable()) {
            String key = "suddendeath.playerentry."
                    + player.getLocalizationKeyForStatus();
            sender.sendMessage(messages.get(key, player.getIndex(),
                    player.getDisplayName()));
        }
    }
}
