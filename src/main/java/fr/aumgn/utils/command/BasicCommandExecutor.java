package fr.aumgn.utils.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public abstract class BasicCommandExecutor implements org.bukkit.command.CommandExecutor {

	public static class Context {
	
		private CommandSender sender;
		private Command command;
		private String label;

		public Context(CommandSender sender, Command command, String label) {
			this.sender = sender;
			this.command = command;
			this.label = label;
		}
	
		public CommandSender getSender() {
			return sender;
		}
	
		public Command getCommand() {
			return command;
		}
	
		public String getLabel() {
			return label;
		}
	
		public boolean isConsoleCommand() {
			return (sender instanceof ConsoleCommandSender);
		}
	
		public boolean isPlayerCommand() {
			return (sender instanceof Player);
		}
		
		public Player getPlayer() {
			return (Player)sender;
		}
		
		public void send(String message) {
			sender.sendMessage(message);
		}
		
		public void success(String message) {
			send(ChatColor.GREEN + message);
		}
		
		public void error(String message) {
			send(ChatColor.RED + message);
		}
		
		public void send(Object message) {
			sender.sendMessage(message.toString());
		}
		
		public void success(Object message) {
			send(ChatColor.GREEN + message.toString());
		}
		
		public void error(Object message) {
			send(ChatColor.RED + message.toString());
		}

	}
	
	public abstract boolean onCommand(Context context, String[] args);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		Context ctx = new Context(sender, cmd, lbl); 
		return onCommand(ctx, args);
	}
	
}
