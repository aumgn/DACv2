package fr.aumgn.dac.command;

import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class JoinCommand extends PlayerCommandExecutor {

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		Player player = context.getPlayer();
		
		if (DAC.isPlayerInGame(player)) {
			error(DACMessage.CmdJoinAlreadyPlaying);
		}
		
		DACArena arena = DAC.getArenas().get(player);
		if (arena == null) {
			error(DACMessage.CmdJoinNotInStart);
		}
		
		if (DAC.getGame(arena) != null) {
			error(DACMessage.CmdJoinInGame);
		}
		
		DACJoinStep joinStep = DAC.getJoinStep(arena);
		if (joinStep == null) {
			joinStep = new DACJoinStep(arena);
			DAC.addJoinStep(joinStep);
		}
		
		if (joinStep.isMaxReached()) {
			error(DACMessage.CmdJoinMaxReached.format(joinStep.getMaxPlayers()));
		}
		
		joinStep.addPlayer(player, args);
	}

}
