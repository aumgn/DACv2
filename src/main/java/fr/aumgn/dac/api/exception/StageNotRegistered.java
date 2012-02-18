package fr.aumgn.dac.api.exception;

public class StageNotRegistered extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public StageNotRegistered() {
        super("Tried to unregister a stage which is not registered.");
    }

}
