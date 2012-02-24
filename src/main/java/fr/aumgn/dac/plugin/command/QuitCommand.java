package fr.aumgn.dac.plugin.command;

import org.bukkit.entity.Player;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.stage.StageQuitReason;
import fr.aumgn.utils.command.PlayerCommandContext;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class QuitCommand extends PlayerCommandExecutor {

    @Override
    public boolean checkUsage(String[] args) {
        return args.length == 0;
    }

    @Override
    public void onPlayerCommand(PlayerCommandContext context, String[] args) {
        Player player = context.getPlayer();

        StagePlayer stagePlayer = DAC.getPlayerManager().get(player);

        if (stagePlayer == null || stagePlayer.getStage() == null) {
            error(DACMessage.CmdQuitNotInGame);
        }
        stagePlayer.getStage().removePlayer(stagePlayer, StageQuitReason.Command);
    }

}
