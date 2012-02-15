package fr.aumgn.dac.command;

import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.stage.StagePlayer;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class GotoCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length == 1 && ( 
				args[0].equalsIgnoreCase("diving") ||
				args[0].equalsIgnoreCase("start"));
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		Player player = context.getPlayer();
		StagePlayer dacPlayer = DAC.getPlayerManager().get(player);
		if (dacPlayer == null || !(dacPlayer.getStage() instanceof Game)) {
			error(DACMessage.CmdGotoNotInGame);
		}
		
		if (args[0].equalsIgnoreCase("diving")) {
			dacPlayer.tpToDiving();
		} else if (args[0].equalsIgnoreCase("start")) {
			dacPlayer.tpToStart();
		}
		context.success(DACMessage.CmdGotoSuccess);
	}

}
