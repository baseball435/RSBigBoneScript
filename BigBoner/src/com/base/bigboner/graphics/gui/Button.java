package com.base.bigboner.graphics.gui;

import java.awt.Graphics2D;

import com.base.bigboner.BigBonerScript;

public class Button {
	/** The position and dimension variables. */
	protected int x, y, w, h;
	
	/** Sets the position and dimension variables. */
	public Button(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	/** @return Whether the click coordinates are within the bounds.
	 * 
	 * @param mx The clicked mouse x
	 * @param my The clicked mouse y
	 * @return Whether it was clicked
	 */
	public boolean clicked(int mx, int my) {
		return mx >= x && mx <= x + w && my >= y && my <= y + h;
	}
	
	/** Draws the background of the button. */
	public void draw(Graphics2D g2d) {
		g2d.setColor(BigBonerScript.GRAY_COLOR);
		g2d.fillRect(x, y, w, h);
		g2d.setColor(BigBonerScript.MIDDLE_GRAY_COLOR);
		g2d.fillRect(x + 5, y + 5, w - 10, h - 10);
	}
	
}
