package fr.aumgn.dac.api;

import fr.aumgn.utils.command.BasicCommandExecutor;

/** 
 * Represents the main "/dac" command which manages all subcommands.  
 */
public interface DACCommand {

    /**
     * Registers a subcommand.
     * <p/>
     * The command still need to be registered in plugin.yml as dac-{name}.
     */
    void registerCommand(String name, BasicCommandExecutor executor);

}
