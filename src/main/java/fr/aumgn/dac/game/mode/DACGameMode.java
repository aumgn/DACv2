package fr.aumgn.dac.game.mode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DACGameMode {

	/**
	* Name of your type
	*
	* @return Name of the showcase type.
	*/
	public String value();
	
}
