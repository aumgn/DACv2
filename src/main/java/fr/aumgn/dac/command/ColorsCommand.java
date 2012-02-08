package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.config.DACColor;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class ColorsCommand extends PlayerCommandExecutor {

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 0) { return false; }
		int i = 0;
		StringBuilder msg = new StringBuilder(32);
		for (DACColor color : DAC.getConfig().getColors()) {  
			msg.append(getColorMessage(color));
			msg.append(" ");
			if (i==2) {
				context.send(msg);
				i = 0;
				msg = new StringBuilder(32);
			} else {
				i++;
			}
		}
		if (i!=0) { context.send(msg); }
		return true;
	}

	private String getColorMessage(DACColor color) {
		return color.getChatColor() + color.getName();
	}

}
