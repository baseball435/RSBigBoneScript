package com.base.bigboner.task.tasks;

import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Hud.Window;

import com.base.bigboner.task.DelayedTask;

public class BuryBones extends DelayedTask {

	public BuryBones(ClientContext ctx) {
		super(ctx, 200);
	}

	/** If there are bones left. */
	@Override
	public boolean activate() {
		return !ctx.backpack.id(PickUpBones.BONE_ID).isEmpty();
	}


	@Override
	public void execute() {
		if (passesTime(Random.nextInt(0, 300))) { //if the time is up
			if (!ctx.hud.opened(Window.BACKPACK)) //Open the backpack if its not
				ctx.hud.open(Window.BACKPACK);
			ctx.backpack.select().id(PickUpBones.BONE_ID).poll().interact("Bury"); //bury the first bone
		}
	}
	
}
