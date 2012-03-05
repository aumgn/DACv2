package fr.aumgn.dac.api.config;

import org.bukkit.configuration.Configuration;

/**
 * Represents user defined configuration.  
 */
public interface DACConfig {

    /**
     * Loads dac configuration with the given {@link Configuration}
     * @param config the config to load with
     */
    void load(Configuration config);

    /**
     * Indicates if the pool must be reset at the start of a game.
     * 
     * @return whether the pool must be reset at the start
     */
    boolean getResetOnStart();

    /**
     * Indicates if the pool must be reset at the end of a game.
     * 
     * @return whether the pool must be reset at the end
     */
    boolean getResetOnEnd();
    
    /**
     * Indicates if jump damage should be cancelled.
     *  
     * @return whether to cancel jump damage and death.
     */
    boolean getCancelJumpDamage();

    /**
     * Gets safe region height.
     * 
     * @return the safe region height
     */
    int getSafeRegionHeight();
    
    /**
     * Gets safe region margin.
     * 
     * @return the safe region margin
     */
    int getSafeRegionMargin();
    
    /**
     * Indicates the number of ticks before a player turn is aborted.  
     * 
     * @return the number of ticks before a player turn is aborted
     */
    int getTurnTimeOut();
    
    /**
     * Indicates if a player must be teleported after a successful jump.
     * 
     * @return whether to teleport the player after his jump. 
     */
    boolean getTpAfterJump();

    /**
     * Indicates if the player must be teleported after a fail.
     * 
     * @return whether or not to teleport the player after a fail
     */
    boolean getTpAfterFail();

    /**
     * Indicates the number of ticks after which the player must be teleported when he fails.
     * 
     * @return the number of ticks after which the player must be teleported when he fails
     */
    int getTpAfterFailDelay();

    /**
     * Gets the maximum number of player allowed in a game. 
     * <p/>
     * This value is indicated by the number of colors available.
     * 
     * @return the maximum number of player allowed
     */
    int getMaxPlayers();

    /**
     * Gets the first line of the death signs.
     * 
     * @return the first line of the death signs
     */
    String getDeathSignFirstLine();

    /**
     * Gets available colors.
     * @see DACColors
     */
    DACColors getColors();

}