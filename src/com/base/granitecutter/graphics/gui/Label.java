package com.base.granitecutter.graphics.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Label implements IGraphicsComponent {

	private String str;
	private int x, y, w, h;
	private Font font;
	private IChangingVariable iVar;
	private Color color;
	
	public Label(String str, int x, int y) {
		this.str = str;
		this.x = x;
		this.y = y;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setChangingVariable(IChangingVariable iVar) {
		this.iVar = iVar;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		if (font != null)
			g2d.setFont(font);
		if (color != null)
			g2d.setColor(color);
		w = g2d.getFontMetrics().stringWidth(str);
		h = g2d.getFontMetrics().getHeight();
		g2d.drawString(str + (iVar != null ? iVar.getVariable() : ""), x, y);
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
