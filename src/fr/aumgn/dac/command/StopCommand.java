package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;
import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class StopCommand extends PlayerCommandExecutor {

	private DAC plugin;
	
	public StopCommand(DAC plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		DACGame game = plugin.getGame(context.getPlayer());
		if (game == null) {
			DACJoinStep joinStep = plugin.getJoinStep(context.getPlayer());
			if (joinStep == null) {
				context.error("Aucune partie en cours a arreté");
				return true;
			}
			joinStep.stop();
			plugin.removeJoinStep(joinStep);
			return true;
		}
		game.stop();
		plugin.removeGame(game);
		return true;
	}

}
