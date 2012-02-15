package fr.aumgn.dac.plugin.command;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACColor;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class ColorsCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length == 0;
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
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

		if (i!=0) {
			context.send(msg);
		}
	}

	private String getColorMessage(DACColor color) {
		return color.getChatColor() + color.getName();
	}

}
