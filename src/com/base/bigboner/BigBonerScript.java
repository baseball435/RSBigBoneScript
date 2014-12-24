package com.base.bigboner;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;

import com.base.bigboner.graphics.ScreenManager;
import com.base.bigboner.graphics.screens.MainScreen;
import com.base.bigboner.task.Task;
import com.base.bigboner.task.tasks.AntiBan;
import com.base.bigboner.task.tasks.FullInv;
import com.base.bigboner.task.tasks.InvCheck;
import com.base.bigboner.task.tasks.PickupManager;
import com.base.bigboner.task.tasks.WalkingTask;

@Script.Manifest(name="BigBoner", description="Picks up and buries or banks bones.")
public class BigBonerScript extends PollingScript<ClientContext> implements PaintListener  {
	
	/** The different colors and fonts used in the GUI */
	public static final Color GRAY_COLOR = new Color(98, 98, 98), MIDDLE_GRAY_COLOR = new Color(110, 110, 110), LIGHT_GRAY_COLOR = new Color(128, 128, 128);
	public static final Font BIG_FONT = new Font("Candara", Font.BOLD, 20), NORMAL_FONT = new Font("Candara", Font.BOLD, 16), SMALL_FONT = new Font("Candara", Font.BOLD, 14); 
	
	/** Brass Key ID */
	private static final int KEY_ID = 983;
	
	/** Start time, didnt use built in getRuntime() because I needed it to be static */
	private static long startTime;
	
	/** Holds all tasks. */
	private ArrayList<Task> tasks = new ArrayList<Task>();
	
	@Override
	public void start() {
		startTime = System.currentTimeMillis();
		if (ctx.backpack.select().id(KEY_ID).isEmpty()) { //If the brass key isnt found then stop
			JOptionPane.showMessageDialog(null, "Please make sure you have a \"Brass Key\" in your inventory then restart.");
			this.stop();
		} else {
			ScreenManager.getInstance().setScreen(new MainScreen(ctx)); //Set to the main screen
			tasks.addAll(Arrays.asList(new WalkingTask(ctx), new PickupManager(ctx), new AntiBan(ctx), new FullInv(ctx), new InvCheck(ctx)));
		}
	}
	
	/** Executes all tasks. */
	@Override
	public void poll() {
		for (Task t : tasks)
			if (t.activate())
				t.execute();
	}

	/** Paints the mouse coordinates and the screens. */
	@Override
	public void repaint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		ScreenManager.getInstance().update();
		ScreenManager.getInstance().draw(g2d);
		g2d.setColor(Color.WHITE);
		g2d.drawLine(0, ctx.mouse.getLocation().y, ctx.client().getCanvas().getWidth(), ctx.mouse.getLocation().y);
		g2d.drawLine(ctx.mouse.getLocation().x, 0, ctx.mouse.getLocation().x, ctx.client().getCanvas().getHeight());

	}
	
	/** Static method to get the runtime. */
	public static long getScriptRuntime() {
		return System.currentTimeMillis() - startTime;
	}

}