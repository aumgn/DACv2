package fr.aumgn.dac.plugin.command;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.utils.command.BasicCommandExecutor;

public class ArenasCommand extends BasicCommandExecutor {

    @Override
    public boolean checkUsage(String[] args) {
        return args.length == 0;
    }

    @Override
    public void onCommand(Context context, String[] args) {
        context.send(DACMessage.CmdArenasList);
        for (Arena arena : DAC.getArenas()) {
            context.send(DACMessage.CmdArenasArena.format(arena.getName(), arena.getWorld().getName()));
        }
    }

}
