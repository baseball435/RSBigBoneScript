package com.base.granitecutter.graphics.gui;

import java.awt.Graphics2D;

public interface IGraphicsComponent {

	void draw(Graphics2D g2d);
	
	int getX();
	
	int getY();
	
	int getWidth();
	
	int getHeight();
	
	void setX(int x);
	
	void setY(int y);
	
	void addX(int x);
	
	void addY(int y);
	
}
