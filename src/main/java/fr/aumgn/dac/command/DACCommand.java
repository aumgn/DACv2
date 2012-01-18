package fr.aumgn.dac.command;

import fr.aumgn.utils.command.CommandDispatcher;

public class DACCommand extends CommandDispatcher {

	public DACCommand() {
		super("dac");
		registerCommand("reload", new ReloadCommand());
		registerCommand("define", new DefineCommand());
		registerCommand("delete", new DeleteCommand());
		registerCommand("set", new SetCommand());
		registerCommand("select", new SelectCommand());
		registerCommand("join", new JoinCommand());
		registerCommand("start", new StartCommand());
		registerCommand("stop", new StopCommand());
		registerCommand("reset", new ResetCommand());
		registerCommand("lives", new LivesCommand());
		registerCommand("goto", new GotoCommand());
		registerCommand("quit", new QuitCommand());
		registerCommand("colors", new ColorsCommand());
	}

}
