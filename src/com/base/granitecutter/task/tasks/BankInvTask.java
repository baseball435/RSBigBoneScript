package com.base.granitecutter.task.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;

import com.base.granitecutter.task.Task;

public class BankInvTask extends Task {

	private static final int[] BANKER_NPCS = new int[] { 3293, 3416, 2718, 3418 };
	
	public BankInvTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().count() == 28 || (ctx.backpack.select().id(TaskManager.GRANITE_5KG_ID).count() == 0 && !TakeGraniteTask.cutting2Kg) || (ctx.backpack.select().id(TaskManager.GRANITE_2KG_ID).count() == 0 && TakeGraniteTask.cutting2Kg);
	}

	@Override
	public void execute() {	
		if (!ctx.bank.opened()) {
			if (!ctx.npcs.select().id(BANKER_NPCS).isEmpty()) {
				Npc banker = ctx.npcs.nearest().poll();
				if (banker.inViewport()) {
					banker.interact("Bank");
					Condition.wait(new Callable<Boolean>() {
		                @Override
		                public Boolean call() throws Exception {
		                    return ctx.bank.opened();
		                }
		            }, 100, 10);
				} else {
					ctx.movement.step(banker);
					if (Random.nextInt(0, 100) < 50)
						ctx.camera.turnTo(banker);
				}
			}
		} else {
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.bank.depositInventory();
				}
				
			}, 100, 10);
		}
	}
}
