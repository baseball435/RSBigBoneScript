package com.base.bigboner.task.tasks;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;

import com.base.bigboner.task.Task;

public class WalkToBank extends Task {
	
	/** The bank area. */
	private static final Area BANK_AREA = new Area(new Tile(3143, 3483), new Tile(3152, 3474));
	
	public WalkToBank(ClientContext ctx) {
		super(ctx);
	}

	/** If we are not in the giants den and outside of the house to go to the dungeon. */
	@Override
	public boolean activate() {
		return !WalkingTask.GIANTS_AREA.contains(ctx.players.local()) && !WalkingTask.IN_HOUSE_AREA.contains(ctx.players.local()) && ctx.players.local().stance() == 18019 && ctx.players.local().animation() == -1;
	}

	@Override
	public void execute() {
		if (!BANK_AREA.contains(ctx.players.local())) { //If we arent in the bank area
			ctx.movement.step(BANK_AREA.getRandomTile()); //Go to it!
		} else { 
			if (!ctx.bank.opened()) { //If the bank interface is not open
				if (!ctx.npcs.select().id(3293, 3416).isEmpty()) { //Find an npc to open it
					Npc banker = ctx.npcs.nearest().poll(); //Get the npc
					if (banker.inViewport()) { //If we can see them
						banker.interact("Bank"); //Open the bank
					} else { //Otherwise
						ctx.movement.step(banker.tile()); //Move to be able to see them
					}
				}
			} else { //if the bank is open then deposit and close the bank
				ctx.bank.deposit(PickUpBones.BONE_ID, ctx.backpack.select().id(PickUpBones.BONE_ID).count());
				ctx.bank.close();
				FullInv.doingAction = false;
			}
		}
	}
	
}