package fr.aumgn.dac.api.exception;

import com.sk89q.worldedit.regions.Region;

public class InvalidRegionType extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidRegionType(String message) {
		super(message);
	}

	public InvalidRegionType(String expected1, String expected2, String given) {
		this("Expected " + expected1 + " or " + expected2 + " got " + given);
	}

	public InvalidRegionType(String expected1, String expected2, Class<? extends Region> given) {
		this(expected1, expected2, given.getSimpleName());
	}

}
