package fr.aumgn.dac.plugin.mode.classic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.column.GlassyColumn;
import fr.aumgn.dac.api.area.column.UniformColumn;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.arena.Pool;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.event.game.DACGameDACEvent;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.GameOptions;
import fr.aumgn.dac.api.game.SimpleGame;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.game.mode.SimpleGameModeHandler;
import fr.aumgn.dac.api.util.DACUtil;
import fr.aumgn.dac.api.util.RIPSign;
import fr.aumgn.dac.plugin.mode.suddendeath.SuddenDeathGameMode;
import fr.aumgn.dac.plugin.mode.suddendeath.SuddenDeathGamePlayer;

public class ClassicGameModeHandler extends SimpleGameModeHandler<ClassicGamePlayer> {

    private Game<ClassicGamePlayer> game;
    private Arena arena;
    private Stack<String> lostOrder;

    public ClassicGameModeHandler(Game<ClassicGamePlayer> game) {
        this.game = game;
        this.arena = game.getArena();
        this.lostOrder = new Stack<String>();
    }

    private int parseLivesOption() {
        GameOptions options = game.getOptions();
        String livesOption = options.get("lives", "0");
        try {
            return Integer.parseInt(livesOption);
        } catch (NumberFormatException exc) {
            return 0;
        }
    }

    @Override
    public void onStart() {
        if (DAC.getConfig().getResetOnStart()) {
            arena.getPool().reset();
        }
        int lives = parseLivesOption();
        for (ClassicGamePlayer player : game.getPlayers()) {
            player.setLives(lives);
        }
        game.send(DACMessage.GameStart);
        game.send(DACMessage.GamePlayers);
        int i = 1;
        for (ClassicGamePlayer player : game.getPlayers()) {
            game.send(DACMessage.GamePlayerList.format(i, player.getDisplayName()));
            i++;
        }
        game.send(DACMessage.GameEnjoy);
    }

    @Override
    public void onNewTurn() {
        Pool pool = arena.getPool();
        for (ClassicGamePlayer player : game.getPlayers()) {
            if (player.isDead()) {
                lostOrder.add(player.getDisplayName());
                RIPSign sign = new RIPSign(pool, player.getDeathPosition()); 
                sign.rip(player.getDisplayName());
                game.onLoose(player);
            }
        }
    }

    @Override
    public void onTurn(ClassicGamePlayer player) {
        player.send(DACMessage.GamePlayerTurn2.getValue());
        player.sendToOthers(DACMessage.GamePlayerTurn.format(player.getDisplayName()));
        player.tpToDiving();
    }

    @Override
    public void onSuccess(ClassicGamePlayer player) {
        Location loc = player.getPlayer().getLocation();
        int x = loc.getBlockX();
        int z = loc.getBlockZ();

        player.tpAfterJump();

        Pool pool = arena.getPool();
        boolean dac = pool.isADACPattern(x, z);

        if (dac) {
            DAC.callEvent(new DACGameDACEvent(game, player));
            pool.getColumn(x, z).set(new GlassyColumn(player.getColor()));
        } else {
            pool.getColumn(x, z).set(new UniformColumn(player.getColor()));
        }

        if (player.mustConfirmate()) {
            if (dac) {
                player.send(DACMessage.GameDACConfirmation3);
                player.sendToOthers(DACMessage.GameDACConfirmation.format(player.getDisplayName()));
                game.send(DACMessage.GameDACConfirmation2);
            } else {
                player.send(DACMessage.GameConfirmation2);
                player.sendToOthers(DACMessage.GameConfirmation.format(player.getDisplayName()));
            }
            onPlayerWin(player);
        } else {
            if (dac) {
                player.winLive();
                player.send(DACMessage.GameDAC2);
                player.sendToOthers(DACMessage.GameDAC.format(player.getDisplayName()));
                player.send(DACMessage.GameLivesAfterDAC2.format(player.getLives()));
                player.sendToOthers(DACMessage.GameLivesAfterDAC.format(player.getLives()));
            } else {
                player.send(DACMessage.GameJumpSuccess2);
                player.sendToOthers(DACMessage.GameJumpSuccess.format(player.getDisplayName()));
            }

            if (pool.isFull()) {
                List<ClassicGamePlayer> playersLeft = getPlayersLeftForSuddenDeath(player);
                if (playersLeft.size() > 1) {
                    switchToSuddenDeath(playersLeft);
                } else {
                    onPlayerWin(playersLeft.get(0));
                }
            } else {
                game.nextTurn();
            }
        }
    }

    private List<ClassicGamePlayer> getPlayersLeftForSuddenDeath(ClassicGamePlayer player) {
        int max = 0;
        List<ClassicGamePlayer> playersLeft = new ArrayList<ClassicGamePlayer>();
        int i = player.getIndex();
        List<ClassicGamePlayer> players = game.getPlayers();
        for (int j = 0; j < players.size(); j++) {
            ClassicGamePlayer playerLeft = players.get((i + j) % players.size());
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

    private void switchToSuddenDeath(List<ClassicGamePlayer> players) {
        GameMode<SuddenDeathGamePlayer> mode = new SuddenDeathGameMode();
        new SimpleGame<SuddenDeathGamePlayer>(mode, game, players, game.getOptions());
    }

    @Override
    public void onFail(ClassicGamePlayer player) {
        player.send(DACMessage.GameJumpFail2);
        player.sendToOthers(DACMessage.GameJumpFail.format(player.getDisplayName()));

        player.tpAfterFail();

        if (player.mustConfirmate()) {
            player.send(DACMessage.GameConfirmationFail2);
            player.sendToOthers(DACMessage.GameConfirmationFail);
            for (ClassicGamePlayer gamePlayer : game.getPlayers()) {
                gamePlayer.resetLives();
            }
            player.setMustConfirmate(false);
            game.nextTurn();
        } else {
            if (player.looseLive()) {
                Vector vec = DACUtil.getDamageBlockVector(player.getPlayer().getLocation());
                player.setDeathPosition(vec);
                onPlayerLoss(player, false);
            } else {
                player.send(DACMessage.GameLivesAfterFail2.format(player.getLives()));
                player.sendToOthers(DACMessage.GameLivesAfterFail.format(player.getLives()));
                game.nextTurn();
            }
        }
    }

    @Override
    public void onQuit(ClassicGamePlayer player) {
        player.looseAllLives();
        onPlayerLoss(player, true);
    }

    private ClassicGamePlayer getLastPlayer() {
        int i = 0;
        ClassicGamePlayer playerLeft = null;
        for (ClassicGamePlayer player : game.getPlayers()) {
            if (!player.isDead()) {
                playerLeft = player;
                i++;
            }
        }
        return (i == 1) ? playerLeft : null;
    }

    public void onPlayerLoss(ClassicGamePlayer player, boolean force) {
        ClassicGamePlayer lastPlayer = getLastPlayer();  
        if (lastPlayer != null) {
            if (!force && lastPlayer.getIndex() > player.getIndex() && lastPlayer.getLives() == 0) {
                lastPlayer.setMustConfirmate(true);
                lastPlayer.send(DACMessage.GameMustConfirmate2);
                lastPlayer.sendToOthers(DACMessage.GameMustConfirmate.format(lastPlayer.getDisplayName()));
                game.nextTurn();
            } else {
                onPlayerWin(lastPlayer);
            }
        } else {
            if (game.isPlayerTurn(player)) {
                game.nextTurn();
            }
        }
    }

    public void onPlayerWin(ClassicGamePlayer player) {
        Pool pool = arena.getPool();

        for (ClassicGamePlayer gamePlayer : game.getPlayers()) {
            if (gamePlayer.isDead()) {
                RIPSign sign = new RIPSign(pool, gamePlayer.getDeathPosition()); 
                sign.rip(gamePlayer.getDisplayName());
                lostOrder.add(gamePlayer.getDisplayName());
            }
        }

        game.onWin(player);

        game.send(DACMessage.GameFinished);
        game.send(DACMessage.GameWinner.format(player.getDisplayName()));
        int i = 2;
        if (!lostOrder.empty()) {
            for (String name = lostOrder.pop(); !lostOrder.empty(); name = lostOrder.pop()) {
                game.send(DACMessage.GameRank.format(i, name));
                i++;
            }
        }

    }

}
