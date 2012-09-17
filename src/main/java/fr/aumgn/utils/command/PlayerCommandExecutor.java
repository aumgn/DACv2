package fr.aumgn.utils.command;

public abstract class PlayerCommandExecutor extends BasicCommandExecutor {

	public abstract boolean onPlayerCommand(Context context, String[] args);

	@Override
	public boolean onCommand(Context context, String[] args) {
		if (context.isPlayerCommand()) {
			return onPlayerCommand(context, args);
		} else {
			context.getSender().sendMessage("This command can only be used in a player context.");
			return true;
		}
	}

}
