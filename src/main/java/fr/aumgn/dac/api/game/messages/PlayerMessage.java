package fr.aumgn.dac.api.game.messages;

import fr.aumgn.dac.api.DACMessage;
import fr.aumgn.dac.api.stage.StagePlayer;

public class PlayerMessage implements GameMessage {

    private StagePlayer player;
    private String contents;

    public PlayerMessage(StagePlayer player, DACMessage message, Object... args) {
        this(player, args.length > 0 ? message.getValue(args) : message.getValue());
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
