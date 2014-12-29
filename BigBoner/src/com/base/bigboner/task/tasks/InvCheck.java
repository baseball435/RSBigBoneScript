package com.base.bigboner.task.tasks;

import org.powerbot.script.rt6.ClientContext;

import com.base.bigboner.task.Task;

public class InvCheck extends Task {

	/** The total amount of bones picked up. */
	public static int bonesPickedUp = 0;
	
	/** Holds a count to find the difference from. */
	private int lastBoneCount = 0;
	
	public InvCheck(ClientContext ctx) {
		super(ctx);
		lastBoneCount = ctx.backpack.select().id(PickUpBones.BONE_ID).count(); //Get the initial count
	}

	@Override
	public boolean activate() {
		boolean b = ctx.backpack.select().id(PickUpBones.BONE_ID).count() > lastBoneCount; //If there are more than last check
		if (ctx.backpack.select().id(PickUpBones.BONE_ID).count() < lastBoneCount) //means we either buried or put them in the bank
			lastBoneCount = ctx.backpack.select().id(PickUpBones.BONE_ID).count(); //Set the count to the amount
		return b;
	}

	@Override
	public void execute() {
		bonesPickedUp += ctx.backpack.select().id(PickUpBones.BONE_ID).count() - lastBoneCount; //Set it to the difference
		lastBoneCount = ctx.backpack.select().id(PickUpBones.BONE_ID).count(); //Set the count
	}
	
}