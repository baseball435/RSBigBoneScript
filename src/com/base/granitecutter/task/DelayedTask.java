package com.base.granitecutter.task;

import org.powerbot.script.rt6.ClientContext;

public abstract class DelayedTask extends Task {

	/** The amount of time to delay the task. */
	private int timeDelay;
	
	/** The last execution. */
	private long lastDelay;
	
	/** Takes the context and the time to delay the task.
	 * 
	 * @param ctx Reference to the context
	 * @param timeDelay The time to delay the task
	 */
	public DelayedTask(ClientContext ctx, int timeDelay) {
		super(ctx);
		this.timeDelay = timeDelay;
	}
	
	/** 
	 * @param random The random amount of time to add to the delay to randomize it
	 * @return Whether or not the time delay has passed
	 */
	protected boolean passesTime(int random) {
		boolean b = System.currentTimeMillis() - lastDelay >= timeDelay;
		if (b)
			lastDelay = System.currentTimeMillis();
		return b;
	}
	
}
