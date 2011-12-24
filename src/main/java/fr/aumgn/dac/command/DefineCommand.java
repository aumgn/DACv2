package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class DefineCommand extends PlayerCommandExecutor {

	private final DAC plugin;

	public DefineCommand(DAC plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 1) { return false; }
		if (plugin.getDACConfig().get(args[0]) != null) {
			context.error("Une arène portant ce nom existe deja");
			return true;
		}
		plugin.getDACConfig().createArena(args[0], context.getPlayer().getWorld());
		context.success("Arène créée avec succés");
		return true;
	}

}
