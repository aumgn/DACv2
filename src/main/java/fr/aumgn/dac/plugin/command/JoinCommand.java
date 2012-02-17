package fr.aumgn.dac.plugin.command;

import org.bukkit.entity.Player;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.joinstage.JoinStage;
import fr.aumgn.dac.api.joinstage.SimpleJoinStage;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class JoinCommand extends PlayerCommandExecutor {

    @Override
    public void onPlayerCommand(Context context, String[] args) {
        Player player = context.getPlayer();

        Stage<?> stage = DAC.getStageManager().get(player);
        if (stage != null) {
            error(DACMessage.CmdJoinAlreadyPlaying);
        }

        Arena arena = DAC.getArenas().get(player);
        if (arena == null) {
            error(DACMessage.CmdJoinNotInStart);
        }

        stage = DAC.getStageManager().get(arena);
        if (stage != null && !(stage instanceof JoinStage)) {
            error(DACMessage.CmdJoinInGame);
        }

        JoinStage<?> joinStage;
        if (stage == null) {
            joinStage = new SimpleJoinStage(arena);
            DAC.getStageManager().register(joinStage);
        } else {
            joinStage = (JoinStage<?>) stage;
        }

        if (joinStage.isMaxReached()) {
            error(DACMessage.CmdJoinMaxReached.format(DAC.getConfig().getMaxPlayers()));
        }

        joinStage.addPlayer(player, args);
    }

}
