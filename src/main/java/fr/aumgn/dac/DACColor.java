package fr.aumgn.dac;

import org.bukkit.ChatColor;

public enum DACColor {
	

	VIOLET (ChatColor.LIGHT_PURPLE, 2),
	ORANGE (ChatColor.GOLD, 1),
	JAUNE (ChatColor.YELLOW, 4),
	VERT (ChatColor.GREEN, 5),
	ROUGE (ChatColor.DARK_RED, 14),
	ROSE (ChatColor.LIGHT_PURPLE, 6),
	BLANC (ChatColor.WHITE, 0),
	GRIS (ChatColor.DARK_GRAY, 8),
	VIOLET_FONCE (ChatColor.DARK_PURPLE, 10),
	VERT_FONCE (ChatColor.DARK_GREEN, 13),
	NOIR (ChatColor.BLACK, 15);
	
	private ChatColor chatColor;
	private byte woolColor;

	private DACColor(ChatColor chatColor, int woolColor) {
		this.chatColor = chatColor;
		this.woolColor = (byte)woolColor;
		
	}
	
	public ChatColor getChatColor() {
		return chatColor;
	}

	public byte getWoolColor() {
		return woolColor;
	}
	
	public static DACColor get(String name) {
		try {
			return valueOf(name.toUpperCase());
		} catch (IllegalArgumentException exc) {
			return null;
		}
	}

}
