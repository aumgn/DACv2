package fr.aumgn.dac.plugin.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.CommandArgs;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.Area;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.exception.DACException;
import fr.aumgn.dac.api.exception.InvalidRegionType;

@NestedCommands(name = "dac")
public class ArenasCommands extends DACCommands {

    @Command(name = "arenas")
    public void arenas(CommandSender sender, CommandArgs args) {
        sender.sendMessage(DACMessage.CmdArenasList.getContent());
        for (Arena arena : DAC.getArenas()) {
            sender.sendMessage(DACMessage.CmdArenasArena.getContent(
                    arena.getName(), arena.getWorld().getName()));
        }
    }

    @Command(name = "define", min = 1, max = 1)
    public void define(Player player, CommandArgs args) {
        String name = args.get(0);
        if (DAC.getArenas().get(name) != null) {
            throw new CommandError(DACMessage.CmdDefineExists.toString());
        }

        DAC.getArenas().createArena(name, player.getWorld());
        success(player, DACMessage.CmdDefineSuccess);
    }

    @Command(name = "delete", min = 1, max = 1)
    public void delete(CommandSender sender, CommandArgs args) {
        Arena arena = DAC.getArenas().get(args.get(0));
        if (arena == null) {
            throw new DACException(DACMessage.CmdDeleteUnknown);
        }

        if (DAC.getStageManager().hasStage(arena)) {
            throw new DACException(DACMessage.CmdDeleteInGame);
        }

        DAC.getArenas().removeArena(arena);
        success(sender, DACMessage.CmdDeleteSuccess);
    }

    @Command(name = "select", min = 2, max = 2)
    public void select(Player player, CommandArgs args) {
        Arena arena = DAC.getArenas().get(args.get(0));
        if (arena == null) {
            throw new DACException(DACMessage.CmdSelectUnknown);
        }

        Area area = null;
        DACMessage message = DACMessage.CmdSelectError;
        String type = args.get(1);
        if (type.equalsIgnoreCase("pool")) {
            area = arena.getPool();
            message = DACMessage.CmdSelectSuccessPool;
        } else if (type.equalsIgnoreCase("start")) {
            area = arena.getStartArea();
            message = DACMessage.CmdSelectSuccessStart;
        }

        try {
            DAC.getWorldEdit().setSelection(player, area.getSelection());
            success(player, message);
        } catch (InvalidRegionType exc) {
            throw new DACException(DACMessage.CmdSelectError);
        }
    }
}
