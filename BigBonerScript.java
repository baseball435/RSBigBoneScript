import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import org.powerbot.script.Area;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Random;
import org.powerbot.script.Script;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.GroundItem;
import org.powerbot.script.rt6.Hud.Window;
import org.powerbot.script.rt6.Npc;

@Script.Manifest(name="BigBoner", description="Picks up and buries or banks bones.")
public class BigBonerScript extends PollingScript<ClientContext> implements PaintListener  {

	//http://prntscr.com/5ka1is
	
	/** The different colors and fonts used in the GUI */
	public static final Color GRAY_COLOR = new Color(98, 98, 98), MIDDLE_GRAY_COLOR = new Color(110, 110, 110), LIGHT_GRAY_COLOR = new Color(128, 128, 128);
	public static final Font BIG_FONT = new Font("Candara", Font.BOLD, 20), NORMAL_FONT = new Font("Candara", Font.BOLD, 16), SMALL_FONT = new Font("Candara", Font.BOLD, 14); 
	
	/** Brass Key ID */
	private static final int KEY_ID = 983;
	
	/** Start time, didnt use built in getRuntime() because I needed it to be static */
	private static long startTime;
	
	/** Holds all tasks. */
	private ArrayList<Task> tasks = new ArrayList<Task>();
	
	@Override
	public void start() {
		startTime = System.currentTimeMillis();
		if (ctx.backpack.select().id(KEY_ID).isEmpty()) { //If the brass key isnt found then stop
			JOptionPane.showMessageDialog(null, "Please make sure you have a \"Brass Key\" in your inventory then restart.");
			this.stop();
		} else {
			ScreenManager.getInstance().setScreen(new MainScreen(ctx)); //Set to the main screen
			tasks.addAll(Arrays.asList(new WalkingTask(ctx), new PickUpManager(ctx), new AntiBan(ctx), new FullInv(ctx), new InvCheck(ctx)));
		}
	}
	
	/** Executes all tasks. */
	@Override
	public void poll() {
		for (Task t : tasks)
			if (t.activate())
				t.execute();
	}

	/** Paints the mouse coordinates and the screens. */
	@Override
	public void repaint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		ScreenManager.getInstance().update();
		ScreenManager.getInstance().draw(g2d);
		g2d.setColor(Color.WHITE);
		g2d.drawLine(0, ctx.mouse.getLocation().y, ctx.client().getCanvas().getWidth(), ctx.mouse.getLocation().y);
		g2d.drawLine(ctx.mouse.getLocation().x, 0, ctx.mouse.getLocation().x, ctx.client().getCanvas().getHeight());

	}
	
	/** Static method to get the runtime. */
	public static long getScriptRuntime() {
		return System.currentTimeMillis() - startTime;
	}

}

/** The settings screen which lets the user change the configurations. */
class SettingsScreen extends Screen implements MouseListener {

	/** The back button to go to the main screen. */
	private Button backBtn = new TextButton("Back", 300, 95, 150, 50);
		
	/** Checkbox to bury bones instead of bank them. */
	private CheckBox buryChk = new CheckBox(150, 90);
	
	/** Checkbox to bank bones instead of bury them. */
	private CheckBox bankChk = new CheckBox(150, 140);
	
	/** Sets context and sets the checkboxes to the boolean value of the config.
	 * 
	 * @param ctx Context reference
	 */
	public SettingsScreen(ClientContext ctx) {
		super(ctx);
		bankChk.setChecked(Config.BANK_BONES);
		buryChk.setChecked(Config.BURY_BONES);
	}

	@Override
	public void create() {
		//Adds the mouse listener to the canvas
		ctx.client().getCanvas().addMouseListener(this);
	}

	@Override
	public void update() {
		
	}
	
	/** Called when the mouse is clicked. Checks if any buttons or checkboxes were pressed.
	 * 
	 * @param x The mouse x 
	 * @param y The mouse y
	 */
	private void checkInput(int x, int y) {
		if (backBtn.clicked(x, y)) {
			ScreenManager.getInstance().setScreen(new MainScreen(ctx)); //Change back to main screen
		} else if (bankChk.clicked(x, y)) { 
			if (bankChk.checked()) { //Set it to bank instead of bury
				buryChk.setChecked(false);
				Config.BANK_BONES = true;
				Config.BURY_BONES = false;
			} else
				bankChk.setChecked(true);
		} else if (buryChk.clicked(x, y)) {
			if (buryChk.checked()) { //Set it to bury instead of bank
				Config.BANK_BONES = false;
				Config.BURY_BONES = true;
				bankChk.setChecked(false);
			} else
				buryChk.setChecked(true);
		}
	}

	/** Draws the GUI. */
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(BigBonerScript.GRAY_COLOR);
		g2d.fillRect(10, 10, 500, 175);
		g2d.setColor(BigBonerScript.LIGHT_GRAY_COLOR);
		g2d.fillRect(15, 15, 490, 165);

		g2d.setColor(BigBonerScript.GRAY_COLOR);
		g2d.fillRect(160, 15, 197, 50);
		
		g2d.setFont(BigBonerScript.BIG_FONT);
		g2d.setColor(Color.WHITE);
		g2d.drawString("Big Boner Version 1.0", 170, 35);
		
		g2d.setFont(BigBonerScript.SMALL_FONT);
		g2d.drawString("Developed by Baseball435", 180, 50);
		
		g2d.setFont(BigBonerScript.NORMAL_FONT);
		g2d.drawString("Bury Bones: ", 45, 110);
		g2d.drawString("Bank Bones: ", 45, 160);
		backBtn.draw(g2d);
		buryChk.draw(g2d);
		bankChk.draw(g2d);
	}

	@Override
	public void destroy() {
		ctx.client().getCanvas().removeMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		checkInput(e.getX(), e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
}

/** Main screen which displays the time running and bones collected. */
class MainScreen extends Screen implements MouseListener {
	
	/** Button that when pressed goes to the settings screen. */
	private Button settingsBtn = new TextButton("Settings", 300, 95, 150, 50);
	
	public MainScreen(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void create() {
		//Adds the mouse listener to the canvas
		ctx.client().getCanvas().addMouseListener(this);
	}

	@Override
	public void update() {
		
	}
	
	/** Called whenever the mouse is clicked.
	 * 
	 * @param x The mouse x
	 * @param y The mouse y
	 */
	private void checkInput(int x, int y) {
		if (settingsBtn.clicked(x, y)) { //If the settings button is clicked
			ScreenManager.getInstance().setScreen(new SettingsScreen(ctx)); //Go to the settings screen
		}
	}

	/** Draws the screen and information. */
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(BigBonerScript.GRAY_COLOR);
		g2d.fillRect(10, 10, 500, 175);
		g2d.setColor(BigBonerScript.LIGHT_GRAY_COLOR);
		g2d.fillRect(15, 15, 490, 165);

		g2d.setColor(BigBonerScript.GRAY_COLOR);
		g2d.fillRect(160, 15, 197, 50);
		
		g2d.setFont(BigBonerScript.BIG_FONT);
		g2d.setColor(Color.WHITE);
		g2d.drawString("Big Boner Version 1.0", 170, 35);
		
		g2d.setFont(BigBonerScript.SMALL_FONT);
		g2d.drawString("Developed by Baseball435", 180, 50);
		
		g2d.setFont(BigBonerScript.NORMAL_FONT);
		if (BigBonerScript.getScriptRuntime() > 0) {
			int toSeconds = (int)BigBonerScript.getScriptRuntime() / 1000;
			int minutes = toSeconds / 60;
			int hours = minutes / 60;
			int seconds = toSeconds % 60;
			
			g2d.drawString("Time Running: " + hours + ":" + minutes + ":" + seconds, 35, 100);
			g2d.drawString("Bones Gathered: " + InvCheck.bonesPickedUp, 35, 150);
		}
		settingsBtn.draw(g2d);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		checkInput(e.getX(), e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void destroy() {
		ctx.client().getCanvas().removeMouseListener(this);
	}
	
}

/** Singleton class that manages the screens. */
class ScreenManager {
	
	/** Singleton instance. */
	private static final ScreenManager instance = new ScreenManager();
	
	/** Current screen showing. */
	private Screen currentScreen;
	
	/** Private constructor. */
	private ScreenManager() {
	}
	
	/** Updates the current screen. */
	public void update() {
		if (currentScreen != null)
			currentScreen.update();
	}
	
	/** Draws the current screen. 
	 * 
	 * @param g2d The graphics object
	 */
	public void draw(Graphics2D g2d) {
		if (currentScreen != null)
			currentScreen.draw(g2d);
	}
	
	/** Sets the current screen to a new screen.
	 * 
	 * @param screen The new screen to display
	 */
	public void setScreen(Screen screen) {
		if (currentScreen != null)
			currentScreen.destroy();
		currentScreen = screen;
		currentScreen.create();
	}
	
	/** Returns the singleton instance. */
	public static ScreenManager getInstance() {
		return instance;
	}
	
}

/** Abstract class that all screens extend. */
abstract class Screen {
	
	/** Instance of the context. */
	protected ClientContext ctx;
	
	/** Creates a new screen and sets the context instance.
	 * 
	 * @param ctx Reference to the context
	 */
	public Screen(ClientContext ctx) {
		this.ctx = ctx;
	}
	
	/** Called when set to display. */
	public abstract void create();
	
	/** Called each frame. */
	public abstract void update();
	
	/** Called each frame to draw to the screen. */
	public abstract void draw(Graphics2D g2d);
	
	/** Called when another screen is set. */
	public abstract void destroy();
	
}

class CheckBox extends Button {
	
	/** Whether or not it's checked. */
	private boolean checked = false;
	
	/** Sets the x and y coordinates.
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public CheckBox(int x, int y) {
		super(x, y, 30, 30);
	}
	
	/** If it is clicked then set whether it's checked to what it's not. */
	@Override
	public boolean clicked(int mx, int my) {
		boolean b = super.clicked(mx, my);
		if (b)
			checked = !checked;
		return b;
	}
	
	/** @return Whether it's checked. */
	public boolean checked() {
		return checked;
	}
	
	/** Sets whether it's checked.
	 * 
	 * @param b Whether it's checked.
	 */
	public void setChecked(boolean b) {
		checked = b;
	}
	
	/** Draws the checkbox and the check if it's checked. */
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		if (checked) {
			g2d.setColor(Color.GREEN);
			g2d.drawLine(x + 5, y + 5, x + w - 5, y + h - 5);
			g2d.drawLine(x + w - 5, y + 5, x + 5, y + h - 5);
		}
	}
	
}

class Button {
	
	/** The position and dimension variables. */
	protected int x, y, w, h;
	
	/** Sets the position and dimension variables. */
	public Button(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	/** @return Whether the click coordinates are within the bounds.
	 * 
	 * @param mx The clicked mouse x
	 * @param my The clicked mouse y
	 * @return Whether it was clicked
	 */
	public boolean clicked(int mx, int my) {
		return mx >= x && mx <= x + w && my >= y && my <= y + h;
	}
	
	/** Draws the background of the button. */
	public void draw(Graphics2D g2d) {
		g2d.setColor(BigBonerScript.GRAY_COLOR);
		g2d.fillRect(x, y, w, h);
		g2d.setColor(BigBonerScript.MIDDLE_GRAY_COLOR);
		g2d.fillRect(x + 5, y + 5, w - 10, h - 10);
	}
	
}

class TextButton extends Button{
	
	/** Holds a string to draw on the button. */
	private String str;
	
	/** Sets the position, dimension, and string instance variables.
	 * 
	 * @param str The string to draw
	 * @param x The x position
	 * @param y The y position
	 * @param w The width
	 * @param h The height
	 */
	public TextButton(String str, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.str = str;
	}
	
	/** Draws the string in the center of the button. */
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		g2d.setColor(Color.WHITE);
		int width = g2d.getFontMetrics().stringWidth(str);
		int height = g2d.getFontMetrics().getHeight();
		g2d.drawString(str, x + (w / 2) - (width / 2), y + (h / 2) + (height / 2) - 5);
	}
	
}

class Config {
	
	/** Whether to bury the bones. */
	public static boolean BURY_BONES = false;
	
	/** Whether to bank the bones. */
	public static boolean BANK_BONES = true;
	
}

/** Monitors the inventory to see when bones are picked up. */
class InvCheck extends Task {

	/** The total amount of bones picked up. */
	public static int bonesPickedUp = 0;
	
	/** Holds a count to find the difference from. */
	private int lastBoneCount = 0;
	
	public InvCheck(ClientContext ctx) {
		super(ctx);
		lastBoneCount = ctx.backpack.select().id(PickUpBones.BONE_ID).count(); //Get the initial count
	}

	@Override
	public boolean activate() {
		boolean b = ctx.backpack.select().id(PickUpBones.BONE_ID).count() > lastBoneCount; //If there are more than last check
		if (ctx.backpack.select().id(PickUpBones.BONE_ID).count() < lastBoneCount) //means we either buried or put them in the bank
			lastBoneCount = ctx.backpack.select().id(PickUpBones.BONE_ID).count(); //Set the count to the amount
		return b;
	}

	@Override
	public void execute() {
		bonesPickedUp += ctx.backpack.select().id(PickUpBones.BONE_ID).count() - lastBoneCount; //Set it to the difference
		lastBoneCount = ctx.backpack.select().id(PickUpBones.BONE_ID).count(); //Set the count
	}
	
}

/** Walks to the bank when inventory is full. */
class WalkToBank extends Task {
	
	/** The bank area. */
	private static final Area BANK_AREA = new Area(new Tile(3143, 3483), new Tile(3152, 3474));
	
	public WalkToBank(ClientContext ctx) {
		super(ctx);
	}

	/** If we are not in the giants den and outside of the house to go to the dungeon. */
	@Override
	public boolean activate() {
		return !WalkingTask.GIANTS_AREA.contains(ctx.players.local()) && !WalkingTask.IN_HOUSE_AREA.contains(ctx.players.local()) && ctx.players.local().stance() == 18019 && ctx.players.local().animation() == -1;
	}

	@Override
	public void execute() {
		if (!BANK_AREA.contains(ctx.players.local())) { //If we arent in the bank area
			ctx.movement.step(BANK_AREA.getRandomTile()); //Go to it!
		} else { 
			if (!ctx.bank.opened()) { //If the bank interface is not open
				if (!ctx.npcs.select().id(3293, 3416).isEmpty()) { //Find an npc to open it
					Npc banker = ctx.npcs.nearest().poll(); //Get the npc
					if (banker.inViewport()) { //If we can see them
						banker.interact("Bank"); //Open the bank
					} else { //Otherwise
						ctx.movement.step(banker.tile()); //Move to be able to see them
					}
				}
			} else { //if the bank is open then deposit and close the bank
				ctx.bank.deposit(PickUpBones.BONE_ID, ctx.backpack.select().id(PickUpBones.BONE_ID).count());
				ctx.bank.close();
				FullInv.doingAction = false;
			}
		}
	}
	
}

/** Leaves the house to the dungeon when the inventory is full. */
class LeaveHouse extends Task {
	
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

/** Leave the giants den when inventory is full. */
class LeaveGiants extends Task {

	/** The ladder to get out. */
	private static int LEAVE_LADDER_ID = 29355;
	
	public LeaveGiants(ClientContext ctx) {
		super(ctx);
	}

	/** If we are in the giants den. */
	@Override
	public boolean activate() {
		return WalkingTask.GIANTS_AREA.contains(ctx.players.local()) && ((ctx.players.local().stance() == 18019 && ctx.players.local().animation() == -1) || ctx.players.local().inCombat());
	}

	@Override
	public void execute() {
		if (!ctx.objects.select().id(LEAVE_LADDER_ID).isEmpty()) { //if we find the ladder
			GameObject ladder = ctx.objects.nearest().poll(); //Get the ladder
			if (ladder.inViewport()) //If we see it
				ladder.interact("Climb-up"); //Climb it
			else //Otherwise 
				ctx.movement.step(ladder.tile()); //Walk to it
		}
	}
	
}

/** Checks if the inventory is full and does the tasks depending on the config settings. */
class FullInv extends Task {

	/** Either in the process of banking or burying the bones. */
	public static boolean doingAction;
	
	/** The burying bones task. */
	private BuryBones buryBonesTask;
	
	/** The leave giants task. */
	private LeaveGiants leaveGiantsTask;
	
	/** The leave house task. */
	private LeaveHouse leaveHouseTask;
	
	/** The walk to bank task. */
	private WalkToBank walkToBankTask;
	
	public FullInv(ClientContext ctx) {
		super(ctx);
		buryBonesTask = new BuryBones(ctx);
		leaveGiantsTask = new LeaveGiants(ctx);
		leaveHouseTask = new LeaveHouse(ctx);
		walkToBankTask = new WalkToBank(ctx);
	}

	/** If the inventory is full or we are in the process of doing an action. */
	@Override
	public boolean activate() {
		return ctx.backpack.select().count() == 28 || doingAction;
	}

	@Override
	public void execute() {
		if (ctx.backpack.select().count() == 28) //if it's full then set doingAction to true
			doingAction = true;
		if (Config.BURY_BONES) { //if we are burying the bones and we are in the safe area or not in the giants den
			if (PickUpBones.SAFE_AREA.contains(ctx.players.local()) || !WalkingTask.GIANTS_AREA.contains(ctx.players.local())) {
				if (doingAction) { //If we're doing it
					if (buryBonesTask.activate()) { //If we can do the bones task then it means there are bones left
						buryBonesTask.execute(); //Bury them
					} else { //There are no bones left
						doingAction = false; //Stop the action
					}
				}
			} else {
				ctx.movement.step(PickUpBones.SAFE_AREA.getRandomTile()); //Move to the safe zone
			}
		} else if (Config.BANK_BONES) { //if we are banking them 
			if (leaveGiantsTask.activate()) //If we are still in the giants den then get out
				leaveGiantsTask.execute();
			if (leaveHouseTask.activate()) //If we are still in the house then get out
				leaveHouseTask.execute();
			if (walkToBankTask.activate()) //If we are not at the bank and havent banked then do it
				walkToBankTask.execute();
		}
		
	}
	
	/** @return Whether or not we are banking or burying. */
	public static boolean isDoingAction() {
		return doingAction;
	}
	
}

/** Buries the bones. Is a delayed task so it happens at random intervals. */
class BuryBones extends DelayedTask {

	public BuryBones(ClientContext ctx) {
		super(ctx, 200);
	}

	/** If there are bones left. */
	@Override
	public boolean activate() {
		return !ctx.backpack.id(PickUpBones.BONE_ID).isEmpty();
	}


	@Override
	public void execute() {
		if (passesTime(Random.nextInt(0, 300))) { //if the time is up
			if (!ctx.hud.opened(Window.BACKPACK)) //Open the backpack if its not
				ctx.hud.open(Window.BACKPACK);
			ctx.backpack.select().id(PickUpBones.BONE_ID).poll().interact("Bury"); //bury the first bone
		}
	}
	
}

/** Helps to ensure randomness. Is a delayed task so it happens at random intervals. */
class AntiBan extends DelayedTask {

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

/** Determines whether to pickup or not based on current health. */
class PickUpManager extends Task {

	/** Pick Up Bones task. */
	private PickUpBones pickUpTask;
	
	/** Health Check task. */
	private HealthCheck healthCheckTask;
	
	public PickUpManager(ClientContext ctx) {
		super(ctx);
		pickUpTask = new PickUpBones(ctx);
		healthCheckTask = new HealthCheck(ctx);
	}

	/** If we are in the giants den and are inventories not full. */
	@Override
	public boolean activate() {
		return WalkingTask.GIANTS_AREA.contains(ctx.players.local()) && ctx.backpack.select().count() != 28 && !FullInv.isDoingAction();
	}

	@Override
	public void execute() {
		if (healthCheckTask.activate()) { //If are health is below 30% dont pick up bones
			healthCheckTask.execute();
		} else { 
			if (pickUpTask.activate()) //Pick up bones if we can
 				pickUpTask.execute();
		}
	}
	
}

/** Checks if your health is above 30% to ensure you don't die. */
class HealthCheck extends Task {

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

/** Picks up bones off the ground. */
class PickUpBones extends Task {

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

/** Walks the player to the giants den whenever they are not in it. */
class WalkingTask extends Task {
	
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

/** Walks to the house when going to the giants den. */
class WalkToHouse extends Task {
	
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

/** Walks to the giants if not in it. */
class WalkToGiants extends Task {
	
	public WalkToGiants(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return true;
	}

	/** Move to a random position in it. */
	@Override
	public void execute() {
		ctx.movement.step(WalkingTask.GIANTS_AREA.getRandomTile());
	}
}

/** All tasks extend this class to be used to perform different... tasks :p */
abstract class Task extends ClientAccessor {

	public Task(ClientContext ctx) {
		super(ctx);
	}
	
	/** @return Whether or not to follow through with the task. */
	public abstract boolean activate();
	
	/** What to do in the task. */
	public abstract void execute();
	
}

/** Extends the task but allows the task to be delayed by a certain amount of time. */
abstract class DelayedTask extends Task {

	/** The amount of time to delay the task. */
	private int timeDelay;
	
	/** The last execution. */
	private long lastDelay;
	
	/** Takes the context and the time to delay the task.
	 * 
	 * @param ctx Reference to the context
	 * @param timeDelay The time to delay the task
	 */
	public DelayedTask(ClientContext ctx, int timeDelay) {
		super(ctx);
		this.timeDelay = timeDelay;
	}
	
	/** 
	 * @param random The random amount of time to add to the delay to randomize it
	 * @return Whether or not the time delay has passed
	 */
	protected boolean passesTime(int random) {
		boolean b = System.currentTimeMillis() - lastDelay >= timeDelay;
		if (b)
			lastDelay = System.currentTimeMillis();
		return b;
	}
	
}
