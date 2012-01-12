package fr.aumgn.dac.command;

import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;
import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class StartCommand extends PlayerCommandExecutor {
	
	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		Player player = context.getPlayer();
		DACJoinStep joinStep = DAC.getJoinStep(player);
		if (joinStep == null) {
			context.error("Vous devez avoir rejoint une partie avec \"dac join\" avant de la débuter.");
			return true;
		}
		if (!joinStep.isMinReached()) {
			context.error("Au moins deux joueurs doivent avoir rejoint la partie pour pouvoir la débuter.");
			return true;
		}
		DACGame game = new DACGame(joinStep);
		DAC.setGame(game);
		DAC.removeJoinStep(joinStep);
		return true;
	}

}
