package fr.aumgn.dac.plugin.command;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.Area;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.exception.InvalidRegionType;
import fr.aumgn.utils.command.PlayerCommandContext;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class SelectCommand extends PlayerCommandExecutor {

    @Override
    public boolean checkUsage(String[] args) {
        return args.length == 2 && (
                args[1].equalsIgnoreCase("pool") ||
                args[1].equalsIgnoreCase("start"));
    }

    @Override
    public void onPlayerCommand(PlayerCommandContext context, String[] args) {
        Arena arena = DAC.getArenas().get(args[0]);
        if (arena == null) {
            error(DACMessage.CmdSelectUnknown);
        }

        Area area = null;
        DACMessage message = DACMessage.CmdSelectError;
        if (args[1].equalsIgnoreCase("pool")) {
            area = arena.getPool();
            message = DACMessage.CmdSelectSuccessPool;
        } else if (args[1].equalsIgnoreCase("start")) {
            area = arena.getStartArea();
            message = DACMessage.CmdSelectSuccessStart;
        }

        try {
            DAC.getWorldEdit().setSelection(context.getPlayer(), area.getSelection());
            context.success(message);
        } catch (InvalidRegionType exc) {
            context.error(DACMessage.CmdSelectError);
            error(exc.getMessage());
        }
    }

}
