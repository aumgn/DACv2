package fr.aumgn.dac.plugin.command;

import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.CommandArgs;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.exception.DACException;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.joinstage.JoinStage;
import fr.aumgn.dac.api.joinstage.SimpleJoinStage;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.stage.StageQuitReason;
import fr.aumgn.dac.api.util.PlayerTeleporter;

@NestedCommands(name = "dac")
public class PlayerCommands extends DACCommands {

    @Command(name = "join", max = -1)
    public void join(Player player, CommandArgs args) {
        Stage stage = DAC.getStageManager().get(player);
        if (stage != null) {
            throw new DACException(DACMessage.CmdJoinAlreadyPlaying);
        }

        Arena arena = DAC.getArenas().get(player);
        if (arena == null) {
            throw new DACException(DACMessage.CmdJoinNotInStart);
        }

        stage = DAC.getStageManager().get(arena);
        if (stage != null && !(stage instanceof JoinStage)) {
            throw new DACException(DACMessage.CmdJoinInGame);
        }

        JoinStage joinStage;
        if (stage == null) {
            joinStage = new SimpleJoinStage(arena);
            DAC.getStageManager().register(joinStage);
        } else {
            joinStage = (JoinStage) stage;
        }

        if (joinStage.isMaxReached()) {
            throw new DACException(DACMessage.CmdJoinMaxReached.getContent(DAC.getConfig().getMaxPlayers()));
        }

        joinStage.addPlayer(player, args.asList());
    }

    @Command(name = "quit")
    public void quit(Player player, CommandArgs args) {
        StagePlayer stagePlayer = DAC.getPlayerManager().get(player);

        if (stagePlayer == null || stagePlayer.getStage() == null) {
            throw new DACException(DACMessage.CmdQuitNotInGame);
        }
        stagePlayer.getStage().removePlayer(stagePlayer, StageQuitReason.Command);
    }

    @Command(name = "goto", min = 1, max = 1)
    public void goto_(Player player, CommandArgs args) {
        StagePlayer dacPlayer = DAC.getPlayerManager().get(player);
        if (dacPlayer == null || !(dacPlayer.getStage() instanceof Game)) {
            throw new DACException(DACMessage.CmdGotoNotInGame);
        }

        PlayerTeleporter teleporter = new PlayerTeleporter(dacPlayer);
        if (args.get(0).equalsIgnoreCase("diving")) {
            teleporter.toDiving();
        } else if (args.get(0).equalsIgnoreCase("start")) {
            teleporter.toStart();
        }
        success(player, DACMessage.CmdGotoSuccess);
    }
}
