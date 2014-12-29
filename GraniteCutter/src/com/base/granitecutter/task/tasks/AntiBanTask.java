package com.base.granitecutter.task.tasks;

import java.util.List;
import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Lobby;
import org.powerbot.script.rt6.Lobby.World;

import com.base.granitecutter.Config;
import com.base.granitecutter.task.DelayedTask;
import com.base.granitecutter.util.AntiPattern;

public class AntiBanTask extends DelayedTask {

	private static final int WORLD_HOP_DELAY = 900000; // 15 minutes
	private static final int BANK_SWITCH_DELAY = 1500000; // 25 minutes

	private List<Lobby.World> worlds;
	private long lastWorldHop, lastBankSwitch;
	
	public AntiBanTask(ClientContext ctx) {
		super(ctx, 5000);
		lastWorldHop = System.currentTimeMillis();
		lastBankSwitch = System.currentTimeMillis();
	}

	@Override
	public boolean activate() {
		return passesTime(Random.nextInt(2000, 5000));
	}

	@Override
	public void execute() {
		
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return worldHop();
			}
		});
		
		moveCamera();
		switchBanks();
	}
	
	private boolean moveCamera() {
		if (Config.ROTATE_CAMERA) {
			int rand = Random.nextInt(0, 500); 
			if (rand < 75) {
				ctx.camera.angle(Random.nextInt(50, 70));
			} else if (rand < 150) {
				ctx.camera.pitch(Random.nextInt(0, 360));
			} else if (rand < 225) {
				ctx.camera.angle(Random.nextInt(50, 70));
				ctx.camera.pitch(Random.nextInt(0, 360));
			} else if (rand <= 300) {
				ctx.camera.pitch(Random.nextInt(0, 360));
				ctx.camera.angle(Random.nextInt(40, 60));
			} 
		}
		return true;
	}
	
	private boolean worldHop() {
		if (Config.DO_WORLD_HOPS && System.currentTimeMillis() - lastWorldHop >= WORLD_HOP_DELAY + Random.nextInt(0, 60000) && !ctx.bank.opened() && ctx.players.local().animation() == -1) {
			ctx.game.logout(true);
			Condition.wait(new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					return ctx.lobby.opened();
				}
			
			}, 100, 50);
			worlds = ctx.lobby.worlds(new Filter<Lobby.World>() {
				@Override
				public boolean accept(World world) {
					return world.members();
				}
					
			});
			if (worlds.size() > 0) {
				ctx.lobby.world(worlds.get(Random.nextInt(0, worlds.size() - 1)));
			}
			ctx.lobby.enterGame();
			lastWorldHop = System.currentTimeMillis();
		}
		return true;
	}

	private boolean switchBanks() {
		if (Config.SWITCH_BANKS && System.currentTimeMillis() - lastBankSwitch >= BANK_SWITCH_DELAY + Random.nextInt(0, 60000) && !ctx.bank.opened() && ctx.players.local().animation() == -1) {
			InBankAreaTask.switchBanks();
			lastBankSwitch = System.currentTimeMillis();
			return true;
		}
		return false;
	}
}
