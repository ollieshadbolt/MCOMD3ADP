


/* View.java */

package View;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import Model.User;

@SuppressWarnings("serial")
public class View extends JFrame {
	View2 view2;
	View3 view3;

	public View(int floorsCount) {
		view2 = new View2();
		view3 = new View3();

		getContentPane().add(view2, BorderLayout.WEST);
		getContentPane().add(view3, BorderLayout.EAST);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
	}

	public void consoleReport(String event) {
		View1.consoleReport(event);
	}

	public void addButtonListeners(ActionListener[] buttonListeners) {
		view2.addButtonListeners(buttonListeners);
	}
	
	public void format() {
		view3.format();
	}

	public void addUser(int creationFloorIndex, int userID) {
		view3.addUser(creationFloorIndex, userID);
	}

	public void moveUser(int userID) {
		view3.moveUser(userID);
	}

	public void setFloorDoorsOpened(boolean floorDoorsOpened, int floor) {
		view3.setFloorDoor(floorDoorsOpened, floor);
	}

	public void setFloorButton(boolean floorButtonPressed, int floor) {
		view3.setFloorButton(floorButtonPressed, floor);
	}

	public void setLiftButton(boolean liftButtonPressed) {
		view3.setLiftButton(liftButtonPressed);
	}
}
