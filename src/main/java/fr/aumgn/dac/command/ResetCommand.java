package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.config.DACArena;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class ResetCommand extends PlayerCommandExecutor {
	
	private DAC plugin;
	
	public ResetCommand(DAC plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 1) { return false; }
		DACArena arena = plugin.getDACConfig().get(args[0]);
		if (arena == null) {
			context.error("Cette arène n'existe pas.");
			return true;
		}
		if (plugin.getGame(arena) != null) {
			context.error("Cette commande ne peut pas être utilisée durant une partie");
			return true;
		}
		arena.getPool().reset();
		context.success("Bassin réinitialisé.");
		return true;
	}

}
