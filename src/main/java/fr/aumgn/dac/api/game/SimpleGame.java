package fr.aumgn.dac.api.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.stage.SimpleStage;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.stage.StageStopReason;

public abstract class SimpleGame extends SimpleStage implements Game {

    protected GameMode mode;
    protected GameOptions options;
    protected List<StagePlayer> players;
    protected Set<Player> spectators;

    protected static List<StagePlayer> shuffle(List<? extends StagePlayer> roulette) {
        List<StagePlayer> list = new ArrayList<StagePlayer>(roulette.size());
        Random rand = DAC.getRand();
        int length = roulette.size();
        for (int i = 0; i < length; i++) {
            int j = rand.nextInt(roulette.size());
            StagePlayer player = roulette.remove(j);
            list.add(player);
        }
        return list;
    }

    public SimpleGame(Stage stage, GameMode gameMode, GameOptions options) {
        this(stage, gameMode, shuffle(stage.getPlayers()), options);
    }

    public SimpleGame(Stage stage, GameMode gameMode, List<? extends StagePlayer> playersList, GameOptions options) {
        super(stage.getArena());
        stage.stop(StageStopReason.ChangeStage);
        this.mode = gameMode;
        this.options = options;
        options.parsePropulsion();
        options.parseLives();
        spectators = new HashSet<Player>();
        players = new ArrayList<StagePlayer>(playersList.size());
        for (StagePlayer player : playersList) {
            players.add(gameMode.createPlayer(this, player));
        }
        DAC.getStageManager().register(this);
    }

    @Override
    public void sendToSpectators(String message) {
        String spectMessage = ChatColor.BLUE + "[" + arena.getName() + "] " + message;
        for (Player spectator : spectators) {
            spectator.sendMessage(spectMessage);
        }
    }

    @Override
    public void send(Object msg) {
        super.send(msg);
        sendToSpectators(msg.toString());
    }

    @Override
    public GameMode getMode() {
        return mode;
    }

    @Override
    public GameOptions getOptions() {
        return options;
    }

    @Override
    public boolean canWatch(Player player) {
        StagePlayer stagePlayer = DAC.getPlayerManager().get(player);
        if (stagePlayer == null) {
            return true;
        }
        if (stagePlayer.getStage() == this) {
            return false;
        }
        return true;
    }

    @Override
    public boolean addSpectator(Player player) {
        return spectators.add(player);
    }

    @Override
    public boolean removeSpectator(Player player) {
        return spectators.remove(player);
    }

}
