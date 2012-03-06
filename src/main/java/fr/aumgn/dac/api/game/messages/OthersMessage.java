package fr.aumgn.dac.api.game.messages;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.stage.StagePlayer;

public class OthersMessage implements GameMessage {

    private Game game;
    private StagePlayer player;
    private String contents;

    public OthersMessage(Game game, StagePlayer player, GameMessageContent message, Object... args) {
        this(game, player, args.length > 0 ? message.getContent(args) : message.getContent());
    }

    public OthersMessage(Game game, StagePlayer player, String contents) {
        this.game = game;
        this.player = player;
        this.contents = contents;
    }

    @Override
    public void send() {
        for (StagePlayer stagePlayer : game.getPlayers()) {
            if (!player.getPlayer().equals(stagePlayer.getPlayer())) {
                stagePlayer.send(contents);
            }
        }
        game.sendToSpectators(contents);
    }

}
