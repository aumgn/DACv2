package fr.aumgn.dac.command;

import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.game.SimpleGame;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.joinstage.JoinStage;
import fr.aumgn.dac.stage.Stage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class StartCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length <= 1;
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		Player player = context.getPlayer();
		Stage stage = DAC.getStageManager().get(player);
		if (!(stage instanceof JoinStage)) {
			error(DACMessage.CmdStartNotInGame);
		}
		JoinStage joinStage = (JoinStage) stage;
		
		GameMode mode;
		if (args.length == 1) {
			mode = DAC.getModes().get(args[0]);
			if (mode == null) {
				error(DACMessage.CmdStartUnknownMode);
			} else if (!joinStage.getArena().hasMode(args[0])) {
				error(DACMessage.CmdStartUnavailableMode);
			}
		} else {
			mode = DAC.getModes().get("classic");
		}
		
		if (!joinStage.isMinReached(mode)) {
			error(DACMessage.CmdStartMinNotReached);
		}
		
		new SimpleGame(mode, joinStage);
	}

}
