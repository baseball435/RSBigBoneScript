package com.base.bigboner.task.tasks;

import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

import com.base.bigboner.task.Task;

public class WalkToHouse extends Task {
	
//	/** The door and ladder ID in the house. */
	public static final int DOOR_ID = 1804, LADDER_ID = 12389;
	
	public WalkToHouse(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return true;
	}

	@Override
	public void execute() {
		if (!WalkingTask.IN_HOUSE_AREA.contains(ctx.players.local())) { //if not in the small house
			if (!WalkingTask.HOUSE_AREA.contains(ctx.players.local())) { //if not in the house area outside
				ctx.movement.step(WalkingTask.HOUSE_AREA.getRandomTile()); //walk to it
			} else { //go in the door
				if (!ctx.objects.select().id(DOOR_ID).isEmpty()) { //If we find the door
					GameObject doorObject = ctx.objects.nearest().poll(); //Get the door
					if (doorObject.inViewport()) { //If we see it open it
						//For some reason I can't interact with this door so I had to kind of hack my way to make it work
						ctx.camera.pitch(Random.nextInt(60, 70)); //Move the camera down
						ctx.camera.angle(Random.nextInt(0, 20)); //Move the camera to face the door 
						ctx.mouse.click(doorObject.centerPoint().x, doorObject.centerPoint().y - Random.nextInt(30, 60), true); //Click in the middle of the door
					} else { //If we cant see it then move to it
						ctx.movement.step(doorObject.tile());
					}
				}
			}
		} else { //press the ladder
			if (!ctx.objects.select().id(LADDER_ID).isEmpty()) { //If we find the ladder
				GameObject ladder = ctx.objects.nearest().poll(); //Get it and climb down it
				if (ladder.inViewport())
					ladder.interact("Climb-Down");
				else
					ctx.movement.step(ladder);
			}
		}
	}
	
}
