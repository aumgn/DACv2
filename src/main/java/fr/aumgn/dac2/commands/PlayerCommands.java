package fr.aumgn.dac2.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.config.Color;
import fr.aumgn.dac2.stage.Stage;
import fr.aumgn.dac2.stage.join.JoinStage;

@NestedCommands("dac2")
public class PlayerCommands extends DACCommands {

    public PlayerCommands(DAC dac) {
        super(dac);
    }

    @Command(name = "join", min = 0, max = -1, argsFlags = "a")
    public void join(Player sender, CommandArgs args) {
        if (dac.getStages().get(sender) != null) {
            throw new CommandError(msg("join.alreadyingame"));
        }

        Arena arena = args.get('a', Arena)
                .valueWithPermOr("dac2.stages.join.arena", sender);
        Stage stage = dac.getStages().get(arena);

        if (!(stage instanceof JoinStage)) {
            throw new CommandError(msg("join.notjoinable"));
        }

        List<Color> colors = new ArrayList<Color>();
        for (int i = 1; i < args.length(); i++) {
            colors.add(args.get(i, Color).value());
        }
        ((JoinStage) stage).addPlayer(sender, colors);
    }

    @Command(name = "quit")
    public void quit(Player sender) {
        Stage stage = dac.getStages().get(sender);
        if (stage == null) {
            throw new CommandError(msg("quit.notingame"));
        }

        stage.onQuit(sender);
    }
}
