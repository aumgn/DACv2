package fr.aumgn.dac2.game.classic;

import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.bukkitutils.playerid.PlayerId;
import fr.aumgn.dac2.game.GamePlayer;
import fr.aumgn.dac2.game.start.PlayerStartData;

public class ClassicGamePlayer extends GamePlayer {

    private int lives;
    private Vector2D deathPosition;

    public ClassicGamePlayer(PlayerId playerId, PlayerStartData playerData) {
        super(playerId, playerData);
        this.lives = 0;
        this.deathPosition = null;
    }

    public int getLives() {
        return lives;
    }

    public Vector2D getDeathPosition() {
        return deathPosition;
    }

    public void incrementLives() {
        lives++;
    }

    public boolean isDead() {
        return lives < 0;
    }

    public void onFail(Vector2D deathPosition) {
        lives--;
        if (isDead()) {
            this.deathPosition = deathPosition;
        }
    }
}
