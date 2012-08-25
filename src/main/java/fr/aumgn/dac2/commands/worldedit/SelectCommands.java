package fr.aumgn.dac2.commands.worldedit;

import static fr.aumgn.dac2.shape.WEShapeUtils.getSelection;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.shape.Shape;

@NestedCommands({ "dac2", "select" })
public class SelectCommands extends WorldEditCommands {

    public SelectCommands(DAC dac) {
        super(dac);
    }

    @Command(name = "pool", min = 1, max = 1)
    public void pool(Player sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).value();

        Shape shape = arena.getPool().getShape();
        Selection sel = getSelection(dac, arena.getWorld(), shape);
        getWorldEdit().setSelection(sender, sel);
        sender.sendMessage(msg("select.pool.success"));
    }

    @Command(name = "start", min = 1, max = 1)
    public void start(Player sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).value();

        Shape shape = arena.getStartRegion().getShape();
        Selection sel = getSelection(dac, arena.getWorld(), shape);
        getWorldEdit().setSelection(sender, sel);
        sender.sendMessage(msg("select.start.success"));
    }

    @Command(name = "surrounding", min = 1, max = 1)
    public void surrounding(Player sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).value();

        Shape shape = arena.getSurroundingRegion().getShape();
        Selection sel = getSelection(dac, arena.getWorld(), shape);
        getWorldEdit().setSelection(sender, sel);
        sender.sendMessage(msg("select.surrounding.success"));
    }
}
