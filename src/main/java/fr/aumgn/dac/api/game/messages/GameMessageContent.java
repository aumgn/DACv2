package fr.aumgn.dac.api.game.messages;

public interface GameMessageContent {

    String getContent();

    String getContent(Object... args);

}
