package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.utils.command.BasicCommandExecutor;
import fr.aumgn.utils.command.CommandDispatcher;

public class DACCommand extends CommandDispatcher {
	
	public DACCommand(final DAC plugin) {
		super("dac");
		registerCommand("reload", new BasicCommandExecutor() {
			@Override
			public boolean onCommand(Context context, String[] args) {
				plugin.reloadDACConfig();
				context.success("Configuration mise a jour.");
				return true;
			}
		});
		registerCommand("define", new DefineCommand(plugin));
		registerCommand("delete", new DeleteCommand(plugin));
		registerCommand("set", new SetCommand(plugin));
		registerCommand("select", new SelectCommand(plugin));
		registerCommand("join", new JoinCommand(plugin));
		registerCommand("start", new StartCommand(plugin));
		registerCommand("stop", new StopCommand(plugin));
		registerCommand("reset", new ResetCommand(plugin));
		registerCommand("lives", new LivesCommand(plugin));
		registerCommand("goto", new GotoCommand(plugin));
		registerCommand("quit", new QuitCommand(plugin));
		registerCommand("colors", new ColorsCommand());
	}

}
