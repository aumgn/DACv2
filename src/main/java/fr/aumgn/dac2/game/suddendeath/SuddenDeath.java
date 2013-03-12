package fr.aumgn.dac2.game.suddendeath;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.localization.PluginMessages;
import fr.aumgn.bukkitutils.playerref.map.PlayersRefHashMap;
import fr.aumgn.bukkitutils.playerref.map.PlayersRefMap;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.regions.PoolFilling;
import fr.aumgn.dac2.arena.regions.PoolFilling.AllButOne;
import fr.aumgn.dac2.game.AbstractGame;
import fr.aumgn.dac2.game.GameParty;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.shape.column.Column;
import fr.aumgn.dac2.stage.StagePlayer;

public class SuddenDeath extends AbstractGame {

    private GameParty<SuddenDeathPlayer> party;
    private PlayersRefMap<SuddenDeathPlayer> playersMap;
    private boolean finished;

    public SuddenDeath(DAC dac, GameStartData data) {
        super(dac, data);
        finished = false;

        Set<? extends StagePlayer> players = data.getPlayers();
        List<SuddenDeathPlayer> list =
                new ArrayList<SuddenDeathPlayer>(players.size());
        playersMap = new PlayersRefHashMap<SuddenDeathPlayer>();

        for (StagePlayer stagePlayer : players) {
            SuddenDeathPlayer player = new SuddenDeathPlayer(stagePlayer);
            list.add(player);
            playersMap.put(player.getRef(), player);
        }
        party = new GameParty<SuddenDeathPlayer>(this, SuddenDeathPlayer.class,
                list);
    }

    @Override
    public void start() {
        resetPool();

        send("suddendeath.start");
        send("suddendeath.playerslist");
        for (SuddenDeathPlayer player : party.iterable()) {
            send("suddendeath.start.playerentry", player.getIndex() + 1,
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
            send("suddendeath.allfailed");
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

        tpBeforeJump(player);
        send("suddendeath.playerturn", player.getDisplayName());
    }

    @Override
    public void stop(boolean force) {
        resetPoolOnEnd();
    }

    @Override
    public boolean contains(Player player) {
        return playersMap.containsKey(player);
    }

    @Override
    public void sendMessage(String message) {
        for (SuddenDeathPlayer player : party.iterable()) {
            player.sendMessage(message);
        }
        sendSpectators(message);
    }

    @Override
    public boolean isPlayerTurn(Player player) {
        SuddenDeathPlayer sdPlayer = playersMap.get(player);
        return sdPlayer != null && party.isTurn(sdPlayer);
    }

    @Override
    public void onJumpSuccess(Player player) {
        SuddenDeathPlayer sdPlayer = playersMap.get(player);
        Column column = arena.getPool().getColumn(player);

        sdPlayer.setSuccess();
        tpAfterJumpSuccess(sdPlayer, column);
        send("suddendeath.jump.success", sdPlayer.getDisplayName());
        nextTurn();
    }

    @Override
    public void onJumpFail(Player player) {
        SuddenDeathPlayer sdPlayer = playersMap.get(player);
        sdPlayer.setFail();
        tpAfterJumpFail(sdPlayer);
        send("suddendeath.jump.fail", sdPlayer.getDisplayName());
        nextTurn();
    }

    @Override
    public void onQuit(Player player) {
        SuddenDeathPlayer sdPlayer = playersMap.get(player);
        party.removePlayer(sdPlayer);
        playersMap.remove(player);
    }

    public void onPlayerWin(SuddenDeathPlayer player) {
        send("suddendeath.winner", player.getDisplayName());
        finished = true;
        dac.getStages().stop(this);
        resetPoolOnEnd();
    }

    public void eliminatePlayersWhoFailed() {
        for (SuddenDeathPlayer player : party.iterable().clone()) {
            if (!player.hasSucceeded()) {
                party.removePlayer(player);
                playersMap.remove(player.getRef());
                spectators.add(player.getRef());
                send("suddendeath.elimination", player.getDisplayName());
            }
        }
    }

    @Override
    public void list(CommandSender sender) {
        PluginMessages messages = dac.getMessages();

        sender.sendMessage(messages.get("suddendeath.playerslist"));
        for (SuddenDeathPlayer player : party.iterable()) {
            String key = "suddendeath.playerentry" + "."
                    + player.getLocalizationKeyForStatus();
            sender.sendMessage(messages.get(key, player.getIndex(),
                    player.getDisplayName()));
        }
    }
}
