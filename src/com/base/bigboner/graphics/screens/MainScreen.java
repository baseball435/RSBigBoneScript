package com.base.bigboner.graphics.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.powerbot.script.rt6.ClientContext;

import com.base.bigboner.BigBonerScript;
import com.base.bigboner.graphics.Screen;
import com.base.bigboner.graphics.ScreenManager;
import com.base.bigboner.graphics.gui.Button;
import com.base.bigboner.graphics.gui.TextButton;
import com.base.bigboner.task.tasks.InvCheck;

public class MainScreen extends Screen implements MouseListener {
	
	/** Button that when pressed goes to the settings screen. */
	private Button settingsBtn = new TextButton("Settings", 300, 95, 150, 50);
	
	public MainScreen(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void create() {
		//Adds the mouse listener to the canvas
		ctx.client().getCanvas().addMouseListener(this);
	}

	@Override
	public void update() {
		
	}
	
	/** Called whenever the mouse is clicked.
	 * 
	 * @param x The mouse x
	 * @param y The mouse y
	 */
	private void checkInput(int x, int y) {
		if (settingsBtn.clicked(x, y)) { //If the settings button is clicked
			ScreenManager.getInstance().setScreen(new SettingsScreen(ctx)); //Go to the settings screen
		}
	}

	/** Draws the screen and information. */
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(BigBonerScript.GRAY_COLOR);
		g2d.fillRect(10, 10, 500, 175);
		g2d.setColor(BigBonerScript.LIGHT_GRAY_COLOR);
		g2d.fillRect(15, 15, 490, 165);

		g2d.setColor(BigBonerScript.GRAY_COLOR);
		g2d.fillRect(160, 15, 197, 50);
		
		g2d.setFont(BigBonerScript.BIG_FONT);
		g2d.setColor(Color.WHITE);
		g2d.drawString("Big Boner Version 1.0", 170, 35);
		
		g2d.setFont(BigBonerScript.SMALL_FONT);
		g2d.drawString("Developed by Baseball435", 180, 50);
		
		g2d.setFont(BigBonerScript.NORMAL_FONT);
		if (BigBonerScript.getScriptRuntime() > 0) {
			int toSeconds = (int)BigBonerScript.getScriptRuntime() / 1000;
			int minutes = toSeconds / 60;
			int hours = minutes / 60;
			int seconds = toSeconds % 60;
			
			g2d.drawString("Time Running: " + hours + ":" + minutes + ":" + seconds, 35, 100);
			g2d.drawString("Bones Gathered: " + InvCheck.bonesPickedUp, 35, 150);
		}
		settingsBtn.draw(g2d);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		checkInput(e.getX(), e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void destroy() {
		ctx.client().getCanvas().removeMouseListener(this);
	}
	
}