package com.base.granitecutter.graphics.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.base.granitecutter.GraniteCutterScript;

public class Panel extends Button implements IGraphicsComponent {

	private static final Color DARK_GRAY = new Color(98, 98, 98), LIGHT_GRAY = new Color(128, 128, 128);
	private static final int PANEL_HEIGHT = 30, SPEED = 25;
	
	private ArrayList<IGraphicsComponent> components = new ArrayList<IGraphicsComponent>();
	
	private int currentHeight = 0;
	private String name;
	private boolean moveDown, moveUp;
	private Rect panelHeader = new Rect(x, y, w, PANEL_HEIGHT, DARK_GRAY);
	
	public Panel(String name, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.name = name;
		panelHeader.setOutlineColor(Color.BLACK);
	}
	
	public void addComponent(IGraphicsComponent comp) {
		components.add(comp);
		comp.addX(x);
		comp.addY(y + PANEL_HEIGHT);
	}
	
	public void update() {
		if (moveDown) {
			if (currentHeight < h) {
				if (currentHeight + SPEED < h)
					currentHeight += SPEED;
				else
					currentHeight = h;
			} else if (currentHeight >= h) {
				currentHeight = h;
				moveDown = false;
			}
		} else if (moveUp) {
			if (currentHeight > 0) {
				if (currentHeight + SPEED > 0)
					currentHeight -= SPEED;
				else currentHeight = 0;
			} else if (currentHeight <= 0) {
				currentHeight = 0;
				moveUp = false;
			}
		}
	}
	
	@Override
	public boolean clicked(int mx, int my) {
		boolean b = mx >= x && mx <= x + w && my >= y && my <= y + PANEL_HEIGHT;
		
		if (b) {
			if (currentHeight == 0) {
				moveDown = true;
				moveUp = false;
			} else {
				moveUp = true;
				moveDown = false;
			}
		}
		if (mx >= x && mx <= x + w && my >= y + PANEL_HEIGHT && my <= y + PANEL_HEIGHT + currentHeight) {
			for (IGraphicsComponent com : components) {
				if (com instanceof Button) {
					((Button)com).clicked(mx, my);
				}
			}
		}
		return b;
	}

	@Override
	public void draw(Graphics2D g2d) {
		panelHeader.draw(g2d);
		g2d.setFont(GraniteCutterScript.NORMAL_FONT);
		g2d.setColor(Color.WHITE);
		int width = g2d.getFontMetrics().stringWidth(name);
		int height = g2d.getFontMetrics().getHeight();
		g2d.drawString(name, x + (w / 2) - (width / 2), y + (PANEL_HEIGHT / 2) + (height / 2) - 5);
		
		String character = currentHeight > 0 ? "-" : "+";
		width = g2d.getFontMetrics().stringWidth(character);
		g2d.drawString(character, x + w - 10 - width, y + (PANEL_HEIGHT / 2) + (height / 2) - 5);
		
		//Draw content
		if (currentHeight > 0) {
			g2d.setColor(LIGHT_GRAY);
			g2d.fillRect(x, y + PANEL_HEIGHT, w, currentHeight);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(x, y + PANEL_HEIGHT, w, currentHeight);
			
			for (IGraphicsComponent com : components) {
				if (com.getY() + com.getHeight() - PANEL_HEIGHT <= currentHeight) {
					com.draw(g2d);
				}
			}
		}
	}
	
}
