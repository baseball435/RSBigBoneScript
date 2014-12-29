package com.base.granitecutter.task.tasks;

import org.powerbot.script.rt6.ClientContext;

import com.base.granitecutter.Config;
import com.base.granitecutter.GraniteCutterScript;
import com.base.granitecutter.task.Task;

public class TakeGraniteTask extends Task {

	public static boolean cutting2Kg = false;
	
	private int take = 0;
	
	public TakeGraniteTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.bank.opened() && ctx.backpack.select().count() == 0;
	}

	@Override
	public void execute() {
		if (ctx.bank.select().id(TaskManager.GRANITE_5KG_ID).count() > 0 && (ctx.bank.select().id(TaskManager.GRANITE_2KG_ID).count() == 0 || !Config.CUT_GRANITE_2KG)) {
			ctx.game.logout(true);
			GraniteCutterScript.instance.stop();
		} else if (ctx.bank.select().id(TaskManager.GRANITE_5KG_ID).count() > 0 && take % 2 == 0) {
			ctx.bank.withdraw(TaskManager.GRANITE_5KG_ID, 7);
			cutting2Kg = false;
		} else if ((Config.CUT_GRANITE_2KG && take % 2 == 1 && ctx.bank.select().id(TaskManager.GRANITE_2KG_ID).count() > 0) || ctx.bank.select().id(TaskManager.GRANITE_5KG_ID).count() == 0) {
			ctx.bank.withdraw(TaskManager.GRANITE_2KG_ID, 7);
			cutting2Kg = true;
		}
		take++;
		ctx.bank.close();
		
	}
	
}
