package fr.aumgn.dac.api.game.mode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DACGameMode {

	/**
	* Name of the game mode
	*
	* @return Name of the game mode.
	*/
	public String name();
	
	public boolean isDefault() default true;
	
	public int minPlayers() default 2;
	
	public boolean allowFill() default false;
	
}
