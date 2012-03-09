package fr.aumgn.dac.plugin.mode.classic;

import com.sk89q.worldedit.Vector;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.SimpleGamePlayer;
import fr.aumgn.dac.api.stage.StagePlayer;

public class ClassicGamePlayer extends SimpleGamePlayer {

    private int lives;
    private boolean mustConfirmate;
    private Vector deathPosition;

    public ClassicGamePlayer(Game game, StagePlayer player) {
        super(game, player);
        this.lives = game.getOptions().getLives();
        this.mustConfirmate = false;
    }

    @Override
    public String formatForList() {
        String livesF = (lives == -1) ? "En sursis" : lives + " vie(s)"; 
        return super.formatForList() + " : " + livesF;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isDead() {
        return lives == -1;
    }

    public Vector getDeathPosition() {
        return deathPosition;
    }

    public boolean mustConfirmate() {
        return mustConfirmate;
    }

    public void resetLives() {
        lives = 0;
    }

    public void winLive() {
        lives++;
    }

    public boolean looseLive() {
        lives--;
        return isDead();
    }

    public void setDeathPosition(Vector vector) {
        deathPosition = vector;
    }

    public void setMustConfirmate(boolean bool) {
        mustConfirmate = bool;
    }

}
