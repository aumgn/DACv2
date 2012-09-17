package fr.aumgn.dac.plugin.command;

import java.util.Map.Entry;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACColor;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.exception.DACException;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;

@NestedCommands(name = "dac")
public class InfoCommands extends DACCommands {

    @Command(name = "colors")
    public void colors(CommandSender sender, CommandArgs args) {
        int i = 0;
        StringBuilder msg = new StringBuilder(32);
        for (Entry<String, DACColor> colorEntry : DAC.getColors().colors()) {
            msg.append(getColorMessage(colorEntry));
            msg.append(" ");
            if (i == 2) {
                sender.sendMessage(msg.toString());
                i = 0;
                msg = new StringBuilder(32);
            } else {
                i++;
            }
        }

        if (i != 0) {
            sender.sendMessage(msg.toString());
        }
    }

    private String getColorMessage(Entry<String, DACColor> colorEntry) {
        return colorEntry.getValue().getChatColor() + colorEntry.getKey();
    }

    @Command(name = "list")
    public void list(Player player, CommandArgs args) {
        Stage stage = DAC.getStageManager().get(player);

        if (stage == null) {
            throw new DACException(DACMessage.CmdLivesNotInGame);
        }

        for (StagePlayer playerInStage : stage.getPlayers()) {
            player.sendMessage("  " + playerInStage.formatForList());
        }
    }
}
