package fr.aumgn.dac;

import com.sk89q.worldedit.regions.Region;

public class DACException {

	public static class WorldEditNotLoaded extends RuntimeException {
		private static final long serialVersionUID = 1L;
		
		public WorldEditNotLoaded() {
			super("Fail ! WorldEdit is not loaded !");
		}
		
	}
	
	public static class InvalidColorsConfig extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public InvalidColorsConfig() {
			super("Invalid colors configuration.");
		}
		
	}
	
	public static class InvalidRegionType extends RuntimeException {
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

}
