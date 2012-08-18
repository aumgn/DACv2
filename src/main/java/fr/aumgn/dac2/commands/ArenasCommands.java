package fr.aumgn.dac2.commands;

import java.util.List;

import org.bukkit.World;
import org.bukkit.command.CommandSender;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.arena.Arenas;

@NestedCommands(name = "dac2")
public class ArenasCommands extends DACCommands {

    public ArenasCommands(DAC dac) {
        super(dac);
    }

    @Command(name = "define", min = 1, max = 2)
    public void define(CommandSender sender, CommandArgs args) {
        String name = args.get(0);
        World world = args.getWorld(1).valueOr(sender);

        dac.getArenas().create(name, world);
        sender.sendMessage(msg("define.success", name));
    }

    @Command(name = "delete", min = 1, max = 1)
    public void delete(CommandSender sender, CommandArgs args) {
        Arenas arenas = dac.getArenas();
        List<Arena> arenasList = args.getList(0, Arena.class).value();

        for (Arena arena : arenasList) {
            arenas.delete(arena);
            sender.sendMessage(msg("delete.success", arena.getName()));
        }
    }

    @Command(name = "arenas")
    public void arenas(CommandSender sender) {
        sender.sendMessage(msg("arenas.header"));
        for (Arena arena : dac.getArenas().all()) {
            sender.sendMessage(msg("arenas.arena", arena.getName(),
                    arena.getWorld().getName()));
        }
    }
}
