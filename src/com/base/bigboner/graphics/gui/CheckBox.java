package com.base.bigboner.graphics.gui;

import java.awt.Color;
import java.awt.Graphics2D;

public class CheckBox extends Button {
	
	/** Whether or not it's checked. */
	private boolean checked = false;
	
	/** Sets the x and y coordinates.
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public CheckBox(int x, int y) {
		super(x, y, 30, 30);
	}
	
	/** If it is clicked then set whether it's checked to what it's not. */
	@Override
	public boolean clicked(int mx, int my) {
		boolean b = super.clicked(mx, my);
		if (b)
			checked = !checked;
		return b;
	}
	
	/** @return Whether it's checked. */
	public boolean checked() {
		return checked;
	}
	
	/** Sets whether it's checked.
	 * 
	 * @param b Whether it's checked.
	 */
	public void setChecked(boolean b) {
		checked = b;
	}
	
	/** Draws the checkbox and the check if it's checked. */
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		if (checked) {
			g2d.setColor(Color.GREEN);
			g2d.drawLine(x + 5, y + 5, x + w - 5, y + h - 5);
			g2d.drawLine(x + w - 5, y + 5, x + 5, y + h - 5);
		}
	}
	
}