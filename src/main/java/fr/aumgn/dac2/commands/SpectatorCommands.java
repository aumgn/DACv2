package fr.aumgn.dac2.commands;

import java.util.List;

import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.stage.Stage;

@NestedCommands("dac2")
public class SpectatorCommands extends DACCommands {

    public SpectatorCommands(DAC dac) {
        super(dac);
    }

    @Command(name = "watch", min = 1, max = 1)
    public void watch(Player sender, CommandArgs args) {
        List<Stage> stages = args.getList(0, Stage).value();

        for (Stage stage : stages) {
            String arenaName = stage.getArena().getName();
            if (stage.isSpectator(sender)) {
                sender.sendMessage(msg("watch.alreadywatching", arenaName));
                continue;
            }
            if (stage.contains(sender)) {
                sender.sendMessage(msg("watch.playing", arenaName));
                continue;
            }

            stage.addSpectator(sender);
            sender.sendMessage(msg("watch.success", arenaName));
        }
    }

    @Command(name = "unwatch", min = 1, max = 1)
    public void unwatch(Player sender, CommandArgs args) {
        List<Stage> stages = args.getList(0, Stage).value();

        for (Stage stage : stages) {
            String arenaName = stage.getArena().getName();
            if (!stage.isSpectator(sender)) {
                sender.sendMessage(msg("unwatch.notwatching", arenaName));
                continue;
            }

            stage.removeSpectator(sender);
            sender.sendMessage(msg("unwatch.success", arenaName));
        }
    }
}
