package fr.aumgn.dac.api.game.messages;

import fr.aumgn.dac.api.stage.StagePlayer;

public class OthersMessage implements GameMessage {

    private StagePlayer player;
    private String contents;

    public OthersMessage(StagePlayer player, GameMessageContent message, Object... args) {
        this(player, args.length > 0 ? message.getContent(args) : message.getContent());
    }

    public OthersMessage(StagePlayer player, String contents) {
        this.player = player;
        this.contents = contents;
    }

    @Override
    public void send() {
        for (StagePlayer stagePlayer : player.getStage().getPlayers()) {
            if (!player.getPlayer().equals(stagePlayer.getPlayer())) {
                stagePlayer.send(contents);
            }
        }
    }

}
