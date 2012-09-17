package fr.aumgn.dac.api.game.messages;

import fr.aumgn.dac.api.game.Game;

public class GeneralMessage implements GameMessage {

    private Game game;
    private String contents;

    public GeneralMessage(Game game, GameMessageContent message, Object... args) {
        this(game, args.length > 0 ? message.getContent(args) : message.getContent());
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
