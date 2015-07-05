package fr.aumgn.dac2.exceptions;

import fr.aumgn.bukkitutils.localization.PluginMessages;

/**
 * Thrown when trying to start a new stage for an arena which already has one.
 */
public class StageAlreadyRunning extends DACException {

    public StageAlreadyRunning(PluginMessages messages) {
        super(messages.get("stage.alreadyrunning"));
    }
}
