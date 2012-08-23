package fr.aumgn.dac2.commands.arg;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.CommandsMessages;
import fr.aumgn.bukkitutils.command.arg.CommandArg;
import fr.aumgn.bukkitutils.command.arg.CommandArgFactory;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.aumgn.bukkitutils.command.exception.CommandUsageError;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.stage.Stage;

public class StageArg extends CommandArg<Stage> {

    public static class Factory extends CommandArgFactory<Stage> {

        private final DAC dac;

        public Factory(DAC dac) {
            this.dac = dac;
        }

        @Override
        public CommandArg<Stage> createCommandArg(CommandsMessages messages,
                String string) {
            return new StageArg(dac, messages, string);
        }
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

    private final DAC dac;

    public StageArg(DAC dac, CommandsMessages messages, String string) {
        super(messages, string);
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
}
