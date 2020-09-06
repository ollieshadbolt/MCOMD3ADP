


/* View3.java */

package View;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class View3 extends JPanel {
	ExecutorService executor = Executors.newSingleThreadExecutor();
	ArrayList<View3User> users;
	int levelCount = 2;

	int doorWidths[];
	private boolean[] floorButtons;
	private boolean liftButton;

	int floorHeight = 10;
	int floorY;
	int levelHeight;
	int maxDoorWidth = 55;
	int maxLiftWidth = maxDoorWidth * 2;
	int liftX;
	int doorHeight = 110;
	int liftY;
	int buttonWidth = 10;
	int buttonHeight = 10;
	int buttonX;
	int buttonY;

	public View3() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(300, 400));
		setBackground(Color.PINK);

		format();
	}
	
	public void format() {
		users = new ArrayList<View3User>();
		doorWidths = new int[levelCount];
		floorButtons = new boolean[levelCount];
		
		for (int i = 0; i < doorWidths.length; i++) {
			doorWidths[i] = maxDoorWidth;
		}

		repaint();
	}

	public void addUser(int creationFloorIndex, int userID) {
		View3User user;
		user = new View3User(creationFloorIndex, userID);
		users.add(user);
		repaint();
	}

	private View3User getUser(int userID) {
		for (View3User user : users) {
			if (user.ID == userID) {
				return user;
			}
		}

		IllegalArgumentException illegalArgumentException;
		String message;

		message = "UserID doesn't exist!";
		illegalArgumentException = new IllegalArgumentException(message);
		throw illegalArgumentException;
	}

	public void moveUser(int userID) {
		executor.execute(new UserAnimation(this, userID));
	}

	public void runUserAnimation(int userID) {
		View3User user;
		int timescale;
		int targetX;

		user = getUser(userID);

		if (user.getX() == 0) {
			targetX = liftX;
		} else if (user.getX() == liftX) {
			targetX = getWidth();
		} else {
			return;
		}

		timescale = 1000 / (targetX - user.getX());

		while (user.getX() < targetX) {
			try {	
				Thread.sleep(timescale);
				user.incrementX();
				repaint();
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}
	}

	public void setFloorDoor(boolean floorDoor, int floor) {
		executor.execute(new FloorAnimation(floorDoor, floor, this));
	}
	
	public void runFloorAnimation(boolean floorDoor, int floor) {
		int timescale;
		timescale = 1000 / maxDoorWidth;

		while ((doorWidths[floor] > 0 && floorDoor) || (doorWidths[floor] < maxDoorWidth && !floorDoor)) {
			if (floorDoor) {
				doorWidths[floor]--;
			}
			else {
				doorWidths[floor]++;
			}

			try {	
				Thread.sleep(timescale);
				repaint();
			}
			catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}
	}

	public void setFloorButton(Boolean pressed, int floor) {
		if (pressed == floorButtons[floor]) {
			return;
		}

		floorButtons[floor] = pressed;
		repaint();
	}
	
	public void setLiftButton(Boolean pressed) {
		if (pressed == liftButton) {
			return;
		}
		
		liftButton = pressed;
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		floorY = getHeight() - floorHeight;
		levelHeight = getHeight() / 2;
		liftX = getWidth() - maxLiftWidth - 50;
		liftY = floorY - doorHeight;
		buttonX = liftX - buttonWidth - 50;
		buttonY = floorY - buttonHeight - 25;

		for (int i = 0; i < 2; i++) {
			// FLOOR
			g.setColor(Color.GRAY);
			g.fillRect(0, floorY - (levelHeight * i), getWidth(), floorHeight);

			// LIFT
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(liftX, liftY - (levelHeight * i), maxLiftWidth, doorHeight);

			// FLOOR BUTTON
			if (floorButtons[i]) {
				g.setColor(Color.GREEN);
			}
			else {
				g.setColor(Color.RED);
			}
	
			g.fillRect(buttonX, buttonY - (levelHeight * i), buttonWidth, buttonHeight);

			// LIFT BUTTON
			if (liftButton) {
				g.setColor(Color.GREEN);
			}
			else {
				g.setColor(Color.RED);
			}

			if (doorWidths[i] != maxDoorWidth) {
				g.fillRect(liftX + maxDoorWidth - (buttonWidth / 2), buttonY - (levelHeight * i), buttonWidth, buttonHeight);
			}
		}

		// USERS
		ImageIcon userImage = new ImageIcon(getClass().getResource("user.png"));

		try {
			for (View3User user : users) {
			for (int i = 0; i < levelCount; i++) {
				if ((i == user.creationFloorIndex && user.getX() <= liftX) ||
					(i != user.creationFloorIndex && user.getX() >= liftX)) {
					int y;
					y = floorY - (levelHeight * i) - userImage.getIconHeight();

					userImage.paintIcon(this, g, user.getX(), y);
				}
			}
		}
		} catch (Exception exception) {
			//
		}

		for (int i = 0; i < 2; i++) {
			// DOORS
			g.setColor(Color.DARK_GRAY);
			g.fillRect(liftX, liftY - (levelHeight * i), doorWidths[i], doorHeight); // LEFT DOOR
			g.fillRect(liftX + (maxDoorWidth * 2) - doorWidths[i], liftY - (levelHeight * i), doorWidths[i], doorHeight); // RIGHT DOOR
		}
	}
}
