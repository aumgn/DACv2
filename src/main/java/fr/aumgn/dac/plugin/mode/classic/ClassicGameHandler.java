package fr.aumgn.dac.plugin.mode.classic;

import java.util.ArrayList;
import java.util.List;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.column.GlassyColumn;
import fr.aumgn.dac.api.area.column.UniformColumn;
import fr.aumgn.dac.api.arena.Pool;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.GameOptions;
import fr.aumgn.dac.api.game.TurnBasedGame;
import fr.aumgn.dac.api.game.event.GameEvent;
import fr.aumgn.dac.api.game.event.GameFinish;
import fr.aumgn.dac.api.game.event.GameJumpFail;
import fr.aumgn.dac.api.game.event.GameJumpSuccess;
import fr.aumgn.dac.api.game.event.GameNewTurn;
import fr.aumgn.dac.api.game.event.GameStart;
import fr.aumgn.dac.api.game.event.GameTurn;
import fr.aumgn.dac.api.game.event.GameWin;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.game.mode.SimpleGameHandler;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.util.RIPSign;

public class ClassicGameHandler extends SimpleGameHandler {

	private int parseLivesOption(Game game) {
        GameOptions options = game.getOptions();
        String livesOption = options.get("lives", "0");
        try {
            return Integer.parseInt(livesOption);
        } catch (NumberFormatException exc) {
            return 0;
        }
    }

    private List<ClassicGamePlayer> getPlayersLeftForSuddenDeath(Game game, ClassicGamePlayer player) {
        int max = 0;
        List<ClassicGamePlayer> playersLeft = new ArrayList<ClassicGamePlayer>();
        int i = player.getIndex();
        List<StagePlayer> players = game.getPlayers();
        for (int j = 0; j < players.size(); j++) {
            ClassicGamePlayer playerLeft = (ClassicGamePlayer) players.get((i + j) % players.size());
            int lives = playerLeft.getLives();
            if (lives > max) {
                max = lives;
                playersLeft = new ArrayList<ClassicGamePlayer>();
                playersLeft.add(playerLeft);
            } else if (lives == max) {
                playersLeft.add(playerLeft);
            }
        }
        return playersLeft;
    }

    private void switchToSuddenDeath(Game game, List<ClassicGamePlayer> players) {
        GameMode mode = DAC.getModes().get("sudden-death");
        new TurnBasedGame(mode, game, players, game.getOptions());
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
    public void onStart(GameStart start) {
        int lives = parseLivesOption(start.getGame());
        for (ClassicGamePlayer player : start.getPlayers(ClassicGamePlayer.class)) {
            player.setLives(lives);
        }
        start.send(DACMessage.GameStart);
        start.send(DACMessage.GamePlayers);
        for (ClassicGamePlayer player : start.getPlayers(ClassicGamePlayer.class)) {
        	player.getDisplayName();
            start.send(DACMessage.GamePlayerList, player.getIndex());
        }
        start.send(DACMessage.GameEnjoy);
    }

    @Override
    public void onNewTurn(GameNewTurn newTurn) {
        Pool pool = newTurn.getArena().getPool();
        for (StagePlayer stagePlayer : newTurn.getGame().getPlayers()) {
        	ClassicGamePlayer player = (ClassicGamePlayer) stagePlayer;
            if (player.isDead()) {
                RIPSign sign = new RIPSign(pool, player.getDeathPosition()); 
                sign.rip(player.getDisplayName());
                newTurn.getGame().onLoose(player);
            }
        }
    }

    @Override
    public void onTurn(GameTurn turn) {
    	ClassicGamePlayer player = turn.getPlayer(ClassicGamePlayer.class);
        turn.sendToPlayer(DACMessage.GamePlayerTurn2);
        turn.sendToOthers(DACMessage.GamePlayerTurn);
        if (player.mustConfirmate()) {
        	turn.sendToPlayer(DACMessage.GameMustConfirmate2);
        	turn.sendToOthers(DACMessage.GameMustConfirmate);
        }
    }

    @Override
    public void onSuccess(GameJumpSuccess success) {
    	ClassicGamePlayer player = success.getPlayer(ClassicGamePlayer.class);

    	if (success.isADAC()) {
    		success.setColumnPattern(new GlassyColumn(player.getColor()));
            if (player.mustConfirmate()) {
            	success.send(DACMessage.GameDACConfirmation3);
                success.sendToOthers(DACMessage.GameDACConfirmation);
                success.send(DACMessage.GameDACConfirmation2);
            } else {
                player.winLive();
                success.sendToPlayer(DACMessage.GameDAC2);
                success.sendToOthers(DACMessage.GameDAC);
                success.sendToPlayer(DACMessage.GameLivesAfterDAC2);
                success.sendToOthers(DACMessage.GameLivesAfterDAC);
            }
            success.setMustTeleport(true);
    	} else { 
    		success.setColumnPattern(new UniformColumn(player.getColor()));
    		if (player.mustConfirmate()) {
            	success.send(DACMessage.GameConfirmation2);
                success.sendToOthers(DACMessage.GameConfirmation);
    		} else {
                success.sendToPlayer(DACMessage.GameJumpSuccess2);
                success.sendToOthers(DACMessage.GameJumpSuccess);
    		}
    	}

         if (success.getArena().getPool().isFull()) {
        	 success.setSwitchToNextTurn(false);
        	 List<ClassicGamePlayer> playersLeft = getPlayersLeftForSuddenDeath(success.getGame(), player);
        	 if (playersLeft.size() > 1) {
        		switchToSuddenDeath(success.getGame(), playersLeft);
        	 } else {
        		success.getGame().onWin(playersLeft.get(0));
            }
        }
    }

    public void onFinish(GameFinish finish) {
    	finish.send(DACMessage.GameFinished);
    	if (finish instanceof GameWin) {
    	    int i = 1;
    	    for (StagePlayer player : ((GameWin) finish).getRanking()) {
    	        if (i==1) {
    	            finish.send(DACMessage.GameWinner, player.getDisplayName());
    	        } else {
    	            finish.send(DACMessage.GameRank, i, player.getDisplayName());
    	        }
    	        i++;
    	    }
    	}
    }

    @Override
    public void onFail(GameJumpFail fail) {
        fail.sendToPlayer(DACMessage.GameJumpFail2);
        fail.sendToOthers(DACMessage.GameJumpFail);
        fail.setMustTeleport(true);
        ClassicGamePlayer player = fail.getPlayer(ClassicGamePlayer.class);
        
        if (player.mustConfirmate()) {
            fail.sendToPlayer(DACMessage.GameConfirmationFail2);
            fail.sendToOthers(DACMessage.GameConfirmationFail);
            for (ClassicGamePlayer gamePlayer : fail.getPlayers(ClassicGamePlayer.class)) {
                if (gamePlayer.isDead()) {
                	gamePlayer.resetLives();
                }
            }
            player.setMustConfirmate(false);
        } else {
            if (player.looseLive()) {
                player.setDeathPosition(fail.getRealDeathPos());
                ClassicGamePlayer lastPlayer = lookForLastPlayer(fail);
                if (lastPlayer != null) {
                	lastPlayer.setMustConfirmate(true);
                }
            } else {
                fail.sendToPlayer(DACMessage.GameLivesAfterFail2, player.getLives());
                fail.sendToOthers(DACMessage.GameLivesAfterFail, player.getLives());
            }
        }
    }
    
}
