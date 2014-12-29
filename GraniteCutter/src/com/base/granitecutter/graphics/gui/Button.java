package com.base.granitecutter.graphics.gui;

import java.awt.Color;
import java.awt.Graphics2D;

public class Button implements IGraphicsComponent {
	
	private static final Color DARK_GRAY = new Color(98, 98, 98), LIGHT_GRAY = new Color(124, 124, 124);
	
	/** The position and dimension variables. */
	protected int x, y, w, h;
	protected IMouseClickAction action;
	
	/** Sets the position and dimension variables. */
	public Button(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void setMouseClickAction(IMouseClickAction action) {
		this.action = action;
	}
	
	/** @return Whether the click coordinates are within the bounds.
	 * 
	 * @param mx The clicked mouse x
	 * @param my The clicked mouse y
	 * @return Whether it was clicked
	 */
	public boolean clicked(int mx, int my) {
		boolean b = mx >= x && mx <= x + w && my >= y && my <= y + h;
		if (b && action != null)
			action.onMouseClick();
		return b;
	}
	
	/** Draws the background of the button. */
	public void draw(Graphics2D g2d) {
		g2d.setColor(LIGHT_GRAY);
		g2d.fillRect(x, y, w, h);
		g2d.setColor(DARK_GRAY);
		g2d.fillRect(x + 3, y + 3, w - 6, h - 6);
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getWidth() {
		return w;
	}

	@Override
	public int getHeight() {
		return h;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void addX(int x) {
		this.x += x;
	}

	@Override
	public void addY(int y) {
		this.y += y;
	}
	
}
