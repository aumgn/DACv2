package fr.aumgn.dac2.game;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Directions;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.playerref.PlayerRef;
import fr.aumgn.dac2.config.Color;
import fr.aumgn.dac2.game.start.PlayerStartData;
import fr.aumgn.dac2.shape.column.ColumnPattern;
import fr.aumgn.dac2.shape.column.UniformPattern;

/**
 * Stores common data associated to player and
 * implements common behaviors.
 */
public class GamePlayer {

    public final PlayerRef playerId;
    private final Color color;
    private final UUID worldId;
    private final Vector pos;
    private final Direction dir;

    private int index;

    public GamePlayer(PlayerRef playerId, PlayerStartData joinData) {
        this.playerId = playerId;
        this.color = joinData.getColor();
        this.worldId = joinData.getWorldId();
        this.pos = joinData.getPosition();
        this.dir = joinData.getDirection();
        this.index = -1;
    }

    public String getDisplayName() {
        String name;
        Player player = playerId.getPlayer();
        if (player == null) {
            name = ChatColor.ITALIC + playerId.getName();
        } else {
            name = player.getDisplayName();
        }

        return color.chat + name + ChatColor.RESET;
    }

    public Color getColor() {
        return color;
    }

    public ColumnPattern getColumnPattern() {
        return new UniformPattern(color);
    }

    public void sendMessage(String message) {
        Player player = playerId.getPlayer();
        if (player != null) {
            player.sendMessage(message);
        }
    }

    public boolean isOnline() {
        return playerId.isOnline();
    }

    public void teleport(World world, Vector pos) {
        Player player = playerId.getPlayer();
        if (player == null) {
            return;
        }
;
        teleport(world, pos, Directions.fromPlayer(player));
    }

    public void teleport(World world, Vector pos, Direction dir) {
        teleport(pos.toLocation(world, dir));
    }

    public void teleport(Location location) {
        Player player = playerId.getPlayer();
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
        teleport(pos.toLocation(Bukkit.getWorld(worldId), dir));
    }

    public Runnable delayedTpToStart() {
        return delayedTeleport(pos.toLocation(Bukkit.getWorld(worldId), dir));
    }

    public int getIndex() {
        return index;
    }

    void setIndex(int index) {
        this.index = index;
    }
}
