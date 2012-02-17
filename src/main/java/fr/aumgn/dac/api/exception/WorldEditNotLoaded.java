package fr.aumgn.dac.api.exception;

public class WorldEditNotLoaded extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public WorldEditNotLoaded() {
        super("Fail ! WorldEdit is not loaded !");
    }

}
