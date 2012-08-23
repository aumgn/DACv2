package fr.aumgn.dac2.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.stage.JoinStage;
import fr.aumgn.dac2.stage.Stage;

@NestedCommands("dac2")
public class StageCommands extends DACCommands {

    public StageCommands(DAC dac) {
        super(dac);
    }

    @Command(name = "initialize", min = 0, max = 1)
    public void init(CommandSender sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).valueOr(sender);

        JoinStage joinStage = new JoinStage(dac, arena);
        dac.getStages().start(joinStage);

        if (!(sender instanceof Player)) {
            sender.sendMessage(msg("init.success", arena.getName()));
        }
    }

    @Command(name = "join", argsFlags = "a", min = 0, max = -1)
    public void join(Player sender, CommandArgs args) {
        if (dac.getStages().get(sender) != null) {
            throw new CommandError(msg("join.alreadyingame"));
        }

        Arena arena = args.get('a', Arena.class)
                .valueWithPermOr("dac2.stages.join.arena", sender);
        Stage stage = dac.getStages().get(arena);

        if (!(stage instanceof JoinStage)) {
            throw new CommandError(msg("join.notjoinable"));
        }

        ((JoinStage) stage).addPlayer(sender, args.asList());
    }

    @Command(name = "stop", min = 0, max = 1)
    public void stop(CommandSender sender, CommandArgs args) {
        Stage stage = args.get(0, Stage.class)
                .valueWithPermOr("dac2.stage.stop.others", sender);

        dac.getStages().stop(stage);
        sender.sendMessage(msg("stop.success", stage.getArena().getName()));
    }
}
