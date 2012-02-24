package fr.aumgn.dac.plugin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.stage.StagePlayerManager;
import fr.aumgn.dac.api.stage.StageQuitReason;
import fr.aumgn.utils.command.PlayerCommandContext;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class KickCommand extends PlayerCommandExecutor {

    @Override
    public boolean checkUsage(String[] args) {
        return args.length == 1;
    }

    @Override
    public void onPlayerCommand(PlayerCommandContext context, String[] args) {
        int count = 0;
        StagePlayerManager playerManager = DAC.getPlayerManager();
        for (Player player : Bukkit.getOnlinePlayers()) {
            StagePlayer stagePlayer = playerManager.get(player);
            if (stagePlayer != null) {
                if (ChatColor.stripColor(stagePlayer.getDisplayName()).startsWith(args[0])) {
                    stagePlayer.getStage().removePlayer(stagePlayer, StageQuitReason.Forced);
                    count++;
                }
            }
        }
        if (count == 0) {
            error(DACMessage.CmdKickNoPlayerFound);
        } else {
            context.success(DACMessage.CmdKickSuccess.format(count));
        }
    }

}
