package fr.aumgn.dac.command;

import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;
import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class StartCommand extends PlayerCommandExecutor {
	
	private DAC plugin;

	public StartCommand(DAC plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		Player player = context.getPlayer();
		DACJoinStep joinStep = plugin.getJoinStep(player);
		if (joinStep == null) {
			context.error("Vous devez avoir rejoint une partie avec \"dac join\" avant de la débuter.");
			return true;
		}
		if (!joinStep.isMinReached()) {
			context.error("Au moins deux joueurs doivent avoir rejoint la partie pour pouvoir la débuter.");
			return true;
		}
		DACGame game = new DACGame(plugin, joinStep);
		plugin.setGame(game);
		plugin.removeJoinStep(joinStep);
		return true;
	}

}
