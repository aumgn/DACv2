package fr.aumgn.dac2.stage;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.playerref.PlayerRef;
import fr.aumgn.bukkitutils.playerref.set.PlayersRefHashSet;
import fr.aumgn.bukkitutils.playerref.set.PlayersRefSet;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;

public class Spectators {

    private final PlayersRefSet set;

    public Spectators() {
        this.set = new PlayersRefHashSet();
    }

    public Spectators(Spectators other) {
        this();
        set.addAll(other.set);
    }

    public boolean contains(PlayerRef player) {
        return set.contains(player);
    }

    public boolean contains(OfflinePlayer player) {
        return set.contains(player);
    }

    public boolean add(PlayerRef player) {
        return set.add(player);
    }

    public boolean add(OfflinePlayer player) {
        return set.add(player);
    }

    public boolean remove(PlayerRef player) {
        return set.remove(player);
    }

    public boolean remove(OfflinePlayer player) {
        return set.remove(player);
    }

    /**
     * Sends a message to the spectators by prefixing
     * the message with the arena's name as specified in the config.
     */
    public void send(DAC dac, Arena arena, String message) {
        String spectatorMessage = dac.getConfig().getSpectatorsMsg()
                .format(new String[] { arena.getName(), message });
        for (Player spectator : set.players()) {
            spectator.sendMessage(spectatorMessage);
        }
    }
}
