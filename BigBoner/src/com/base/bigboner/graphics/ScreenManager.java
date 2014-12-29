package com.base.bigboner.graphics;

import java.awt.Graphics2D;

public class ScreenManager {

	/** Singleton instance. */
	private static final ScreenManager instance = new ScreenManager();
	
	/** Current screen showing. */
	private Screen currentScreen;
	
	/** Private constructor. */
	private ScreenManager() {
	}
	
	/** Updates the current screen. */
	public void update() {
		if (currentScreen != null)
			currentScreen.update();
	}
	
	/** Draws the current screen. 
	 * 
	 * @param g2d The graphics object
	 */
	public void draw(Graphics2D g2d) {
		if (currentScreen != null)
			currentScreen.draw(g2d);
	}
	
	/** Sets the current screen to a new screen.
	 * 
	 * @param screen The new screen to display
	 */
	public void setScreen(Screen screen) {
		if (currentScreen != null)
			currentScreen.destroy();
		currentScreen = screen;
		currentScreen.create();
	}
	
	/** Returns the singleton instance. */
	public static ScreenManager getInstance() {
		return instance;
	}
	
}
