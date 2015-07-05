package fr.aumgn.dac2.stage;

import com.google.common.collect.Sets;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

import static fr.aumgn.dac2.utils.DACUtil.onlinePlayersIterable;

public class Spectators {

    private final Set<UUID> set;

    public Spectators() {
        this.set = Sets.newHashSet();
    }

    public Spectators(Spectators other) {
        this();
        set.addAll(other.set);
    }

    public boolean contains(UUID player) {
        return set.contains(player);
    }

    public boolean contains(OfflinePlayer player) {
        return set.contains(player.getUniqueId());
    }

    public boolean add(UUID player) {
        return set.add(player);
    }

    public boolean add(OfflinePlayer player) {
        return set.add(player.getUniqueId());
    }

    public boolean remove(UUID player) {
        return set.remove(player);
    }

    public boolean remove(OfflinePlayer player) {
        return set.remove(player.getUniqueId());
    }

    /**
     * Sends a message to the spectators by prefixing
     * the message with the arena's name as specified in the config.
     */
    public void send(DAC dac, Arena arena, String message) {
        String spectatorMessage = dac.getConfig().getSpectatorsMsg()
                .format(new String[] { arena.getName(), message });
        for (Player spectator : onlinePlayersIterable(set)) {
            spectator.sendMessage(spectatorMessage);
        }
    }
}
