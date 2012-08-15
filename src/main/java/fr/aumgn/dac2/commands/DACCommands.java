package fr.aumgn.dac2.commands;

import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.dac2.DAC;

public abstract class DACCommands implements Commands {

    protected final DAC dac;

    public DACCommands(DAC dac) {
        this.dac = dac;
    }
}
