package fr.aumgn.dac2.commands;

import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.bukkitutils.command.arg.CommandArgFactory;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.commands.arg.ArenaArg;
import fr.aumgn.dac2.commands.arg.StageArg;

public abstract class DACCommands implements Commands {

    protected final DAC dac;
    protected final CommandArgFactory<ArenaArg> Arena;
    protected final CommandArgFactory<StageArg> Stage;

    public DACCommands(DAC dac) {
        this.dac = dac;
        this.Arena = new CommandArgFactory<ArenaArg>() {
            @Override
            public ArenaArg createCommandArg(String string) {
                return new ArenaArg(DACCommands.this.dac, string);
            }
        };
        this.Stage = new CommandArgFactory<StageArg>() {
            @Override
            public StageArg createCommandArg(String string) {
                return new StageArg(DACCommands.this.dac, string);
            }
        };
    }

    protected String msg(String key) {
        return dac.getCmdMessages().get(key);
    }

    protected String msg(String key, Object... arguments) {
        return dac.getCmdMessages().get(key, arguments);
    }
}
