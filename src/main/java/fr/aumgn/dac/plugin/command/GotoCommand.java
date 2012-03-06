package fr.aumgn.dac.plugin.command;

import org.bukkit.entity.Player;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.util.PlayerTeleporter;
import fr.aumgn.utils.command.PlayerCommandContext;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class GotoCommand extends PlayerCommandExecutor {

    @Override
    public boolean checkUsage(String[] args) {
        return args.length == 1 && (args[0].equalsIgnoreCase("diving") || args[0].equalsIgnoreCase("start"));
    }

    @Override
    public void onPlayerCommand(PlayerCommandContext context, String[] args) {
        Player player = context.getPlayer();
        StagePlayer dacPlayer = DAC.getPlayerManager().get(player);
        if (dacPlayer == null || !(dacPlayer.getStage() instanceof Game)) {
            error(DACMessage.CmdGotoNotInGame);
        }

        if (args[0].equalsIgnoreCase("diving")) {
            new PlayerTeleporter(dacPlayer).toDiving();
        } else if (args[0].equalsIgnoreCase("start")) {
            new PlayerTeleporter(dacPlayer).toStart();
        }
        context.success(DACMessage.CmdGotoSuccess);
    }

}
