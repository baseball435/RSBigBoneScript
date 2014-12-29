package com.base.bigboner.task.tasks;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GroundItem;

import com.base.bigboner.task.Task;

public class PickUpBones extends Task {

	/** The big bone ID. */
	public static final int BONE_ID = 532;
	
	/** The safe area. */
	public static final Area SAFE_AREA = new Area(new Tile(3097, 9837), new Tile(3099, 9838));
	
	public PickUpBones(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return true;
	}

	@Override
	public void execute() {
		if (!ctx.groundItems.select().id(BONE_ID).isEmpty()) { //If there are bones to pickup
			GroundItem bone = ctx.groundItems.nearest().poll(); //Get the closest one
			if (bone.inViewport())  //If we can see it
				bone.interact(false, "Take", "Big bones"); //Take it
			else if ((ctx.players.local().stance() == 18019 && ctx.players.local().animation() == -1) || ctx.players.local().inCombat()) //Otherwise if we arent moving then move to it
				ctx.movement.step(bone.tile());
		} else { //If there are no bones on the ground
			if (!SAFE_AREA.contains(ctx.players.local())) //If we arent in the safe zone
				ctx.movement.step(SAFE_AREA.getRandomTile()); //Move to it
		}
	}
	
}