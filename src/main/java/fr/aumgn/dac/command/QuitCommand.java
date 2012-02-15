package fr.aumgn.dac.command;

import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.player.DACPlayer;
import fr.aumgn.dac.stage.Stage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class QuitCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length == 0;
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		Player player = context.getPlayer(); 
		
		DACPlayer dacPlayer = DAC.getPlayerManager().get(player);
		Stage<?> stage = dacPlayer.getStage();
		
		if (stage == null) {
			error(DACMessage.CmdQuitNotInGame);
		}
		stage.removePlayer(dacPlayer);
	}

}
