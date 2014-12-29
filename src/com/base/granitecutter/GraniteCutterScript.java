package com.base.granitecutter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GeItem;
import org.powerbot.script.rt6.GeItem.PriceType;

import com.base.granitecutter.graphics.ScreenManager;
import com.base.granitecutter.graphics.screens.MainScreen;
import com.base.granitecutter.task.Task;
import com.base.granitecutter.task.tasks.Inv2kgCheck;
import com.base.granitecutter.task.tasks.Inv5kgCheck;
import com.base.granitecutter.task.tasks.TaskManager;

@Script.Manifest(name="Base's Granite Cutter", description="Cuts Granite and makes 1 Mil/Hour.", properties="topic=1238385" )
public class GraniteCutterScript extends PollingScript<ClientContext> implements PaintListener  {
	
	//http://prntscr.com/5kpkzh
	//http://prntscr.com/5kz2he
	//http://prntscr.com/5kz8ed
	
	//http://prntscr.com/5ldksz
	//http://prntscr.com/5m4t0n
	
	public static GraniteCutterScript instance;
	public static int FIVEKG_PRICE, TWOKG_PRICE, G_PRICE;
	
	/** The different colors and fonts used in the GUI */
	public static final Font BIG_FONT = new Font("Candara", Font.BOLD, 20), NORMAL_FONT = new Font("Candara", Font.BOLD, 16), SMALL_FONT = new Font("Candara", Font.BOLD, 14); 
	
	/** Holds all tasks. */
	private ArrayList<Task> tasks = new ArrayList<Task>();
	
	@Override
	public void start() {
		instance = this;
		ScreenManager.getInstance().setScreen(new MainScreen(ctx)); //Set to the main screen
		tasks.addAll(Arrays.asList(new TaskManager(ctx), new Inv5kgCheck(ctx), new Inv2kgCheck(ctx)));

		FIVEKG_PRICE = GeItem.profile(TaskManager.GRANITE_5KG_ID).price(PriceType.CURRENT).price();
		TWOKG_PRICE = GeItem.profile(TaskManager.GRANITE_2KG_ID).price(PriceType.CURRENT).price();
		G_PRICE = GeItem.profile(TaskManager.GRANITE_500G_ID).price(PriceType.CURRENT).price();
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
	
	public double getProfit() {
		double profit = -FIVEKG_PRICE;
		profit += TWOKG_PRICE * 2;
		profit += G_PRICE * 2;

		profit *= Inv5kgCheck.granite5kgCut;
		
		profit += Inv2kgCheck.granite2kgCut * (G_PRICE * 4);
		
		if (Inv2kgCheck.granite2kgCut <= Inv5kgCheck.granite5kgCut * 2) {
			profit -= Inv2kgCheck.granite2kgCut * -TWOKG_PRICE;
		}
		//System.out.println(profit);
		return profit;
	}

}