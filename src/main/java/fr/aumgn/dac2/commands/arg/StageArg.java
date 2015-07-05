package fr.aumgn.dac2.commands.arg;

import fr.aumgn.bukkitutils.command.arg.impl.AsbtractSenderArg;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.aumgn.bukkitutils.command.exception.CommandUsageError;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.stage.Stage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StageArg extends AsbtractSenderArg<Stage> {

    private final DAC dac;

    public StageArg(DAC dac, String string) {
        super(string);
        this.dac = dac;
    }

    @Override
    public Stage value() {
        if (!dac.getArenas().has(string)) {
            throw new ArenaArg.NoSuchArena(dac, string);
        }

        Arena arena = dac.getArenas().get(string);
        Stage stage = dac.getStages().get(arena);
        if (stage == null) {
            throw new NoStageForArena(dac, arena);
        }

        return stage;
    }

    @Override
    protected Stage defaultFor(CommandSender sender) {
        if (!(sender instanceof Player)) {
            throw new CommandUsageError(dac.getCmdMessages()
                    .get("stage.arg.needed"));
        }

        Stage stage = dac.getStages().get((Player) sender);
        if (stage == null) {
            throw new NoStageForPlayer(dac);
        }

        return stage;
    }

    @Override
    protected String missingPermOtherMessage(String permission) {
        return dac.getCmdMessages().get("stage.arg.otherpermissionmissing");
    }

    public static class NoStageForArena extends CommandError {

        private static final long serialVersionUID = 8898579114644791040L;

        public NoStageForArena(DAC dac, Arena arena) {
            super(dac.getCmdMessages()
                    .get("stage.arg.nostageforarena", arena.getName()));
        }
    }

    public static class NoStageForPlayer extends CommandError {

        private static final long serialVersionUID = 2665340229475254801L;

        public NoStageForPlayer(DAC dac) {
            super(dac.getCmdMessages()
                    .get("stage.arg.nostageforplayer"));
        }
    }
}
