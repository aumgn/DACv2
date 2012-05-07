package fr.aumgn.dac.plugin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.CommandArgs;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.exception.DACException;
import fr.aumgn.dac.api.game.GameOptions;
import fr.aumgn.dac.api.game.TurnBasedGame;
import fr.aumgn.dac.api.game.mode.DACGameMode;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.joinstage.JoinStage;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.stage.StagePlayerManager;
import fr.aumgn.dac.api.stage.StageQuitReason;
import fr.aumgn.dac.api.stage.StageStopReason;

@NestedCommands(name = "dac")
public class GameCommands extends DACCommands {

    @Command(name = "start", max = -1)
    public void start(Player player, CommandArgs args) {
        Stage stage = DAC.getStageManager().get(player);
        if (!(stage instanceof JoinStage)) {
            throw new DACException(DACMessage.CmdStartNotInGame);
        }

        JoinStage joinStage = (JoinStage) stage;
        GameMode mode;
        if (args.length() > 0) {
            mode = DAC.getModes().getNewInstance(args.get(0));
            if (mode == null) {
                throw new DACException(DACMessage.CmdStartUnknownMode);
            }
            String modeName = mode.getClass().getAnnotation(DACGameMode.class).name();
            if (!joinStage.getArena().allowMode(modeName)) {
                throw new DACException(DACMessage.CmdStartUnavailableMode);
            }
        } else {
            mode = DAC.getModes().getNewInstance("classic");
        }

        GameOptions options;
        if (args.length() > 1) {
            options = GameOptions.parse(args.asList(1));
        } else {
            options = new GameOptions();
        }
        options = stage.getArena().mergeOptions(options);

        if (!joinStage.isMinReached(mode)) {
            throw new DACException(DACMessage.CmdStartMinNotReached);
        }

        new TurnBasedGame(joinStage, mode, options);
    }

    @Command(name = "stop")
    public void stop(Player player, CommandArgs args) {
        Stage stage = DAC.getStageManager().get(player);
        if (stage == null) {
            throw new DACException(DACMessage.CmdStopNoGameToStop);
        }

        stage.stop(StageStopReason.Forced);
    }

    @Command(name = "kick")
    public void kick(CommandSender sender, CommandArgs args) {
        int count = 0;
        StagePlayerManager playerManager = DAC.getPlayerManager();
        String pattern = args.get(0);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().startsWith(pattern)) {
                StagePlayer stagePlayer = playerManager.get(player);
                if (stagePlayer == null) {
                    continue;
                }

                stagePlayer.getStage().removePlayer(stagePlayer, StageQuitReason.Forced);
                count++;
            }
        }

        if (count == 0) {
            throw new DACException(DACMessage.CmdKickNoPlayerFound);
        } else {
            success(sender, DACMessage.CmdKickSuccess.getContent(count));
        }
    }

}
