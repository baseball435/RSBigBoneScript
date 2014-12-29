package com.base.granitecutter.task.tasks;

import org.powerbot.script.rt6.ClientContext;

import com.base.granitecutter.task.Task;

public class Inv5kgCheck extends Task {

	/** The total amount of bones picked up. */
	public static int granite5kgCut = 0;
	
	/** Holds a count to find the difference from. */
	private int lastGranite5kgCount = 0;
	
	public Inv5kgCheck(ClientContext ctx) {
		super(ctx);
		lastGranite5kgCount = ctx.backpack.select().id(TaskManager.GRANITE_5KG_ID).count(); //Get the initial count
	}

	@Override
	public boolean activate() {
		boolean b = ctx.backpack.select().id(TaskManager.GRANITE_5KG_ID).count() < lastGranite5kgCount; //If there are more than last check
		if (ctx.backpack.select().id(TaskManager.GRANITE_5KG_ID).count() > lastGranite5kgCount) //means we either buried or put them in the bank
			lastGranite5kgCount = ctx.backpack.select().id(TaskManager.GRANITE_5KG_ID).count(); //Set the count to the amount
		return b;
	}

	@Override
	public void execute() {
		granite5kgCut += lastGranite5kgCount - ctx.backpack.select().id(TaskManager.GRANITE_5KG_ID).count(); //Set it to the difference
		lastGranite5kgCount = ctx.backpack.select().id(TaskManager.GRANITE_5KG_ID).count(); //Set the count
	}
	
}