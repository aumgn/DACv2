package fr.aumgn.dac.plugin.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.stage.StagePlayersManager;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class KickCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length == 1;
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		StagePlayersManager playerManager = DAC.getPlayerManager();
		for (Player player : matchPlayer(context, args[0])) {
			StagePlayer dacPlayer = playerManager.get(player);
			dacPlayer.getStage().removePlayer(dacPlayer);
		}
	}

	public List<Player> matchPlayer(Context context, String arg) {
		List<Player> list = Bukkit.matchPlayer(arg);
		if (list.isEmpty()) {
			error(DACMessage.CmdKickNoPlayerFound);
		}
		return list;
	}

}
