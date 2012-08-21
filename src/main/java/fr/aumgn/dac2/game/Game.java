package fr.aumgn.dac2.game;

import org.bukkit.entity.Player;

import fr.aumgn.dac2.stage.Stage;

public interface Game extends Stage {

    void sendMessage(String message);

    boolean isPlayerTurn(Player player);

    void onJumpSuccess(Player player);

    void onJumpFail(Player player);

    void onQuit(Player player);
}
