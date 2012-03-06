package fr.aumgn.dac.api.game.event;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.ColumnPattern;
import fr.aumgn.dac.api.area.column.UniformColumn;
import fr.aumgn.dac.api.stage.StagePlayer;

public class GameJumpSuccess extends GameJumpEvent {

    private ColumnPattern pattern;

    public GameJumpSuccess(StagePlayer player, int x, int z) {
        super(player, x, z, DAC.getConfig().getTpAfterJump());
        this.pattern = new UniformColumn(player.getColor()); 
    }

    public boolean isADAC() {
        return getArena().getPool().isADACPattern(getX(), getZ());
    }

    public ColumnPattern getColumnPattern() {
        return pattern;
    }

    public void setColumnPattern(ColumnPattern pattern) {
        this.pattern = pattern;
    }

}
