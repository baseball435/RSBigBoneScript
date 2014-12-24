package com.base.bigboner.task.tasks;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

import com.base.bigboner.task.Task;

public class LeaveGiants extends Task {

	/** The ladder to get out. */
	private static int LEAVE_LADDER_ID = 29355;
	
	public LeaveGiants(ClientContext ctx) {
		super(ctx);
	}

	/** If we are in the giants den. */
	@Override
	public boolean activate() {
		return WalkingTask.GIANTS_AREA.contains(ctx.players.local()) && ((ctx.players.local().stance() == 18019 && ctx.players.local().animation() == -1) || ctx.players.local().inCombat());
	}

	@Override
	public void execute() {
		if (!ctx.objects.select().id(LEAVE_LADDER_ID).isEmpty()) { //if we find the ladder
			GameObject ladder = ctx.objects.nearest().poll(); //Get the ladder
			if (ladder.inViewport()) //If we see it
				ladder.interact("Climb-up"); //Climb it
			else //Otherwise 
				ctx.movement.step(ladder.tile()); //Walk to it
		}
	}
	
}
