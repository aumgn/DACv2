package fr.aumgn.dac.plugin.mode.classic;

import java.util.ArrayList;
import java.util.List;

import fr.aumgn.dac.api.area.column.GlassyColumn;
import fr.aumgn.dac.api.area.column.UniformColumn;
import fr.aumgn.dac.api.arena.Pool;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.TurnBasedGame;
import fr.aumgn.dac.api.game.event.GameEvent;
import fr.aumgn.dac.api.game.event.GameFinish;
import fr.aumgn.dac.api.game.event.GameJumpFail;
import fr.aumgn.dac.api.game.event.GameJumpSuccess;
import fr.aumgn.dac.api.game.event.GameNewTurn;
import fr.aumgn.dac.api.game.event.GamePoolFilled;
import fr.aumgn.dac.api.game.event.GameStart;
import fr.aumgn.dac.api.game.event.GameTurn;
import fr.aumgn.dac.api.game.event.GameWin;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.game.mode.SimpleGameHandler;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.util.RIPSign;
import fr.aumgn.dac.plugin.mode.suddendeath.SuddenDeathGameHandler;
import fr.aumgn.dac.plugin.mode.suddendeath.SuddenDeathGameMode;

public class ClassicGameHandler extends SimpleGameHandler {

    private boolean suddenDeath = false;

    @Override
    public void onStart(GameStart event) {
        event.send(DACMessage.GameStart);
        event.send(DACMessage.GamePlayers);
        for (ClassicGamePlayer player : event.getPlayers(ClassicGamePlayer.class)) {
            player.getDisplayName();
            event.send(DACMessage.GamePlayerList, player.getIndex(), player.getDisplayName());
        }
        event.send(DACMessage.GameEnjoy);
    }

    @Override
    public void onNewTurn(GameNewTurn event) {
        Pool pool = event.getArena().getPool();
        for (StagePlayer stagePlayer : event.getGame().getPlayers()) {
            ClassicGamePlayer player = (ClassicGamePlayer) stagePlayer;
            if (player.isDead()) {
                RIPSign sign = new RIPSign(pool, player.getDeathPosition()); 
                sign.rip(player.getDisplayName());
                event.addLoss(player);
            }
        }
    }

    @Override
    public void onTurn(GameTurn event) {
        ClassicGamePlayer player = event.getPlayer(ClassicGamePlayer.class);
        event.sendToPlayer(DACMessage.GamePlayerTurn2);
        event.sendToOthers(DACMessage.GamePlayerTurn);
        if (player.mustConfirmate()) {
            event.sendToPlayer(DACMessage.GameMustConfirmate2);
            event.sendToOthers(DACMessage.GameMustConfirmate);
        }
    }

    @Override
    public void onSuccess(GameJumpSuccess event) {
        ClassicGamePlayer player = event.getPlayer(ClassicGamePlayer.class);

        if (event.isADAC()) {
            event.setColumnPattern(new GlassyColumn(player.getColor()));
            if (player.mustConfirmate()) {
                event.sendToPlayer(DACMessage.GameDACConfirmation3);
                event.sendToOthers(DACMessage.GameDACConfirmation);
                event.send(DACMessage.GameDACConfirmation2);
            } else {
                player.winLive();
                event.sendToPlayer(DACMessage.GameDAC2);
                event.sendToOthers(DACMessage.GameDAC);
                event.sendToPlayer(DACMessage.GameLivesAfterDAC2, player.getLives());
                event.sendToOthers(DACMessage.GameLivesAfterDAC, player.getLives());
            }
        } else { 
            event.setColumnPattern(new UniformColumn(player.getColor()));
            if (player.mustConfirmate()) {
                event.sendToPlayer(DACMessage.GameConfirmation2);
                event.sendToOthers(DACMessage.GameConfirmation);
            } else {
                event.sendToPlayer(DACMessage.GameJumpSuccess2);
                event.sendToOthers(DACMessage.GameJumpSuccess);
            }
        }
    }

    @Override
    public void onFail(GameJumpFail event) {
        event.sendToPlayer(DACMessage.GameJumpFail2);
        event.sendToOthers(DACMessage.GameJumpFail);
        event.setMustTeleport(true);
        ClassicGamePlayer player = event.getPlayer(ClassicGamePlayer.class);

        if (player.mustConfirmate()) {
            event.sendToPlayer(DACMessage.GameConfirmationFail2);
            event.sendToOthers(DACMessage.GameConfirmationFail);
            for (ClassicGamePlayer gamePlayer : event.getPlayers(ClassicGamePlayer.class)) {
                if (gamePlayer.isDead()) {
                    gamePlayer.resetLives();
                }
            }
            player.setMustConfirmate(false);
        } else {
            if (player.looseLive()) {
                player.setDeathPosition(event.getRealDeathPos());
                ClassicGamePlayer lastPlayer = lookForLastPlayer(event);
                if (lastPlayer != null) {
                    lastPlayer.setMustConfirmate(true);
                }
            } else {
                event.sendToPlayer(DACMessage.GameLivesAfterFail2, player.getLives());
                event.sendToOthers(DACMessage.GameLivesAfterFail, player.getLives());
            }
        }
    }

    private ClassicGamePlayer lookForLastPlayer(GameEvent event) {
        int count = 0;
        ClassicGamePlayer lastPlayer = null;
        for (ClassicGamePlayer gamePlayer : event.getPlayers(ClassicGamePlayer.class)) {
            if (!gamePlayer.isDead()) {
                count++;
                lastPlayer = gamePlayer;
            }
        }
        return (count == 1) ? lastPlayer : null;
    }

    @Override
    public void onPoolFilled(GamePoolFilled event) {
        ClassicGamePlayer player = event.getPlayer(ClassicGamePlayer.class);
        List<ClassicGamePlayer> playersLeft = getPlayersLeftForSuddenDeath(event.getGame(), player);
        if (playersLeft.size() > 1) {
            switchToSuddenDeath(event.getGame(), playersLeft);
        } else {
            event.setWinner(playersLeft.get(0));
        }
    }

    private List<ClassicGamePlayer> getPlayersLeftForSuddenDeath(Game game, ClassicGamePlayer player) {
        int maxLives = 0;
        List<ClassicGamePlayer> playersLeft = new ArrayList<ClassicGamePlayer>();
        List<StagePlayer> players = game.getPlayers();
        int firstIndex = player.getIndex() + 1;
        int size = players.size();
        for (int i = 0; i < size; i++) {
            ClassicGamePlayer playerLeft = (ClassicGamePlayer) players.get((firstIndex + i) % size);
            int lives = playerLeft.getLives();
            if (lives > maxLives) {
                maxLives = lives;
                playersLeft = new ArrayList<ClassicGamePlayer>();
                playersLeft.add(playerLeft);
            } else if (lives == maxLives) {
                playersLeft.add(playerLeft);
            }
        }
        return playersLeft;
    }

    private void switchToSuddenDeath(Game game, List<ClassicGamePlayer> players) {
        suddenDeath = true;
        GameMode mode = new SuddenDeathGameMode();
        new TurnBasedGame(game, mode, new SuddenDeathGameHandler(), game.getOptions());
    }

    @Override
    public void onFinish(GameFinish event) {
        if (event instanceof GameWin) {
            event.send(DACMessage.GameFinished);
            int i = 1;
            for (StagePlayer player : ((GameWin) event).getRanking()) {
                if (i==1) {
                    event.send(DACMessage.GameWinner, player.getDisplayName());
                } else {
                    event.send(DACMessage.GameRank, i, player.getDisplayName());
                }
                i++;
            }
        } else if (suddenDeath) {
            event.send(DACMessage.GameSuddenDeath);
        } else {
            event.send(DACMessage.GameStopped);
        }
    }

}
