package fr.aumgn.dac.plugin.command;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.utils.command.PlayerCommandContext;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class ListCommand extends PlayerCommandExecutor {

    @Override
    public boolean checkUsage(String[] args) {
        return args.length == 0;
    }

    @Override
    public void onPlayerCommand(PlayerCommandContext context, String[] args) {
        Stage stage = DAC.getStageManager().get(context.getPlayer());

        if (stage == null) {
            error(DACMessage.CmdLivesNotInGame);
        }

        for (StagePlayer playerInStage : stage.getPlayers()) {
            context.send(playerInStage.formatForList());
        }
    }

}
