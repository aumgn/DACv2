package fr.aumgn.dac.plugin.command;

import java.util.Map;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.utils.command.BasicCommandExecutor;

public class OptionsCommand extends BasicCommandExecutor {

    @Override
    public boolean checkUsage(String[] args) {
        if (args.length == 1) {
            return true;
        }
        return args.length > 2 && (
                args[1].equalsIgnoreCase("set") ||
                args[1].equalsIgnoreCase("rm"));
    }

    @Override
    public void onCommand(Context context, String[] args) {
        Arena arena = DAC.getArenas().get(args[0]);
        if (arena == null) {
            error(DACMessage.CmdOptionsUnknown);
        }

        if (args.length == 1) {
            context.send(DACMessage.CmdOptionsList);
            for (Map.Entry<String, String> option : arena.options()) {
                context.send(DACMessage.CmdOptionsOption.format(option.getKey(), option.getValue()));
            }
            return;
        }

        if (args[1].equalsIgnoreCase("set")) {
            for (int i = 2; i < args.length; i++) {
                String[] option = args[i].split(":");
                if (option.length == 2) {
                    arena.setOption(option[0], option[1]);
                }
            }
            context.send(DACMessage.CmdOptionsAddSuccess);
        } else if (args[1].equalsIgnoreCase("rm")) {
            for (int i = 2; i < args.length; i++) {
                arena.removeOption(args[i]);
            }
            context.send(DACMessage.CmdOptionsRemoveSuccess);
        }
    }

}
