package fr.aumgn.dac.api.exception;

public class StageAlreadyRegistered extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StageAlreadyRegistered() {
        super("Tried to register a stage which is already registered.");
    }

}
