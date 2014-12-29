package com.base.bigboner.task.tasks;

import org.powerbot.script.rt6.ClientContext;

import com.base.bigboner.task.Task;

public class WalkToGiants extends Task {
	
	public WalkToGiants(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return true;
	}

	/** Move to a random position in it. */
	@Override
	public void execute() {
		ctx.movement.step(WalkingTask.GIANTS_AREA.getRandomTile());
	}
	
}