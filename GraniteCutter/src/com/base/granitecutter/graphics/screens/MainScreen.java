package com.base.granitecutter.graphics.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GeItem;
import org.powerbot.script.rt6.GeItem.PriceType;

import com.base.granitecutter.Config;
import com.base.granitecutter.GraniteCutterScript;
import com.base.granitecutter.graphics.Screen;
import com.base.granitecutter.graphics.gui.CheckBox;
import com.base.granitecutter.graphics.gui.IChangingVariable;
import com.base.granitecutter.graphics.gui.IMouseClickAction;
import com.base.granitecutter.graphics.gui.Label;
import com.base.granitecutter.graphics.gui.Panel;
import com.base.granitecutter.graphics.gui.Rect;
import com.base.granitecutter.task.tasks.Inv2kgCheck;
import com.base.granitecutter.task.tasks.Inv5kgCheck;
import com.base.granitecutter.task.tasks.TaskManager;

public class MainScreen extends Screen implements MouseListener {

	private static final Color GRAY = new Color(110, 110, 110), LIGHT_GRAY2 = new Color(200, 200, 200);
	private static DecimalFormat df = new DecimalFormat("#.##");
	
	private final Panel infoPanel = new Panel("Information", 15, 15, 250, 419);
	private final Panel settingsPanel = new Panel("Settings", 265, 15, 250, 100);
	private final Panel antibanPanel = new Panel("Anti-Ban", 515, 15, 250, 280);
	
	public MainScreen(ClientContext ctx) {
		super(ctx);
	}
	
	@Override
	public void create() {
		ctx.dispatcher.add(this);
		setInfoPanel();
		setSettingsPanel();
		setAntibanPanel();
	}
	
	private void setInfoPanel() {
		Label infoLbl = new Label("Base's Granite Cutter", 41, 35);
		infoLbl.setFont(GraniteCutterScript.BIG_FONT);
		infoLbl.setColor(Color.WHITE);
		
		Label timeLbl = new Label("Time Running: ", 50, 75);
		timeLbl.setFont(GraniteCutterScript.NORMAL_FONT);
		timeLbl.setColor(Color.BLACK);
		timeLbl.setChangingVariable(new IChangingVariable() {
			@Override
			public Object getVariable() {
				int toSeconds = (int)GraniteCutterScript.instance.getTotalRuntime() / 1000;
				int minutes = (toSeconds / 60) % 60;
				int hours = ((toSeconds / 60) / 60);
				int seconds = toSeconds % 60;
				return hours + ":" + minutes + ":" + seconds;
			}
		});
		
		Label granite5kgCutLbl = new Label("Granite (5kg) Cut: ", 50, 111);
		granite5kgCutLbl.setFont(GraniteCutterScript.NORMAL_FONT);
		granite5kgCutLbl.setColor(Color.BLACK);
		granite5kgCutLbl.setChangingVariable(new IChangingVariable() {
			@Override
			public Object getVariable() {
				return Inv5kgCheck.granite5kgCut;
			}
		});
		

		Label granite2kgCutLbl = new Label("Granite (2kg) Cut: ", 50, 147);
		granite2kgCutLbl.setFont(GraniteCutterScript.NORMAL_FONT);
		granite2kgCutLbl.setColor(Color.BLACK);
		granite2kgCutLbl.setChangingVariable(new IChangingVariable() {
			@Override
			public Object getVariable() {
				return Inv2kgCheck.granite2kgCut;
			}
		});
		
		Label profitPerHourLbl = new Label("Profit/Hour: ", 50, 183);
		profitPerHourLbl.setFont(GraniteCutterScript.NORMAL_FONT);
		profitPerHourLbl.setColor(Color.BLACK);
		profitPerHourLbl.setChangingVariable(new IChangingVariable() {
			@Override
			public Object getVariable() {
				
				double profit = GraniteCutterScript.instance.getProfit();
				
				return df.format((3600000.0D * profit) / GraniteCutterScript.instance.getTotalRuntime());
			}
		});
		
		Label profitLbl = new Label("Profit Made: ", 50, 219);
		profitLbl.setFont(GraniteCutterScript.NORMAL_FONT);
		profitLbl.setColor(Color.BLACK);
		profitLbl.setChangingVariable(new IChangingVariable() {
			@Override
			public Object getVariable() {
				return GraniteCutterScript.instance.getProfit();
			}
		});
		
		Label pricesLbl = new Label("Prices", 105, 265);
		pricesLbl.setFont(GraniteCutterScript.BIG_FONT);
		pricesLbl.setColor(Color.WHITE);
		
		Label granite5kgPriceLbl = new Label("Granite (5kg) Price: ", 50, 306);
		granite5kgPriceLbl.setFont(GraniteCutterScript.NORMAL_FONT);
		granite5kgPriceLbl.setColor(Color.BLACK);
		granite5kgPriceLbl.setChangingVariable(new IChangingVariable() {
			@Override
			public Object getVariable() {
				return GraniteCutterScript.instance.FIVEKG_PRICE;
			}
		});

		Label granite2kgPriceLbl = new Label("Granite (2kg) Price: ", 50, 342);
		granite2kgPriceLbl.setFont(GraniteCutterScript.NORMAL_FONT);
		granite2kgPriceLbl.setColor(Color.BLACK);
		granite2kgPriceLbl.setChangingVariable(new IChangingVariable() {
			@Override
			public Object getVariable() {
				return GraniteCutterScript.instance.TWOKG_PRICE;
			}
		});
		
		Label granite500gPriceLbl = new Label("Granite (500g) Price: ", 50, 378);
		granite500gPriceLbl.setFont(GraniteCutterScript.NORMAL_FONT);
		granite500gPriceLbl.setColor(Color.BLACK);
		granite500gPriceLbl.setChangingVariable(new IChangingVariable() {
			@Override
			public Object getVariable() {
				return GraniteCutterScript.instance.G_PRICE;
			}
		});
		
		Rect infoBg = new Rect(27, 15, 197, 25, GRAY);
		infoBg.setOutlineColor(LIGHT_GRAY2);
		
		Rect timeBg = new Rect(5, 57, 240, 25, LIGHT_GRAY2);
		timeBg.setOutlineColor(GRAY);
		
		Rect granite5kgBg = new Rect(5, 93, 240, 25, LIGHT_GRAY2);
		timeBg.setOutlineColor(GRAY);
		
		Rect granite2kgBg = new Rect(5, 129, 240, 25, LIGHT_GRAY2);
		timeBg.setOutlineColor(GRAY);
		
		Rect profitPerHourBg = new Rect(5, 165, 240, 25, LIGHT_GRAY2);
		timeBg.setOutlineColor(GRAY);
		
		Rect profitBg = new Rect(5, 201, 240, 25, LIGHT_GRAY2);
		profitBg.setOutlineColor(GRAY);
		
		Rect pricesBg = new Rect(27, 245, 197, 25, GRAY);
		pricesBg.setOutlineColor(LIGHT_GRAY2);
		
		Rect granite5kgPriceBg = new Rect(5, 288, 240, 25, LIGHT_GRAY2);
		profitBg.setOutlineColor(GRAY);

		Rect granite2kgPriceBg = new Rect(5, 324, 240, 25, LIGHT_GRAY2);
		profitBg.setOutlineColor(GRAY);
		
		Rect granite500gPriceBg = new Rect(5, 360, 240, 25, LIGHT_GRAY2);
		profitBg.setOutlineColor(GRAY);

		infoPanel.addComponent(timeBg);
		infoPanel.addComponent(pricesBg);
		infoPanel.addComponent(granite5kgBg);
		infoPanel.addComponent(granite2kgBg);
		infoPanel.addComponent(profitPerHourBg);
		infoPanel.addComponent(granite5kgPriceBg);
		infoPanel.addComponent(granite2kgPriceBg);
		infoPanel.addComponent(granite500gPriceBg);
		infoPanel.addComponent(profitBg);
		infoPanel.addComponent(infoBg);
		infoPanel.addComponent(infoLbl);
		infoPanel.addComponent(timeLbl);
		infoPanel.addComponent(granite5kgCutLbl);
		infoPanel.addComponent(granite2kgCutLbl);
		infoPanel.addComponent(profitPerHourLbl);
		infoPanel.addComponent(profitLbl);
		infoPanel.addComponent(pricesLbl);
		infoPanel.addComponent(granite5kgPriceLbl);
		infoPanel.addComponent(granite2kgPriceLbl);
		infoPanel.addComponent(granite500gPriceLbl);
	}

	private void setSettingsPanel() {
		Label cut2kgLbl = new Label("Cut Granite (2kg) After 5kg: ", 15, 55);
		cut2kgLbl.setFont(GraniteCutterScript.NORMAL_FONT);
		cut2kgLbl.setColor(Color.BLACK);
		
		CheckBox cut2kgChk = new CheckBox(210, 35);
		cut2kgChk.setChecked(Config.CUT_GRANITE_2KG);
		cut2kgChk.setMouseClickAction(new IMouseClickAction() {

			@Override
			public void onMouseClick() {
				Config.CUT_GRANITE_2KG = !Config.CUT_GRANITE_2KG;
			}
			
		});
		
		Rect cutBg = new Rect(5, 32, 240, 35, LIGHT_GRAY2);
		cutBg.setOutlineColor(GRAY);
		
		settingsPanel.addComponent(cutBg);
		settingsPanel.addComponent(cut2kgLbl);
		settingsPanel.addComponent(cut2kgChk);
	}
	
	private void setAntibanPanel() {
		Label worldHopLbl = new Label("World Hop: ", 15, 55);
		worldHopLbl.setFont(GraniteCutterScript.NORMAL_FONT);
		worldHopLbl.setColor(Color.BLACK);
		
		CheckBox worldHopChk = new CheckBox(210, 35);
		worldHopChk.setChecked(Config.DO_WORLD_HOPS);
		worldHopChk.setMouseClickAction(new IMouseClickAction() {

			@Override
			public void onMouseClick() {
				Config.DO_WORLD_HOPS = !Config.DO_WORLD_HOPS;
			}
			
		});

		Label switchBanksLbl = new Label("Switch Banks: ", 15, 100);
		switchBanksLbl.setFont(GraniteCutterScript.NORMAL_FONT);
		switchBanksLbl.setColor(Color.BLACK);
		
		CheckBox switchBanksChk = new CheckBox(210, 80);
		switchBanksChk.setChecked(Config.SWITCH_BANKS);
		switchBanksChk.setMouseClickAction(new IMouseClickAction() {

			@Override
			public void onMouseClick() {
				Config.SWITCH_BANKS = !Config.SWITCH_BANKS;
			}
			
		});
		
		Label rotateCameraLbl = new Label("Rotate Camera: ", 15, 145);
		rotateCameraLbl.setFont(GraniteCutterScript.NORMAL_FONT);
		rotateCameraLbl.setColor(Color.BLACK);
		
		CheckBox rotateCameraChk = new CheckBox(210, 125);
		rotateCameraChk.setChecked(Config.ROTATE_CAMERA);
		rotateCameraChk.setMouseClickAction(new IMouseClickAction() {

			@Override
			public void onMouseClick() {
				Config.ROTATE_CAMERA = !Config.ROTATE_CAMERA;
			}
			
		});

		Label hoverPlayersLbl = new Label("Hover Over Players: ", 15, 190);
		hoverPlayersLbl.setFont(GraniteCutterScript.NORMAL_FONT);
		hoverPlayersLbl.setColor(Color.BLACK);
		
		CheckBox hoverPlayersChk = new CheckBox(210, 170);
		hoverPlayersChk.setChecked(Config.HOVER_PLAYERS);
		hoverPlayersChk.setMouseClickAction(new IMouseClickAction() {

			@Override
			public void onMouseClick() {
				Config.HOVER_PLAYERS = !Config.HOVER_PLAYERS;
			}
			
		});

		Label moveOffScreenLbl = new Label("Move Mouse Off Screen: ", 15, 235);
		moveOffScreenLbl.setFont(GraniteCutterScript.NORMAL_FONT);
		moveOffScreenLbl.setColor(Color.BLACK);
		
		CheckBox moveOffScreenChk = new CheckBox(210, 215);
		moveOffScreenChk.setChecked(Config.MOVE_MOUSE_OFF_SCREEN);
		moveOffScreenChk.setMouseClickAction(new IMouseClickAction() {

			@Override
			public void onMouseClick() {
				Config.MOVE_MOUSE_OFF_SCREEN = !Config.MOVE_MOUSE_OFF_SCREEN;
			}
			
		});
		
		Rect worldHopBg = new Rect(5, 32, 240, 35, LIGHT_GRAY2);
		worldHopBg.setOutlineColor(GRAY);

		Rect switchBanksBg = new Rect(5, 77, 240, 35, LIGHT_GRAY2);
		switchBanksBg.setOutlineColor(GRAY);
		
		Rect rotateCameraBg = new Rect(5, 122, 240, 35, LIGHT_GRAY2);
		rotateCameraBg.setOutlineColor(GRAY);
		
		Rect hoverPlayersBg = new Rect(5, 167, 240, 35, LIGHT_GRAY2);
		hoverPlayersBg.setOutlineColor(GRAY);
		
		Rect moveOffScreenBg = new Rect(5, 212, 240, 35, LIGHT_GRAY2);
		moveOffScreenBg.setOutlineColor(GRAY);
		
		antibanPanel.addComponent(worldHopBg);
		antibanPanel.addComponent(switchBanksBg);
		antibanPanel.addComponent(rotateCameraBg);
		antibanPanel.addComponent(hoverPlayersBg);
		antibanPanel.addComponent(moveOffScreenBg);
		antibanPanel.addComponent(worldHopLbl);
		antibanPanel.addComponent(worldHopChk);
		antibanPanel.addComponent(switchBanksLbl);
		antibanPanel.addComponent(switchBanksChk);
		antibanPanel.addComponent(rotateCameraLbl);
		antibanPanel.addComponent(rotateCameraChk);
		antibanPanel.addComponent(hoverPlayersLbl);
		antibanPanel.addComponent(hoverPlayersChk);
		antibanPanel.addComponent(moveOffScreenLbl);
		antibanPanel.addComponent(moveOffScreenChk);
	}
	
	@Override
	public void update() {
		infoPanel.update();
		settingsPanel.update();
		antibanPanel.update();
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		infoPanel.draw(g2d);
		settingsPanel.draw(g2d);
		antibanPanel.draw(g2d);
	}

	@Override
	public void destroy() {
		ctx.client().getCanvas().removeMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		infoPanel.clicked(event.getX(), event.getY());
		settingsPanel.clicked(event.getX(), event.getY());
		antibanPanel.clicked(event.getX(), event.getY());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

}
