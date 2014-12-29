package com.base.granitecutter.task.tasks;

import org.powerbot.script.rt6.ClientContext;

import com.base.granitecutter.Config;
import com.base.granitecutter.task.Task;

public class Inv2kgCheck extends Task {

	/** The total amount of bones picked up. */
	public static int granite2kgCut = 0;
	
	/** Holds a count to find the difference from. */
	private int lastGranite2kgCount = 0;
	
	public Inv2kgCheck(ClientContext ctx) {
		super(ctx);
		lastGranite2kgCount = ctx.backpack.select().id(TaskManager.GRANITE_2KG_ID).count(); //Get the initial count
	}

	@Override
	public boolean activate() {
		if (!TakeGraniteTask.cutting2Kg)
			return false;
		boolean b = ctx.backpack.select().id(TaskManager.GRANITE_2KG_ID).count() < lastGranite2kgCount; //If there are more than last check
		if (ctx.backpack.select().id(TaskManager.GRANITE_2KG_ID).count() > lastGranite2kgCount) //means we either buried or put them in the bank
			lastGranite2kgCount = ctx.backpack.select().id(TaskManager.GRANITE_2KG_ID).count(); //Set the count to the amount
		return b;
	}

	@Override
	public void execute() {
		granite2kgCut += lastGranite2kgCount - ctx.backpack.select().id(TaskManager.GRANITE_2KG_ID).count(); //Set it to the difference
		lastGranite2kgCount = ctx.backpack.select().id(TaskManager.GRANITE_2KG_ID).count(); //Set the count
	}
	
}