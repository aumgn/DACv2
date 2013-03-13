package fr.aumgn.dac2.game.classic;

import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.game.GamePlayer;
import fr.aumgn.dac2.stage.StagePlayer;

public class ClassicGamePlayer extends GamePlayer {

    public static class Factory
            implements GamePlayer.Factory<ClassicGamePlayer> {

        @Override
        public Class<ClassicGamePlayer> getSubclass() {
            return ClassicGamePlayer.class;
        }

        @Override
        public ClassicGamePlayer create(StagePlayer player, int index) {
            return new ClassicGamePlayer(player, index);
        }
    }

    private int lives;
    private Vector2D deathPosition;

    public ClassicGamePlayer(StagePlayer player, int index) {
        super(player, index);
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
