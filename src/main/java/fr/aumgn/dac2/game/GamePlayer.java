package fr.aumgn.dac2.game;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.playerid.PlayerId;
import fr.aumgn.dac2.config.Color;
import fr.aumgn.dac2.stage.JoinPlayerData;

public class GamePlayer {

    public final PlayerId playerId;
    public final Color color;
    private final UUID worldId;
    private final Vector pos;
    private final Direction dir;

    private int index;

    public GamePlayer(PlayerId playerId, JoinPlayerData joinData, int index) {
        this.index = index;
        this.playerId = playerId;
        this.color = joinData.color;
        this.worldId = joinData.worldId;
        this.pos = joinData.pos;
        this.dir = joinData.dir;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public void sendMessage(String message) {
        Player player = playerId.getPlayer();
        if (player != null) {
            player.sendMessage(message);
        }
    }

    public boolean isOnline() {
        return playerId.isOnline();
    }

    public void setNoDamageTicks(int i) {
        Player player = playerId.getPlayer();
        if (player != null) {
            player.setNoDamageTicks(i);
        }
    }

    public void teleport(Location location) {
        Player player = playerId.getPlayer();
        if (player != null) {
            player.setFallDistance(0f);
            player.teleport(location);
        }
    }

    public void tpToStart() {
        teleport(pos.toLocation(Bukkit.getWorld(worldId), dir));
    }
}
