package fr.aumgn.utils.command;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;

public class CommandDispatcher extends BasicCommandExecutor {
	
	private String name;
	private CommandExecutor defaultExecutor;
	private Set<String> commands;
	
	public CommandDispatcher(String name) {
		this.name = name;
		this.commands = new HashSet<String>();
	}
	
	public CommandDispatcher(String name, CommandExecutor defaultExecutor) {
		this(name);
		this.defaultExecutor = defaultExecutor;
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

	public void registerCommand(String cmdName, BasicCommandExecutor executor) {
		String subCommandName = getSubCommandName(cmdName);
		PluginCommand cmd = Bukkit.getPluginCommand(subCommandName);
		cmd.setExecutor(executor);
		commands.add(subCommandName);
	}
	
	@Override
	public boolean onCommand(Context context, String[] args) {
		if (args.length < 1) {
			return callDefaultExecutor(context, args);
		}
		if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
			return callHelp(context, args);
		}
		return callSubCommand(context, args);
	}
	
	private boolean callDefaultExecutor(Context context, String[] args) {
		if (defaultExecutor instanceof BasicCommandExecutor) {
			return ((BasicCommandExecutor)defaultExecutor).onCommand(context, args);
		} else if (defaultExecutor != null) {
			return defaultExecutor.onCommand(context.getSender(), context.getCommand(), context.getLabel(), args);
		} else {
			return false;
		}		
	}

	private boolean callHelp(Context context, String[] args) {
		if (args.length == 1) {
			context.send("Utilisation: /" + context.getLabel() + " " + args[0] + " #command");
		} else {
			String subName = getSubCommandName(args[1]);
			Command subCmd = Bukkit.getPluginCommand(subName);
			if (subCmd != null && context.getSender().hasPermission(subCmd.getPermission())) {
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
	
	private boolean callSubCommand(Context context, String[] args) {
		Command subCmd = Bukkit.getPluginCommand(getSubCommandName(args[0]));
		if (subCmd != null && commands.contains(subCmd.getName())) {
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
