package fr.aumgn.dac2.game;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.OfflinePlayer;

import fr.aumgn.bukkitutils.playerref.map.PlayersRefHashMap;
import fr.aumgn.bukkitutils.playerref.map.PlayersRefMap;
import fr.aumgn.bukkitutils.util.Util;
import fr.aumgn.dac2.stage.StagePlayer;

public class GameParty<T extends GamePlayer> {

    // Come on..
    private final Class<T> clazz;
    private final Game game;

    private T[] array;
    private final PlayersRefMap<T> map;

    private int turn;

    public GameParty(Game game, Set<? extends StagePlayer> players,
            GamePlayer.Factory<T> playerFactory) {
        this.clazz = playerFactory.getSubclass();
        this.game = game;
        this.array = newArray(players.size());
        this.map = new PlayersRefHashMap<T>();

        List<StagePlayer> roulette = new LinkedList<StagePlayer>(players);
        Random rand = Util.getRandom();
        for (int i = 0; i< array.length; i++) {
            int j = rand.nextInt(roulette.size());
            T player = playerFactory.create(roulette.remove(j), i);
            array[i] = player;

            map.put(player.getRef(), player);
        }

        turn = -1;
    }

    @SuppressWarnings("unchecked")
    private T[] newArray(int size) {
        return (T[]) Array.newInstance(clazz, size);
    }

    public boolean contains(OfflinePlayer player) {
        return map.containsKey(player);
    }

    public T get(OfflinePlayer player) {
        return map.get(player);
    }

    public boolean isTurn(OfflinePlayer player) {
        T gamePlayer = get(player);
        return gamePlayer != null && gamePlayer.getIndex() == turn;
    }

    public boolean isLastTurn() {
        return turn == array.length - 1;
    }

    public T getCurrent() {
        return array[turn];
    }

    /**
     * Returns the internal array used to store the players.
     * This array is accessible for performance reason
     * (and because Java doesn't allow clean polymorphic management of arrays)
     * and should preferably not be used to modify the array in any way.
     */
    public T[] iterable() {
        return array;
    }

    public int size() {
        return array.length;
    }

    public T nextTurn() {
        incrementTurn();
        return array[turn];
    }

    private void incrementTurn() {
        turn++;
        if (turn >= array.length) {
            turn = 0;
            game.onNewTurn();
        }
    }

    public void remove(T player) {
        int index = player.getIndex();

        T[] newPlayers = newArray(array.length - 1);
        System.arraycopy(array, 0, newPlayers, 0, index);
        System.arraycopy(array, index + 1, newPlayers,
                index, array.length - index - 1);

        for (int i = index + 1; i < array.length; i++) {
            array[i].updateIndex(i - 1);
        }

        if (index <= turn) {
            if (turn == 0) {
                turn = array.length - 2;
            } else {
                turn--;
            }
        }

        array = newPlayers;

        map.remove(player.getRef());
    }

    public List<T> asList() {
        return Arrays.asList(array);
    }
}
