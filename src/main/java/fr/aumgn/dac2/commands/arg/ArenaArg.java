package fr.aumgn.dac2.commands.arg;

import fr.aumgn.bukkitutils.command.Messages;
import fr.aumgn.bukkitutils.command.arg.CommandArg;
import fr.aumgn.bukkitutils.command.arg.CommandArgFactory;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.aumgn.bukkitutils.localization.PluginMessages;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;

public class ArenaArg extends CommandArg<Arena> {

    public static class Factory extends CommandArgFactory<Arena> {

        private final DAC dac;

        public Factory(DAC dac) {
            this.dac = dac;
        }

        @Override
        public CommandArg<Arena> createCommandArg(Messages messages, String string) {
            return new ArenaArg(dac, messages, string);
        }
    }

    public static class NoSuchArena extends CommandError {
        private static final long serialVersionUID = -4832133406864970323L;

        public NoSuchArena(PluginMessages messages, String name) {
            super(messages.get("arena.arg.notfound", name));
        }
    }

    private final DAC dac;

    public ArenaArg(DAC dac, Messages messages, String string) {
        super(messages, string);
        this.dac = dac;
    }

    @Override
    public Arena value() {
        if (!dac.getArenas().has(string)) {
            throw new NoSuchArena(dac.getCmdMessages(), string);
        }

        return dac.getArenas().get(string);
    }
}
