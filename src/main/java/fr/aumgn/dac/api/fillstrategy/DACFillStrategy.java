package fr.aumgn.dac.api.fillstrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DACFillStrategy {

    /**
     * Name of the strategy.
     *  
     * @return the strategy name
     */
    public String name();

    /**
     * Aliases of the strategy.
     * 
     * @return the aliases of the strategy
     */
    public String[] aliases() default {};

}
