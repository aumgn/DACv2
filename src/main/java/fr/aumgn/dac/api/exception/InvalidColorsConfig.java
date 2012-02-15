package fr.aumgn.dac.api.exception;

public class InvalidColorsConfig extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidColorsConfig() {
		super("Invalid colors configuration.");
	}

}