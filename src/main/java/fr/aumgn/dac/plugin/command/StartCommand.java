package fr.aumgn.dac.plugin.command;

import java.util.Arrays;

import org.bukkit.entity.Player;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.GameOptions;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.joinstage.JoinStage;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class StartCommand extends PlayerCommandExecutor {

    @Override
    public boolean checkUsage(String[] args) {
        return true;
    }

    @Override
    public void onPlayerCommand(Context context, String[] args) {
        Player player = context.getPlayer();
        Stage<?> stage = DAC.getStageManager().get(player);
        if (!(stage instanceof JoinStage)) {
            error(DACMessage.CmdStartNotInGame);
        }
        JoinStage<?> joinStage = (JoinStage<?>) stage;

        GameMode<?> mode;
        if (args.length > 0) {
            mode = DAC.getModes().get(args[0]);
            if (mode == null) {
                error(DACMessage.CmdStartUnknownMode);
            } else if (!joinStage.getArena().hasMode(args[0])) {
                error(DACMessage.CmdStartUnavailableMode);
            }
        } else {
            mode = DAC.getModes().get("classic");
        }

        GameOptions options = joinStage.getArena().getOptions();
        if (args.length > 1) {
            String[] commandOptions = Arrays.copyOfRange(args, 1, args.length);
            options = options.merge(GameOptions.parse(commandOptions));
        }

        if (!joinStage.isMinReached(mode)) {
            error(DACMessage.CmdStartMinNotReached);
        }

        mode.createGame(joinStage, options);
    }

}
