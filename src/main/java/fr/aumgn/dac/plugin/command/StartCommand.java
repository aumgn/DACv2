package fr.aumgn.dac.plugin.command;

import java.util.Arrays;

import org.bukkit.entity.Player;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.GameOptions;
import fr.aumgn.dac.api.game.mode.DACGameMode;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.joinstage.JoinStage;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.utils.command.PlayerCommandContext;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class StartCommand extends PlayerCommandExecutor {

    @Override
    public boolean checkUsage(String[] args) {
        return true;
    }

    @Override
    public void onPlayerCommand(PlayerCommandContext context, String[] args) {
        Player player = context.getPlayer();
        Stage stage = DAC.getStageManager().get(player);
        if (!(stage instanceof JoinStage)) {
            error(DACMessage.CmdStartNotInGame);
        }
        JoinStage joinStage = (JoinStage) stage;

        GameMode mode;
        if (args.length > 0) {
            mode = DAC.getModes().get(args[0]);
            if (mode == null) {
                error(DACMessage.CmdStartUnknownMode);
            }
            String modeName = mode.getClass().getAnnotation(DACGameMode.class).name();
            if (!joinStage.getArena().hasMode(modeName)) {
                error(DACMessage.CmdStartUnavailableMode);
            }
        } else {
            mode = DAC.getModes().get("classic");
        }

        GameOptions options;
        if (args.length == 0) {
            options = new GameOptions();
        } else {
            String[] commandOptions = Arrays.copyOfRange(args, 1, args.length);
            options = GameOptions.parse(commandOptions);            
        }
        options = stage.getArena().mergeOptions(options);

        if (!joinStage.isMinReached(mode)) {
            error(DACMessage.CmdStartMinNotReached);
        }

        mode.createGame(joinStage, options);
    }

}
