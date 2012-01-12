package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACColors.DACColor;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class ColorsCommand extends PlayerCommandExecutor {

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 0) { return false; }
		int i = 0;
		String msg = "";
		for (DACColor color : DAC.getDACConfig().getColors()) {  
			msg += getColorMessage(color) + " ";
			if (i==2) {
				context.send(msg);
				msg = "";
				i = 0;
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
