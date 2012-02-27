package fr.aumgn.dac.api.game.messages;

import fr.aumgn.dac.api.stage.StagePlayer;

public class PlayerMessage implements GameMessage {

    private StagePlayer player;
    private String contents;

    public PlayerMessage(StagePlayer player, GameMessageContent message, Object... args) {
        this(player, args.length > 0 ? message.getContent(args) : message.getContent());
    }

    public PlayerMessage(StagePlayer player, String contents) {
        this.player = player;
        this.contents = contents;
    }

    @Override
    public void send() {
        player.send(contents);
    }

}
