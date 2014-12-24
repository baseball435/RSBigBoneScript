package com.base.bigboner.task.tasks;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;

import com.base.bigboner.task.Task;

public class WalkingTask extends Task {
	
	/** The giants den. */
	public static Area GIANTS_AREA = new Area(new Tile(3125, 9865), new Tile(3085, 9820));
	
	/** The house area. */
	public static Area HOUSE_AREA = new Area(new Tile(3109, 3457), new Tile(3121, 3446));
	
	/** The inside house area. */
	public static Area IN_HOUSE_AREA = new Area(new Tile(3113, 3453), new Tile(3117, 3450));
	
	/** The Walk To Giants task. */
	private WalkToGiants walkToGiantsTask;
	
	/** The Walk To House task. */
	private WalkToHouse walkToHouseTask;
	
	public WalkingTask(ClientContext ctx) {
		super(ctx);
		walkToGiantsTask = new WalkToGiants(ctx);
		walkToHouseTask = new WalkToHouse(ctx);
	}

	/** If we arent moving. */
	@Override
	public boolean activate() {
		return ctx.players.local().stance() == 18019 && ctx.players.local().animation() == -1;
	}

	@Override
	public void execute() {
		if (ctx.backpack.select().count() < 28 && !FullInv.doingAction) { //not going to bank yet
			if (!WalkingTask.GIANTS_AREA.contains(ctx.players.local())) { //if we arent in the giants den
				if (ctx.movement.reachable(ctx.players.local(), GIANTS_AREA.getRandomTile())) { //If we can reach it from our location without going up or down ladders
					if (walkToGiantsTask.activate()) //Then walk to the giants
						walkToGiantsTask.execute();
				} else { //If we have to go down the ladder first
					if (walkToHouseTask.activate()) //Walk to the house
						walkToHouseTask.execute();
				}
			}
		}
	}
	
}