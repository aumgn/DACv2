package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class GotoCommand extends PlayerCommandExecutor {
	
	private DAC plugin;
	
	public GotoCommand(DAC plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 1) { return false; }
		DACGame game = plugin.getGame(context.getPlayer());
		if (game == null) {
			context.error("Cette commande ne peut etre utilisé que durant une parite de DAC.");
			return true;
		}
		if (args[0].equalsIgnoreCase("diving")) {
			context.getPlayer().teleport(game.getArena().getDivingBoard());
			context.success("Poof !");
			return true;
		}
		if (args[0].equalsIgnoreCase("start")) {
			game.wrapPlayer(context.getPlayer()).tpToStart();
			context.success("Poof !");
			return true;
		}
		return false;
	}

}
