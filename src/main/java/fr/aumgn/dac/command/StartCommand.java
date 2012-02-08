package fr.aumgn.dac.command;

import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;
import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class StartCommand extends PlayerCommandExecutor {

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		Player player = context.getPlayer();
		DACJoinStep joinStep = DAC.getJoinStep(player);
		if (joinStep == null) {
			context.error(DACMessage.CmdStartNotInGame);
			return true;
		}
		if (!joinStep.isMinReached()) {
			context.error(DACMessage.CmdStartMinNotReached);
			return true;
		}
		DACGame game = new DACGame(joinStep);
		DAC.addGame(game);
		DAC.removeJoinStep(joinStep);
		return true;
	}

}
