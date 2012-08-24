package fr.aumgn.dac2.game;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import fr.aumgn.bukkitutils.util.Util;

public class GameParty<T extends GamePlayer> {

    // Come on..
    private final Class<T> clazz;
    private final Game game;
    private T[] players;
    private int turn;

    public GameParty(Game game, Class<T> clazz, List<T> list) {
        this.clazz = (Class<T>) clazz;
        this.game = game;
        players = newArray(list.size());

        LinkedList<T> roulette = new LinkedList<T>(list);
        Random rand = Util.getRandom();
        for (int i = 0; i< players.length; i++) {
            int j = rand.nextInt(roulette.size());
            T player = roulette.remove(j);
            players[i] = player;
            player.setIndex(i);
        }

        turn = -1;
    }

    @SuppressWarnings("unchecked")
    private T[] newArray(int size) {
        return (T[]) Array.newInstance(clazz, size);
    }

    public boolean isTurn(T player) {
        return player.getIndex() == turn;
    }

    public T getCurrent() {
        return players[turn];
    }

    public T[] iterable() {
        return players;
    }

    public int size() {
        return players.length;
    }

    public T nextTurn() {
        incrementTurn();
        return players[turn];
    }

    private void incrementTurn() {
        turn++;
        if (turn >= players.length) {
            turn = 0;
            game.onNewTurn();
        }
    }

    public void removePlayer(T player) {
        int index = player.getIndex();

        T[] newPlayers = newArray(players.length - 1);
        System.arraycopy(players, 0, newPlayers, 0, index);
        System.arraycopy(players, index + 1, newPlayers,
                index, players.length - index - 1);

        for (int i = index + 1; i < players.length; i++) {
            players[i].setIndex(i - 1);
        }

        if (index <= turn) {
            if (turn == 0) {
                turn = players.length - 1;
            } else {
                turn--;
            }
        }

        players = newPlayers;
    }
}
