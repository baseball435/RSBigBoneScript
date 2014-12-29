package com.base.bigboner.graphics.gui;

import java.awt.Color;
import java.awt.Graphics2D;

public class TextButton extends Button{
	
	/** Holds a string to draw on the button. */
	private String str;
	
	/** Sets the position, dimension, and string instance variables.
	 * 
	 * @param str The string to draw
	 * @param x The x position
	 * @param y The y position
	 * @param w The width
	 * @param h The height
	 */
	public TextButton(String str, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.str = str;
	}
	
	/** Draws the string in the center of the button. */
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		g2d.setColor(Color.WHITE);
		int width = g2d.getFontMetrics().stringWidth(str);
		int height = g2d.getFontMetrics().getHeight();
		g2d.drawString(str, x + (w / 2) - (width / 2), y + (h / 2) + (height / 2) - 5);
	}
	
}
