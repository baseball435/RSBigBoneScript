package com.base.bigboner.task.tasks;

import org.powerbot.script.rt6.ClientContext;

import com.base.bigboner.task.Task;

public class PickupManager extends Task {

	/** Pick Up Bones task. */
	private PickUpBones pickUpTask;
	
	/** Health Check task. */
	private HealthCheck healthCheckTask;
	
	public PickupManager(ClientContext ctx) {
		super(ctx);
		pickUpTask = new PickUpBones(ctx);
		healthCheckTask = new HealthCheck(ctx);
	}

	/** If we are in the giants den and are inventories not full. */
	@Override
	public boolean activate() {
		return WalkingTask.GIANTS_AREA.contains(ctx.players.local()) && ctx.backpack.select().count() != 28 && !FullInv.isDoingAction();
	}

	@Override
	public void execute() {
		if (healthCheckTask.activate()) { //If are health is below 30% dont pick up bones
			healthCheckTask.execute();
		} else { 
			if (pickUpTask.activate()) //Pick up bones if we can
 				pickUpTask.execute();
		}
	}
	
}
