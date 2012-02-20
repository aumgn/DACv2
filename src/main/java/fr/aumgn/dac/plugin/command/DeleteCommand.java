package fr.aumgn.dac.plugin.command;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandContext;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class DeleteCommand extends PlayerCommandExecutor {

    @Override
    public boolean checkUsage(String[] args) {
        return args.length == 1;
    }

    @Override
    public void onPlayerCommand(PlayerCommandContext context, String[] args) {
        Arena arena = DAC.getArenas().get(args[0]);
        if (arena == null) {
            error(DACMessage.CmdDeleteUnknown);
        }
        if (DAC.getStageManager().hasStage(arena)) {
            error(DACMessage.CmdDeleteInGame);
        }
        DAC.getArenas().removeArena(arena);
        context.success(DACMessage.CmdDeleteSuccess);
    }

}
