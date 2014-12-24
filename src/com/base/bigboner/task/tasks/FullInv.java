package com.base.bigboner.task.tasks;

import org.powerbot.script.rt6.ClientContext;

import com.base.bigboner.Config;
import com.base.bigboner.task.Task;

public class FullInv extends Task {

	/** Either in the process of banking or burying the bones. */
	public static boolean doingAction;
	
	/** The burying bones task. */
	private BuryBones buryBonesTask;
	
	/** The leave giants task. */
	private LeaveGiants leaveGiantsTask;
	
	/** The leave house task. */
	private LeaveHouse leaveHouseTask;
	
	/** The walk to bank task. */
	private WalkToBank walkToBankTask;
	
	public FullInv(ClientContext ctx) {
		super(ctx);
		buryBonesTask = new BuryBones(ctx);
		leaveGiantsTask = new LeaveGiants(ctx);
		leaveHouseTask = new LeaveHouse(ctx);
		walkToBankTask = new WalkToBank(ctx);
	}

	/** If the inventory is full or we are in the process of doing an action. */
	@Override
	public boolean activate() {
		return ctx.backpack.select().count() == 28 || doingAction;
	}

	@Override
	public void execute() {
		if (ctx.backpack.select().count() == 28) //if it's full then set doingAction to true
			doingAction = true;
		if (Config.BURY_BONES) { //if we are burying the bones and we are in the safe area or not in the giants den
			if (PickUpBones.SAFE_AREA.contains(ctx.players.local()) || !WalkingTask.GIANTS_AREA.contains(ctx.players.local())) {
				if (doingAction) { //If we're doing it
					if (buryBonesTask.activate()) { //If we can do the bones task then it means there are bones left
						buryBonesTask.execute(); //Bury them
					} else { //There are no bones left
						doingAction = false; //Stop the action
					}
				}
			} else {
				ctx.movement.step(PickUpBones.SAFE_AREA.getRandomTile()); //Move to the safe zone
			}
		} else if (Config.BANK_BONES) { //if we are banking them 
			if (leaveGiantsTask.activate()) //If we are still in the giants den then get out
				leaveGiantsTask.execute();
			if (leaveHouseTask.activate()) //If we are still in the house then get out
				leaveHouseTask.execute();
			if (walkToBankTask.activate()) //If we are not at the bank and havent banked then do it
				walkToBankTask.execute();
		}
		
	}
	
	/** @return Whether or not we are banking or burying. */
	public static boolean isDoingAction() {
		return doingAction;
	}
	
}