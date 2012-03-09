package fr.aumgn.dac.api.game.mode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DACGameMode {

    public String name();

    public String[] aliases() default {};

    /**
     * Indicates if the {@link GameMode} should be added to a 
     * new arena as an available game mode.
     */
    public boolean isDefault() default true;

    /**
     * Gets the required amount of players to start a game with this mode. 
     */
    public int minPlayers() default 2;

    /**
     * Indicates if the game mode allows any player to fill the pool
     * even while in game.
     */
    public boolean allowFill() default false;

}
