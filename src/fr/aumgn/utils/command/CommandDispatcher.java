package fr.aumgn.utils.command;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;

public class CommandDispatcher extends CommandExecutor {
	
	private String name;
	private CommandExecutor defaultExecutor;
	private HashMap<String, PluginCommand> commands;
	
	public CommandDispatcher(String name) {
		this.name = name;
		this.commands = new HashMap<String, PluginCommand>();
	}
	
	public CommandDispatcher(String name, CommandExecutor defaultExecutor) {
		this(name);
		setDefaultExecutor(defaultExecutor);
	}
	
	public CommandExecutor getDefaultExecutor() {
		return defaultExecutor;
	}

	public void setDefaultExecutor(CommandExecutor defaultExecutor) {
		this.defaultExecutor = defaultExecutor;
	}
	
	private String getSubCommandName(String cmdName) {
		return (name + "-" + cmdName);
	}

	public void registerCommand(String cmdName, CommandExecutor executor) {
		String subCommandName = getSubCommandName(cmdName);
		PluginCommand cmd = Bukkit.getPluginCommand(subCommandName);
		cmd.setExecutor(executor);
		commands.put(subCommandName, cmd);
	}
	
	@Override
	public boolean onCommand(Context context, String[] args) {
		if (args.length < 1) {
			if (defaultExecutor == null)
				return false;
			else
				return defaultExecutor.onCommand(context, args);
		}
		
		if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
			if (args.length == 1) {
				context.send("Usage: /" + context.getLabel() + " " + args[0] + " #command");
			} else {
				String name = getSubCommandName(args[1]);
				Command subCmd = Bukkit.getPluginCommand(name);
				if (subCmd != null) {
					context.success("Description :");
					context.send("  " + subCmd.getDescription());
					String usage = subCmd.getUsage();
					usage = usage.replaceAll("<command>", context.getLabel() + " " + args[1]);
					context.success(usage);
				} else {
					context.error("Aucune commande ne porte ce nom.");
				}
			}
			return true;
		}
		
		Command subCmd = Bukkit.getPluginCommand(getSubCommandName(args[0]));
		if (subCmd != null && commands.containsKey(subCmd.getName())) {
			String[] subArgs = new String[args.length-1];
			System.arraycopy(args, 1, subArgs, 0, args.length-1);
			String subLabel = context.getLabel() + " " + args[0];
			subCmd.execute(context.getSender(), subLabel, subArgs);
			return true;
		} else {
			return false;
		}
	}

}
