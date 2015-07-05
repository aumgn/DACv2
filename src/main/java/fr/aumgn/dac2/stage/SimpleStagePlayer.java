package fr.aumgn.dac2.stage;

import fr.aumgn.bukkitutils.geom.Position;
import fr.aumgn.bukkitutils.playerref.PlayerRef;
import fr.aumgn.dac2.config.Color;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class SimpleStagePlayer implements StagePlayer {

    private final PlayerRef ref;
    private final Color color;
    private final Position position;

    protected SimpleStagePlayer(Color color, Player player) {
        this.ref = PlayerRef.get(player);
        this.color = color;
        this.position = new Position(player.getLocation());
    }

    protected SimpleStagePlayer(StagePlayer player) {
        this.ref = player.getRef();
        this.color = player.getColor();
        this.position = player.getStartPosition();
    }

    @Override
    public PlayerRef getRef() {
        return ref;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Position getStartPosition() {
        return position;
    }

    @Override
    public String getDisplayName() {
        String rawName = ref.getDisplayName();
        rawName = ChatColor.stripColor(rawName);
        return color.chat + rawName;
    }
}
