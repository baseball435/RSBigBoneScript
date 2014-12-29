package com.base.granitecutter.util;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Player;

public class AntiPattern {

	public static boolean moveMouseOffScreen(ClientContext ctx) {
		System.out.println("moving off screen");
		int rand = Random.nextInt(0, 100);
		if (rand < 25) {
			Condition.wait(new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {	
					return ctx.mouse.move(0, Random.nextInt(0, ctx.client().getCanvas().getHeight()));
				}
			}, 100, 10);
		} else if (rand < 50) {
			Condition.wait(new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {	
					return ctx.mouse.move(ctx.client().getCanvas().getWidth(), Random.nextInt(0, ctx.client().getCanvas().getHeight()));
				}
			}, 100, 10);
		} else if (rand < 75) {
			Condition.wait(new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {	
					return ctx.mouse.move(Random.nextInt(0, ctx.client().getCanvas().getWidth()), 0);
				}
			}, 100, 10);
		} else {
			
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {	
					return ctx.mouse.move(Random.nextInt(0, ctx.client().getCanvas().getWidth()), ctx.client().getCanvas().getHeight());
				}
			}, 100, 10);
		}
		System.out.println("Done moving off screen");
		return true;
	}
	
	public static boolean hoverOverPlayer(ClientContext ctx) {
		ctx.players.select(new Filter<Player>() {

			@Override
			public boolean accept(Player player) {
				return player.inViewport();
			}
			
		});
		if (!ctx.players.isEmpty()) {

			Player player = ctx.players.shuffle().poll();
			System.out.println("hovering");
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {	
					return player.hover();
				}
			}, 100, 50);
			System.out.println("Done hovering");
		}
		return true;
	}
	
}
