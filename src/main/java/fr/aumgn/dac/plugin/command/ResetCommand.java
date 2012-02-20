package fr.aumgn.dac.plugin.command;

import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.utils.command.PlayerCommandContext;

public class ResetCommand extends FillCommand {

    @Override
    public boolean checkUsage(String[] args) {
        return args.length == 1;
    }

    @Override
    public void fill(PlayerCommandContext context, String[] args, Arena arena) {
        arena.getPool().reset();
    }

}
