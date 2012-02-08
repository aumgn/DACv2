package fr.aumgn.dac.command;

import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;
import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class ListCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length == 0;
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		Player player = context.getPlayer();

		DACJoinStep joinStep = DAC.getJoinStep(player);
		if (joinStep != null) {
			joinStep.listPlayersTo(player);
			return;			
		}

		DACGame game = DAC.getGame(context.getPlayer());
		if (game != null) {
			game.displayLives(context.getPlayer());
			return;
		}

		error(DACMessage.CmdLivesNotInGame);
	}

}
