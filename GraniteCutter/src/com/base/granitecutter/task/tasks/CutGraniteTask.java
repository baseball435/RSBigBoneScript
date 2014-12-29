package com.base.granitecutter.task.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.Hud.Window;
import org.powerbot.script.rt6.Item;

import com.base.granitecutter.Config;
import com.base.granitecutter.task.DelayedTask;
import com.base.granitecutter.util.AntiPattern;

public class CutGraniteTask extends DelayedTask {
	
	private long startCraftingTime;
	
	public CutGraniteTask(ClientContext ctx) {
		super(ctx, 1500);
	}

	@Override
	public boolean activate() {
		return passesTime(Random.nextInt(0, 500)) && !ctx.bank.opened() && ((!TakeGraniteTask.cutting2Kg && ctx.backpack.select().id(TaskManager.GRANITE_5KG_ID).count() <= 7 && ctx.backpack.select().id(TaskManager.GRANITE_5KG_ID).count() > 0) || (TakeGraniteTask.cutting2Kg && ctx.backpack.select().id(TaskManager.GRANITE_2KG_ID).count() <= 7 && ctx.backpack.select().id(TaskManager.GRANITE_2KG_ID).count() > 0)) && ctx.players.local().animation() == -1;
	}

	@Override
	public void execute() {
		if (!checkWidget()) {
			if (System.currentTimeMillis() - startCraftingTime >= 12000 + Random.nextInt(0, 1000)) {
				Item granite = null;
				if (!ctx.backpack.select().id(TaskManager.GRANITE_5KG_ID).isEmpty()) {
					granite = ctx.backpack.shuffle().poll();
				} else if (TakeGraniteTask.cutting2Kg && !ctx.backpack.select().id(TaskManager.GRANITE_2KG_ID).isEmpty()) {
					granite = ctx.backpack.shuffle().poll();
				}
				if (granite != null) {
					if (!ctx.hud.opened(Window.BACKPACK)) //Open the backpack if its not
						ctx.hud.open(Window.BACKPACK);
					Condition.wait(new Callable<Boolean>() {
		                @Override
		                public Boolean call() throws Exception {
		                    return false;
		                }
		            }, 100, 10);
					granite.click();
					checkWidget();
				}
			}
		}
	}

	private boolean checkWidget() {
		if (!ctx.widgets.select().id(1371).isEmpty()) {
			Component c = ctx.widgets.peek().component(5);
			if (c.valid()) {
				c.click();
				System.out.println("Checking widget");
				Condition.wait(new Callable<Boolean>() {
	                @Override
	                public Boolean call() throws Exception {
	                    return ctx.widgets.select().id(1371).isEmpty();
	                }
	            }, 100, 30);
				startCraftingTime = System.currentTimeMillis();
				
				if (Config.HOVER_PLAYERS && Random.nextInt(0, 100) <= 10) {
					AntiPattern.hoverOverPlayer(ctx);
				} else if (Config.MOVE_MOUSE_OFF_SCREEN && Random.nextInt(0, 100) < 40) {
					AntiPattern.moveMouseOffScreen(ctx);
				}
				return true;
			}
		}
		return false;
	}
	
}
