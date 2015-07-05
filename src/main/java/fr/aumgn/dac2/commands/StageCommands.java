package fr.aumgn.dac2.commands;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.game.Game;
import fr.aumgn.dac2.game.GameFactory;
import fr.aumgn.dac2.game.start.GameQuickStart;
import fr.aumgn.dac2.stage.Stage;
import fr.aumgn.dac2.stage.join.JoinStage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@NestedCommands("dac2")
public class StageCommands extends DACCommands {

    public StageCommands(DAC dac) {
        super(dac);
    }

    @Command(name = "initialize", min = 0, max = 1)
    public void init(CommandSender sender, CommandArgs args) {
        Arena arena = args.get(0, Arena)
                .valueWithPermOr("dac.stages.init.arena", sender);

        JoinStage joinStage = new JoinStage(dac, arena);
        dac.getStages().start(joinStage);

        if (!(sender instanceof Player)) {
            sender.sendMessage(msg("init.success", arena.getName()));
        }
    }

    @Command(name = "stop", min = 0, max = 1, argsFlags = "a")
    public void stop(CommandSender sender, CommandArgs args) {
        Stage stage = args.get(0, Stage)
                .valueWithPermOr("dac2.stage.stop.others", sender);

        dac.getStages().forceStop(stage);
        sender.sendMessage(msg("stop.success", stage.getArena().getName()));
    }

    @Command(name = "start", min = 0, max = 1, argsFlags = "a")
    public void start(CommandSender sender, CommandArgs args) {
        Stage stage = args.get('a', Stage)
                .valueWithPermOr("dac2.stage.start.others", sender);
        String gameMode = args.get(0, "classic");

        if (stage == null) {
            throw new CommandError(msg("start.notajoinstage"));
        }
        else if (!(stage instanceof JoinStage)) {
            throw new CommandError(msg("start.alreadystarted"));
        }

        JoinStage joinStage = (JoinStage) stage;
        GameFactory factory = GameFactory.getByAlias(dac, gameMode);
        if (joinStage.size() < factory.getMinimumPlayers()) {
            throw new CommandError(msg("start.notenoughplayers"));
        }
        Game game = factory.createGame(dac, joinStage);
        dac.getStages().switchTo(game);
    }

    @Command(name = "quickstart", min = 0, max = 1, argsFlags = "a")
    public void quickstart(CommandSender sender, CommandArgs args) {
        Arena arena = args.get('a', Arena)
                .valueWithPermOr("dac.stages.quickstart.arena", sender);

        String gameMode = args.get(0, "classic");
        GameQuickStart quickStart = new GameQuickStart(dac, arena);
        GameFactory factory = GameFactory.getByAlias(dac, gameMode);
        if (quickStart.size() < factory.getMinimumPlayers()) {
            throw new CommandError(msg("quickstart.notenoughplayers"));
        }
        Game game = factory.createGame(dac, quickStart);
        dac.getStages().start(game);
    }

    @Command(name = "kick", min = 1, max = 1)
    public void kick(CommandSender sender, CommandArgs args) {
        Player player = args.getPlayer(0).value();
        Stage stage = dac.getStages().get(player);
        if (stage == null) {
            throw new CommandError(msg("quit.notingame"));
        }

        stage.onQuit(player);
        sender.sendMessage(msg("kick.success", player.getDisplayName(),
                stage.getArena().getName()));
    }
}
