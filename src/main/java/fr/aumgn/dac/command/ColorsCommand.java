package fr.aumgn.dac.command;

import fr.aumgn.dac.DACColor;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class ColorsCommand extends PlayerCommandExecutor {

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 0) { return false; }
		for (DACColor color : DACColor. values()) {
			context.send(color.getChatColor() + color.toString().toLowerCase());
		}
		return true;
	}

}
