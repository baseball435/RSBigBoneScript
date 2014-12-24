package com.base.bigboner.task.tasks;

import org.powerbot.script.rt6.ClientContext;

import com.base.bigboner.task.Task;

public class HealthCheck extends Task {

	public HealthCheck(ClientContext ctx) {
		super(ctx);
	}

	/** If the health is below 30% */
	@Override
	public boolean activate() {
		return getHealthPercent() <= 30;
	}

	/** If we arent in the safe area then move to it immediately. */
	@Override
	public void execute() {
		if (!PickUpBones.SAFE_AREA.contains(ctx.players.local()) && ((ctx.players.local().stance() == 18019 && ctx.players.local().animation() == -1) || ctx.players.local().inCombat()))
				ctx.movement.step(PickUpBones.SAFE_AREA.getRandomTile());
	}
	
	/** Gets the current percent of health of the player.
	 * It seems that ctx.players.local().getHealthPercent() only returns the percentage when in combat.
	 * That is why I had to use this method to get the percent at all times
	 */
	public int getHealthPercent() {
        return ctx.widgets.select().id(1430).peek().component(4).component(3).width();
    }
	
}