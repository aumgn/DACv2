package fr.aumgn.dac.plugin.command;

import java.util.Collections;

import org.bukkit.command.CommandSender;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.CommandArgs;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.exception.DACException;
import fr.aumgn.dac.api.fillstrategy.FillStrategy;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.mode.DACGameMode;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.stage.Stage;

@NestedCommands(name = "dac")
public class PoolCommands extends DACCommands {

    public Arena getArena(CommandSender sender, CommandArgs args) {
        Arena arena = DAC.getArenas().get(args.get(0));
        if (arena == null) {
            throw new DACException(DACMessage.CmdFillUnknown);
        }

        return arena;
    }

    public void ensureHasPermissionForArena(CommandSender sender, Arena arena) {
        Stage stage = DAC.getStageManager().get(arena);
        if (stage instanceof Game && !sender.hasPermission("dac.game.fill")) {
            GameMode mode = ((Game) stage).getMode();
            if (!mode.getClass().getAnnotation(DACGameMode.class).allowFill()) {
                throw new DACException(DACMessage.CmdFillInGame);
            }
        }
    }

    @Command(name = "fill", min = 2, max = -1)
    public void fill(CommandSender sender, CommandArgs args) {
        Arena arena = getArena(sender, args);
        ensureHasPermissionForArena(sender, arena);

        FillStrategy strategy = DAC.getFillStrategies().get(args.get(1));
        if (strategy == null) {
            throw new DACException(DACMessage.CmdFillUnknownStrategy);
        }

        if (args.length() > 2) {
            arena.getPool().fillWith(strategy, args.asList(2));
        } else {
            arena.getPool().fillWith(strategy, Collections.<String>emptyList());
        }
        success(sender, DACMessage.CmdFillSuccess);
    }

    @Command(name = "reset", min = 1, max = 1)
    public void reset(CommandSender sender, CommandArgs args) {
        Arena arena = getArena(sender, args);
        ensureHasPermissionForArena(sender, arena);

        arena.getPool().reset();
        success(sender, DACMessage.CmdFillSuccess);
    }


}
