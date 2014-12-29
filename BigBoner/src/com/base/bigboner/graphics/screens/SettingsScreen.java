package com.base.bigboner.graphics.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.powerbot.script.rt6.ClientContext;

import com.base.bigboner.BigBonerScript;
import com.base.bigboner.Config;
import com.base.bigboner.graphics.Screen;
import com.base.bigboner.graphics.ScreenManager;
import com.base.bigboner.graphics.gui.Button;
import com.base.bigboner.graphics.gui.CheckBox;
import com.base.bigboner.graphics.gui.TextButton;

public class SettingsScreen extends Screen implements MouseListener {

	/** The back button to go to the main screen. */
	private Button backBtn = new TextButton("Back", 300, 95, 150, 50);
		
	/** Checkbox to bury bones instead of bank them. */
	private CheckBox buryChk = new CheckBox(150, 90);
	
	/** Checkbox to bank bones instead of bury them. */
	private CheckBox bankChk = new CheckBox(150, 140);
	
	/** Sets context and sets the checkboxes to the boolean value of the config.
	 * 
	 * @param ctx Context reference
	 */
	public SettingsScreen(ClientContext ctx) {
		super(ctx);
		bankChk.setChecked(Config.BANK_BONES);
		buryChk.setChecked(Config.BURY_BONES);
	}

	@Override
	public void create() {
		//Adds the mouse listener to the canvas
		ctx.client().getCanvas().addMouseListener(this);
	}

	@Override
	public void update() {
		
	}
	
	/** Called when the mouse is clicked. Checks if any buttons or checkboxes were pressed.
	 * 
	 * @param x The mouse x 
	 * @param y The mouse y
	 */
	private void checkInput(int x, int y) {
		if (backBtn.clicked(x, y)) {
			ScreenManager.getInstance().setScreen(new MainScreen(ctx)); //Change back to main screen
		} else if (bankChk.clicked(x, y)) { 
			if (bankChk.checked()) { //Set it to bank instead of bury
				buryChk.setChecked(false);
				Config.BANK_BONES = true;
				Config.BURY_BONES = false;
			} else
				bankChk.setChecked(true);
		} else if (buryChk.clicked(x, y)) {
			if (buryChk.checked()) { //Set it to bury instead of bank
				Config.BANK_BONES = false;
				Config.BURY_BONES = true;
				bankChk.setChecked(false);
			} else
				buryChk.setChecked(true);
		}
	}

	/** Draws the GUI. */
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
		g2d.drawString("Bury Bones: ", 45, 110);
		g2d.drawString("Bank Bones: ", 45, 160);
		backBtn.draw(g2d);
		buryChk.draw(g2d);
		bankChk.draw(g2d);
	}

	@Override
	public void destroy() {
		ctx.client().getCanvas().removeMouseListener(this);
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
	
}