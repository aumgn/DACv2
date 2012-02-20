package fr.aumgn.dac.plugin.command;

import java.util.Arrays;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.fillstrategy.FillStrategy;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.mode.DACGameMode;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.utils.command.PlayerCommandContext;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class FillCommand extends PlayerCommandExecutor {

    @Override
    public boolean checkUsage(String[] args) {
        return args.length > 1;
    }

    @Override
    public void onPlayerCommand(PlayerCommandContext context, String[] args) {
        Arena arena = DAC.getArenas().get(args[0]);

        if (arena == null) {
            error(DACMessage.CmdFillUnknown);
        }

        Stage<?> stage = DAC.getStageManager().get(arena);
        if (stage instanceof Game) {
            if (!context.hasPermission("dac.game.fill")) {
                GameMode<?> mode = ((Game<?>) stage).getMode();
                if (!mode.getClass().getAnnotation(DACGameMode.class).allowFill()) {
                    error(DACMessage.CmdFillInGame);
                }
            }
        }

        fill(context, args, arena);
        context.success(DACMessage.CmdFillSuccess);
    }

    protected void fill(PlayerCommandContext context, String[] args, Arena arena) {
        FillStrategy strategy = DAC.getFillStrategies().get(args[1]);
        if (strategy == null) {
            error(DACMessage.CmdFillUnknownStrategy);
        }

        String[] fillArgs = Arrays.copyOfRange(args, 2, args.length);
        arena.getPool().fillWith(strategy, fillArgs);
    }

}
