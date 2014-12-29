package com.base.granitecutter.graphics.gui;

import java.awt.Color;
import java.awt.Graphics2D;

public class Rect implements IGraphicsComponent {

	private int x, y, w, h;
	private Color color, outlineColor;
	
	public Rect(int x, int y, int w, int h, Color color) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.color = color;
	}
	
	public void setOutlineColor(Color color) {
		outlineColor = color;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fillRect(x, y, w, h);
		if (outlineColor != null) {
			g2d.setColor(outlineColor);
			g2d.drawRect(x, y, w, h);
		}
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
