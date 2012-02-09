package fr.aumgn.dac.command;

import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.player.DACPlayer;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class GotoCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length == 1 && ( 
				args[0].equalsIgnoreCase("diving") ||
				args[0].equalsIgnoreCase("start")
				);
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		Player player = context.getPlayer();
		DACPlayer dacPlayer = DAC.getPlayerManager().get(player);
		if (dacPlayer == null) {
			error(DACMessage.CmdGotoNotInGame);
		}
		
		if (args[0].equalsIgnoreCase("diving")) {
			DACArena arena = dacPlayer.getStage().getArena();
			player.teleport(arena.getDivingBoard().getLocation());
		} else if (args[0].equalsIgnoreCase("start")) {
			player.teleport(dacPlayer.getStartLocation());
		}
		context.success(DACMessage.CmdGotoSuccess);
	}

}
