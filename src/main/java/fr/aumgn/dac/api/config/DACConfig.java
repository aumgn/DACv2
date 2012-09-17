package fr.aumgn.dac.api.config;

/**
 * Represents user defined configuration.  
 */
public interface DACConfig {

    boolean getResetOnStart();

    boolean getResetOnEnd();

    boolean getCancelJumpDamage();

    boolean getPutColumn(); 
    
    boolean getTpBeforeJump();
    
    boolean getTpAfterJump();

    boolean getTpAfterFail();

    int getTpAfterFailDelay();

    int getSafeRegionHeight();

    int getSafeRegionMargin();

    int getTurnTimeOut();
    
    boolean getPrefixSpectatorsMessages();

    String getDeathSignFirstLine();
}