package fr.aumgn.dac2.commands.arg;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Messages;
import fr.aumgn.bukkitutils.command.arg.CommandArg;
import fr.aumgn.bukkitutils.command.arg.CommandArgFactory;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.aumgn.bukkitutils.command.exception.CommandUsageError;

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

        public NoSuchArena(DAC dac, String name) {
            super(dac.getCmdMessages().get("arena.arg.notfound", name));
        }
    }

    public static class NotInArena extends CommandError {

        private static final long serialVersionUID = 6112644121244362679L;

        public NotInArena(DAC dac) {
            super(dac.getCmdMessages().get("arena.arg.notinarena"));
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
            throw new NoSuchArena(dac, string);
        }

        return dac.getArenas().get(string);
    }

    @Override
    protected Arena defaultFor(CommandSender sender) {
        if (!(sender instanceof Player)) {
            throw new CommandUsageError(dac.getCmdMessages()
                    .get("arena.arg.needed"));
        }

        Arena arena = dac.getArenas().get((Player) sender);
        if (arena == null) {
            throw new NotInArena(dac);
        }

        return arena;
    }
}
