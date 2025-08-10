package onthedocket.utils;

import java.awt.Color;

public class Theme {
	public static final Theme LIGHT = Theme.light();
	public static final Theme DARK = Theme.dark();
	
	private Color backgroundColor;
	private Color secondaryColor;
	private Color accentColor;
	private Color textColor;
	private Color textColor2;
	
	private Theme() {}
	
	private static Theme light() {
		Theme theme = new Theme();
		theme.setBackgroundColor(Color.WHITE);
		theme.setSecondaryColor(Color.GRAY);
		theme.setAccentColor(Color.CYAN);
		theme.setTextColor(Color.BLACK);
		theme.setTextColor2(Color.BLUE);
		return theme;
	}
	
	private static Theme dark() {
		Theme theme = new Theme();
		theme.setBackgroundColor(Color.BLACK);
		theme.setSecondaryColor(Color.GRAY);
		theme.setAccentColor(Color.BLUE);
		theme.setTextColor(Color.WHITE);
		theme.setTextColor2(Color.LIGHT_GRAY);
		return theme;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getSecondaryColor() {
		return secondaryColor;
	}

	public void setSecondaryColor(Color secondaryColor) {
		this.secondaryColor = secondaryColor;
	}

	public Color getAccentColor() {
		return accentColor;
	}

	public void setAccentColor(Color accentColor) {
		this.accentColor = accentColor;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public Color getTextColor2() {
		return textColor2;
	}

	public void setTextColor2(Color textColor2) {
		this.textColor2 = textColor2;
	}
}
