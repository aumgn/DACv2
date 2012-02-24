package fr.aumgn.dac.api.game.messages;

import fr.aumgn.dac.api.DACMessage;
import fr.aumgn.dac.api.game.Game;

public class GeneralMessage implements GameMessage {

	private Game game;
	private String contents;

	public GeneralMessage(Game game, DACMessage message, Object... args) {
		this(game, (args.length == 0) ? message.getValue() : message.getValue(args));
	}
	
	public GeneralMessage(Game game, String contents) {
		this.game = game;
		this.contents = contents;
	}
	
	@Override
	public void send() {
		game.send(contents);
	}

}
