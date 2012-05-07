package fr.aumgn.dac.plugin.command;

import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.CommandArgs;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.exception.DACException;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.stage.Stage;

@NestedCommands(name = "dac")
public class SpectatorCommands extends DACCommands {

    @Command(name = "watch", max = 1)
    public void watch(Player player, CommandArgs args) {
        if (args.length() == 0) {
            Arena arena = DAC.getArenas().get(player);
            if (arena == null) {
                throw new DACException(DACMessage.CmdWatchNotInArena);
            }
            watchArena(player, arena);
        } else {
            for (String arg : args) {
                Arena arena = DAC.getArenas().get(arg);
                if (arena == null) {
                    throw new DACException(DACMessage.CmdWatchUnknown.getContent(arg));
                } else {
                    watchArena(player, arena);
                }
            }
        }

    }

    private void watchArena(Player player, Arena arena) {
        Stage stage = DAC.getStageManager().get(arena);
        if (!(stage instanceof Game)) {
            return;
        }

        Game game = (Game) stage;
        if (game.canWatch(player)) {
            game.addSpectator(player);
            success(player, DACMessage.CmdWatchSuccess.getContent(arena.getName()));
        } else {
            player.sendMessage(DACMessage.CmdWatchAlreadyWatching.getContent(arena.getName()));
        }
    }

    @Command(name = "unwatch")
    public void unwatch(Player player, CommandArgs args) {
        if (args.length() == 0) {
            for (Arena arena : DAC.getArenas()) {
                unwatchArena(player, arena);
            }
        } else {
            for (String arg : args) {
                Arena arena = DAC.getArenas().get(arg);
                if (arena == null) {
                    throw new DACException(DACMessage.CmdWatchUnknown.getContent(arg));
                } else {
                    unwatchArena(player, arena);
                }
            }
        }
    }

    private void unwatchArena(Player player, Arena arena) {
        Stage stage = DAC.getStageManager().get(arena);
        if (!(stage instanceof Game)) {
            return;
        }

        if (((Game) stage).removeSpectator(player)) {
            success(player, DACMessage.CmdUnwatchSuccess.getContent(arena.getName()));
        }
    }
}
