package onthedocket.utils;

import java.awt.Color;

/**
 * Encapsulates a named collection of colors for styling the calendar UI.
 * A Theme defines background, secondary, accent, and text colors.
 * Instances are immutable and common presets (LIGHT, DARK, SUNRISE)
 * are provided for convenience.
 * 
 * @author Sitatunga147 (with moderate AI assistance)
 */
public class Theme {
	public static final Theme LIGHT = new Theme("Light", Color.WHITE, Color.GRAY, Color.CYAN, Color.BLACK, Color.BLUE);
	public static final Theme DARK = new Theme("Dark", Color.BLACK, Color.GRAY, Color.BLUE, Color.WHITE, Color.LIGHT_GRAY);
	public static final Theme SUNRISE = new Theme("Sunrise", new Color(0xFF8153), new Color(0xFCC5AF), new Color(0xFFE63B), new Color(0x000100), new Color(0x684A2E));
	
	private final String name;
	private final Color backgroundColor;
	private final Color secondaryColor;
	private final Color accentColor;
	private final Color primaryTextColor;
	private final Color secondaryTextColor;
	
	/**
     * Constructs a new Theme with the specified display name and color palette.
     *
     * @param name the name of this theme
     * @param backgroundColor the primary background color
     * @param secondaryColor the color used for secondary UI elements
     * @param accentColor the color used for accents and highlights
     * @param primaryTextColor the color used for main text
     * @param secondaryTextColor the color used for less prominent text
     */
	private Theme(String name, Color backgroundColor, Color secondaryColor, Color accentColor, Color primaryTextColor, Color secondaryTextColor) {
		this.name = name;
		this.backgroundColor = backgroundColor;
		this.secondaryColor = secondaryColor;
		this.accentColor = accentColor;
		this.primaryTextColor = primaryTextColor;
		this.secondaryTextColor = secondaryTextColor;
	}
	
	/**
     * Returns a string representation of this theme in the form "Theme[name]".
     *
     * @return a formatted name of this theme
     */
	@Override
	public String toString() {
		return "Theme[" + name + "]";
	}
	
	public String getName() {
		return name;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Color getSecondaryColor() {
		return secondaryColor;
	}

	public Color getAccentColor() {
		return accentColor;
	}

	public Color getPrimaryTextColor() {
		return primaryTextColor;
	}

	public Color getSecondaryTextColor() {
		return secondaryTextColor;
	}
}
