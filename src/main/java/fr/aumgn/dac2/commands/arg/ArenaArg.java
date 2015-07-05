package fr.aumgn.dac2.commands.arg;

import fr.aumgn.bukkitutils.command.arg.impl.AsbtractSenderArg;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.aumgn.bukkitutils.command.exception.CommandUsageError;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaArg extends AsbtractSenderArg<Arena> {

    private final DAC dac;

    public ArenaArg(DAC dac, String string) {
        super(string);
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

    @Override
    protected String missingPermOtherMessage(String permission) {
        return dac.getCmdMessages().get("arena.arg.otherpermissionmissing");
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
}
