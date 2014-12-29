package com.base.granitecutter.task.tasks;

import org.powerbot.script.rt6.ClientContext;

import com.base.granitecutter.task.Task;

public class TaskManager extends Task {
	
	public static int GRANITE_5KG_ID = 6983, GRANITE_2KG_ID = 6981, GRANITE_500G_ID = 6979;
	
	private final InBankAreaTask inBankAreaTask;
	private final BankInvTask bankInvTask;
	private final TakeGraniteTask takeGraniteTask;
	private final CutGraniteTask cutGraniteTask;
	private final AntiBanTask antiBanTask;
	
	public TaskManager(ClientContext ctx) {
		super(ctx);
		inBankAreaTask = new InBankAreaTask(ctx);
		bankInvTask = new BankInvTask(ctx);
		takeGraniteTask = new TakeGraniteTask(ctx);
		cutGraniteTask = new CutGraniteTask(ctx);
		antiBanTask = new AntiBanTask(ctx);
	}

	@Override
	public boolean activate() {
		return true;
	}

	@Override
	public void execute() {
		if (antiBanTask.activate())
			antiBanTask.execute();
		if (inBankAreaTask.activate()) { //if we arent in the bank area then go there
			inBankAreaTask.execute();
		} else {
			if (bankInvTask.activate())
				bankInvTask.execute();
			if (takeGraniteTask.activate())
				takeGraniteTask.execute();
			if (cutGraniteTask.activate())
				cutGraniteTask.execute();
		}
	}

}
