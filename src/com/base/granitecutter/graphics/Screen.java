package com.base.granitecutter.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import org.powerbot.script.rt6.ClientContext;

public abstract class Screen {
	
	/** Instance of the context. */
	protected ClientContext ctx;
	
	/** Creates a new screen and sets the context instance.
	 * 
	 * @param ctx Reference to the context
	 */
	public Screen(ClientContext ctx) {
		this.ctx = ctx;
	}
	
	/** Called when set to display. */
	public abstract void create();
	
	/** Called each frame. */
	public abstract void update();
	
	/** Called each frame to draw to the screen. */
	public abstract void draw(Graphics2D g2d);
	
	/** Called when another screen is set. */
	public abstract void destroy();
	
}