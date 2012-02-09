package fr.aumgn.dac.command;

import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
//import fr.aumgn.dac.DACGame;
//import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.dac.config.DACMessage;
//import fr.aumgn.dac.game.BasicGame;
import fr.aumgn.dac.game.SimpleGame;
import fr.aumgn.dac.game.mode.DACGameModes;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.joinstep.JoinStage;
import fr.aumgn.dac.stage.Stage;
import fr.aumgn.dac.stage.StageManager;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class StartCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length <= 1;
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		Player player = context.getPlayer();
		StageManager stageManager =  DAC.getStageManager();
		Stage stage = stageManager.getPlayer(player).getStage();
		if (!(stage instanceof JoinStage)) {
			error(DACMessage.CmdStartNotInGame);
		}
		JoinStage joinStage = (JoinStage) stage;
		
		GameMode mode;
		if (args.length == 1) {
			mode = DACGameModes.get(args[0]);
			if (mode == null) {
				error(DACMessage.CmdStartUnknownMode);
			}
		} else {
			mode = DACGameModes.get("default");
		}
		
		if (!joinStage.isMinReached(mode)) {
			error(DACMessage.CmdStartMinNotReached);
		}
		
		stageManager.unregister(joinStage);
		SimpleGame game = new SimpleGame(mode, joinStage);
		stageManager.register(game);
	}

}
