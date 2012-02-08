package fr.aumgn.dac.command;

import fr.aumgn.utils.command.CommandDispatcher;

public class DACCommand extends CommandDispatcher {

	public DACCommand() {
		super("dac");
		registerCommand("reload", new ReloadCommand());
		registerCommand("define", new DefineCommand());
		registerCommand("delete", new DeleteCommand());
		registerCommand("arenas", new ArenasCommand());
		registerCommand("set", new SetCommand());
		registerCommand("select", new SelectCommand());
		
		registerCommand("kick", new KickCommand());
		registerCommand("stop", new StopCommand());
		registerCommand("reset", new ResetCommand());

		registerCommand("join", new JoinCommand());
		registerCommand("start", new StartCommand());
		registerCommand("list", new ListCommand());
		registerCommand("goto", new GotoCommand());
		registerCommand("quit", new QuitCommand());
		registerCommand("colors", new ColorsCommand());
	}

}
