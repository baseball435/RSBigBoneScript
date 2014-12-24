package com.base.bigboner.task.tasks;

import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

import com.base.bigboner.task.Task;

public class LeaveHouse extends Task {
	
	public LeaveHouse(ClientContext ctx) {
		super(ctx);
	}

	/** If we are in the house area. */
	@Override
	public boolean activate() {
		return WalkingTask.IN_HOUSE_AREA.contains(ctx.players.local()) && ctx.players.local().stance() == 18019 && ctx.players.local().animation() == -1;
	}

	@Override
	public void execute() {
		if (!ctx.objects.select().id(WalkToHouse.DOOR_ID).isEmpty()) { //If we can find the door
			GameObject doorObject = ctx.objects.nearest().poll(); //Get the door
			if (doorObject.inViewport()) { //If we can see it
				//For some reason I can't interact with this door so I had to kind of hack my way to make it work
				ctx.camera.pitch(Random.nextInt(60, 70)); //Move the camera down
				ctx.camera.angle(Random.nextInt(0, 20)); //Move the camera to face the door 
				ctx.mouse.click(doorObject.centerPoint().x, doorObject.centerPoint().y - Random.nextInt(30, 60), true); //Click in the middle of the door
			} else { //If we cant see the door
				ctx.movement.step(doorObject.tile()); //Move to it 
			}
		}
	}
	
}

