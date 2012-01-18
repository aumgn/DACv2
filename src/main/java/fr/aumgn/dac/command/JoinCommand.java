package fr.aumgn.dac.command;

import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class JoinCommand extends PlayerCommandExecutor {
	
	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		Player player = context.getPlayer();
		if (DAC.isPlayerInGame(player)) {
			context.error(DACMessage.CmdJoinAlreadyPlaying);
			return true;	
		}
		DACArena arena = DAC.getArenas().get(player);
		if (arena == null) {
			context.error(DACMessage.CmdJoinNotInStart);
			return true;
		}
		if (DAC.getGame(arena) != null) {
			context.error(DACMessage.CmdJoinInGame);
			return true;
		}
		DACJoinStep joinStep = DAC.getJoinStep(arena);
		if (joinStep == null) {
			joinStep = new DACJoinStep(arena);
			DAC.setJoinStep(joinStep);
		}
		if (joinStep.isMaxReached()) {
			context.error(DACMessage.CmdJoinMaxReached);
			return true;
		}
		joinStep.addPlayer(player, args);
		return true;
	}

}
