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
import fr.aumgn.dac.api.game.mode.GameHandler;
import fr.aumgn.dac.api.stage.SimpleStage;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;

public abstract class SimpleGame extends SimpleStage implements Game {

    protected GameMode mode;
    protected GameOptions options;
    protected GameHandler gameHandler;
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

    public SimpleGame(GameMode gameMode, Stage stage, GameOptions options) {
        this(gameMode, stage, shuffle(stage.getPlayers()), options);
    }

    public SimpleGame(GameMode gameMode, Stage stage, List<? extends StagePlayer> playersList, GameOptions options) {
        super(stage.getArena());
        stage.stop();
        this.mode = gameMode;
        this.options = options;
        options.parsePropulsion();
        players = new ArrayList<StagePlayer>(playersList.size());
        int i = 0;
        for (StagePlayer player : playersList) {
            players.add(gameMode.createPlayer(this, player, i + 1));
            i++;
        }
        gameHandler = gameMode.createHandler();
        spectators = new HashSet<Player>();
    }

    @Override
    public void send(Object msg) {
        super.send(msg);
        for (Player spectator : spectators) {
            spectator.sendMessage(ChatColor.BLUE + "[" + arena.getName() + "] " + msg.toString());
        }
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
