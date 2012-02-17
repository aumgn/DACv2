package fr.aumgn.dac.plugin.command;

import fr.aumgn.dac.api.arena.Arena;

public class ResetCommand extends FillCommand {

    @Override
    public boolean checkUsage(String[] args) {
        return args.length == 1;
    }

    @Override
    public void fill(Context context, String[] args, Arena arena) {
        arena.getPool().reset();
    }

}
