package fr.aumgn.dac2.commands;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.stage.Spectators;
import fr.aumgn.dac2.stage.Stage;
import org.bukkit.entity.Player;

import java.util.List;

@NestedCommands("dac2")
public class SpectatorCommands extends DACCommands {

    public SpectatorCommands(DAC dac) {
        super(dac);
    }

    @Command(name = "watch", min = 1, max = 1)
    public void watch(Player sender, CommandArgs args) {
        List<Stage> stages = args.getList(0, Stage).value();

        for (Stage stage : stages) {
            Spectators spectators = stage.getSpectators();
            String arenaName = stage.getArena().getName();
            if (spectators.contains(sender)) {
                sender.sendMessage(msg("watch.alreadywatching", arenaName));
                continue;
            }
            if (stage.contains(sender)) {
                sender.sendMessage(msg("watch.playing", arenaName));
                continue;
            }

            spectators.add(sender);
            sender.sendMessage(msg("watch.success", arenaName));
        }
    }

    @Command(name = "unwatch", min = 1, max = 1)
    public void unwatch(Player sender, CommandArgs args) {
        List<Stage> stages = args.getList(0, Stage).value();

        for (Stage stage : stages) {
            Spectators spectators = stage.getSpectators();
            String arenaName = stage.getArena().getName();
            if (!stage.getSpectators().contains(sender)) {
                sender.sendMessage(msg("unwatch.notwatching", arenaName));
                continue;
            }

            spectators.remove(sender);
            sender.sendMessage(msg("unwatch.success", arenaName));
        }
    }
}
