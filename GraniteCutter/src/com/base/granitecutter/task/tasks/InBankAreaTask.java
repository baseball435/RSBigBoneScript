package com.base.granitecutter.task.tasks;

import org.powerbot.script.Area;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;

import com.base.granitecutter.task.Task;

public class InBankAreaTask extends Task {

	private static final Area BOTTOM_LEFT_BANK = new Area(new Tile(3144, 3482), new Tile(3152, 3475));
	private static final Area BOTTOM_RIGHT_BANK = new Area(new Tile(3176, 3482), new Tile(3184, 3475));
	private static final Area TOP_RIGHT_BANK = new Area(new Tile(3177, 3498), new Tile(3185, 3509));
	private static final Area TOP_LEFT_BANK = new Area(new Tile(3144, 3509), new Tile(3152, 3501));
	
	private static Area selectedBank = getRandomBank();
	
	public InBankAreaTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return !selectedBank.contains(ctx.players.local());
	}

	@Override
	public void execute() {
		System.out.println("Not in bank");
		if ((ctx.players.local().stance() == 18019 || ctx.players.local().stance() == 808) && ctx.players.local().animation() == -1) {
			ctx.movement.step(selectedBank.getRandomTile());
		}
	}
	
	public static void switchBanks() {
		selectedBank = getRandomBank();
	}
	
	private static Area getRandomBank() {
		int rand = Random.nextInt(0, 100);
		if (rand < 25) {
			return BOTTOM_LEFT_BANK;
		} else if (rand < 50) {
			return BOTTOM_RIGHT_BANK;
		} else if (rand < 75) {
			return TOP_LEFT_BANK;
		}
		return TOP_RIGHT_BANK;
	}

}
