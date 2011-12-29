package fr.aumgn.dac.command;

import fr.aumgn.dac.DACColor;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class ColorsCommand extends PlayerCommandExecutor {

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 0) { return false; }
		DACColor[] colors = DACColor.values();
		for (int i=0; i < colors.length; i+=3) {  
			String msg = getColorMessage(colors[i]);
			msg += " " + getColorMessage(colors[i+1]);
			msg += " " + getColorMessage(colors[i+2]);
			context.send(msg);
		}
		return true;
	}
	
	private String getColorMessage(DACColor color) {
		return color.getChatColor() + color.toString().toLowerCase();
	}

}
