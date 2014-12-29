package com.base.granitecutter.task;

import org.powerbot.script.rt6.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;

public abstract class Task extends ClientAccessor {

	public Task(ClientContext ctx) {
		super(ctx);
	}
	
	/** @return Whether or not to follow through with the task. */
	public abstract boolean activate();
	
	/** What to do in the task. */
	public abstract void execute();
	
}