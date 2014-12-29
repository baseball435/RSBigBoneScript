package com.base.bigboner.task.tasks;

import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Hud.Window;

import com.base.bigboner.task.DelayedTask;

public class AntiBan extends DelayedTask {

	public AntiBan(ClientContext ctx) {
		super(ctx, 5000);
	}

	@Override
	public boolean activate() {
		return passesTime(Random.nextInt(2000, 5000));
	}

	@Override
	public void execute() {
		int rand = Random.nextInt(0, 500); //get a random number
		if (!FullInv.isDoingAction()) { //If we arent burying or banking
			if (rand < 25) {
				ctx.hud.open(Window.SKILLS); 
			} else if (rand < 50) {
				ctx.hud.open(Window.BACKPACK);
			} else if (rand < 75) {
				ctx.hud.open(Window.ACTIVE_TASK);
			} else if (rand < 100) {
				ctx.hud.open(Window.PRAYER_ABILITIES);
			} else if (rand <= 125) {
				ctx.hud.open(Window.WORN_EQUIPMENT);
			}else if (rand < 200) {
				ctx.camera.angle(Random.nextInt(10, 70));
			} else if (rand < 300) {
				ctx.camera.pitch(Random.nextInt(0, 360));
			} else if (rand < 400) {
				ctx.camera.angle(Random.nextInt(50, 70));
				ctx.camera.pitch(Random.nextInt(0, 360));
			} else if (rand <= 500) {
				ctx.camera.angle(Random.nextInt(20, 40));
				ctx.camera.pitch(Random.nextInt(0, 360));
			} 
		}

	}
	
}
