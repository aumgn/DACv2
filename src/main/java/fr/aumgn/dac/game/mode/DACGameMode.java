package fr.aumgn.dac.game.mode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DACGameMode {

	/**
	* Name of your mode
	*
	* @return Name of the game mode.
	*/
	public String name();
	
	public int minPlayers() default 2;
	
	public boolean allowPoolReset() default false;
	
}
