package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class ResetCommand extends PlayerCommandExecutor {
	
	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 1) { return false; }
		DACArena arena = DAC.getArenas().get(args[0]);
		if (arena == null) {
			context.error("Cette arène n'existe pas.");
			return true;
		}
		if (DAC.getGame(arena) != null) {
			context.error("Cette commande ne peut pas être utilisée durant une partie");
			return true;
		}
		arena.getPool().reset();
		context.success("Bassin réinitialisé.");
		return true;
	}

}
