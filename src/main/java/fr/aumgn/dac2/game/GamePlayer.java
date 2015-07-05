package fr.aumgn.dac2.game;

import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Directions;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.dac2.shape.column.ColumnPattern;
import fr.aumgn.dac2.shape.column.UniformPattern;
import fr.aumgn.dac2.stage.SimpleStagePlayer;
import fr.aumgn.dac2.stage.StagePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Stores common data associated to player and
 * implements common behaviors.
 */
public class GamePlayer extends SimpleStagePlayer {

    private int index;

    public GamePlayer(StagePlayer player, int index) {
        super(player);
        this.index = index;
    }

    public ColumnPattern getColumnPattern() {
        return new UniformPattern(getColor());
    }

    private Player player() {
        return Bukkit.getPlayer(getPlayerID());
    }

    public void sendMessage(String message) {
        Player player = player();
        if (player != null) {
            player.sendMessage(message);
        }
    }

    public boolean isOnline() {
        return player() != null;
    }

    public void teleport(World world, Vector pos) {
        Player player = player();
        if (player == null) {
            return;
        }

        teleport(world, pos, Directions.fromPlayer(player));
    }

    public void teleport(World world, Vector pos, Direction dir) {
        teleport(pos.toLocation(world, dir));
    }

    public void teleport(Location location) {
        Player player = player();
        if (player != null) {
            player.setFallDistance(0f);
            player.teleport(location);
        }
    }

    public Runnable delayedTeleport(final Location location) {
        return new Runnable() {
            @Override
            public void run() {
                teleport(location);
            }
        };
    }

    public void tpToStart() {
        teleport(getStartPosition().toLocation());
    }

    public Runnable delayedTpToStart() {
        return delayedTeleport(getStartPosition().toLocation());
    }

    public int getIndex() {
        return index;
    }

    void updateIndex(int index) {
        this.index = index;
    }

    public interface Factory<T extends GamePlayer> {

        Class<T> getSubclass();

        T create(StagePlayer player, int index);
    }
}
