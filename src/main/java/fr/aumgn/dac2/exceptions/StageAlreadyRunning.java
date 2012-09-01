package fr.aumgn.dac2.exceptions;

import fr.aumgn.bukkitutils.localization.PluginMessages;

/**
 * Thrown when trying to start a new stage for an arena which already has one.
 */
public class StageAlreadyRunning extends DACException {

    private static final long serialVersionUID = -403011438737982028L;

    public StageAlreadyRunning(PluginMessages messages) {
        super(messages.get("stage.alreadyrunning"));
    }
}
