package fr.aumgn.dac2.utils;

import com.google.common.collect.AbstractIterator;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.UUID;

public final class DACUtil {

    public static final int TICKS_PER_SECONDS = 20;
    public static final double PLAYER_MAX_HEALTH = 20d;

    private DACUtil() {
    }

    public static String playerDisplayName(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        Player player = offlinePlayer.getPlayer();
        return player == null
                ? offlinePlayer.getName()
                : player.getDisplayName();
    }

    public static Iterable<Player> onlinePlayersIterable(final Iterable<UUID> uuids) {
        return new UuidToPlayerIterable(uuids);
    }

    private static class UuidToPlayerIterable implements Iterable<Player> {
        private final Iterable<UUID> uuids;

        public UuidToPlayerIterable(Iterable<UUID> uuids) {
            this.uuids = uuids;
        }

        @Override
        public Iterator<Player> iterator() {
            return new UuidToPlayerIterator(uuids);
        }

    }

    private static class UuidToPlayerIterator extends AbstractIterator<Player> {
        private final Iterator<UUID> uuids;

        public UuidToPlayerIterator(Iterable<UUID> uuids) {
            this.uuids = uuids.iterator();
        }

        @Override
        protected Player computeNext() {
            Player player = null;
            while (uuids.hasNext() && player == null) {
                player = Bukkit.getPlayer(uuids.next());
            }

            return player == null
                    ? this.<Player>endOfData()
                    : player;
        }
    }
}
