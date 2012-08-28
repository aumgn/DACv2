package fr.aumgn.dac2.commands;

import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.arena.Diving;

@NestedCommands({"dac2", "set"})
public class SetupCommands extends DACCommands {

    public SetupCommands(DAC dac) {
        super(dac);
    }

    @Command(name = "diving", min = 1, max = 1)
    public void diving(Player sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).value();
        arena.setDiving(new Diving(sender.getLocation()));
        dac.getArenas().saveArena(dac, arena);
        sender.sendMessage(msg("set.diving.success"));
    }
}
