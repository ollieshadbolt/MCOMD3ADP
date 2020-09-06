


/* Controller.java */

package Controller;

import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import Model.Model;
import Model.User;
import View.View;

public class Controller {
	private int userSleepMilliseconds;
	private ExecutorService executor;
	private int floorCount;
	private Model model;
	private View view;

	public Controller(View view, Model model, int floorCount) {
		ActionListener[] buttonListeners;

		executor = Executors.newSingleThreadExecutor();
		userSleepMilliseconds = 1000;
		buttonListeners = new ActionListener[3];

		this.floorCount = floorCount;
		this.model = model;
		this.view = view;

		for (int i = 0; i < buttonListeners.length; i++) {
			buttonListeners[i] = new ButtonListener(this, executor, i + 1);
		}

		view.addButtonListeners(buttonListeners);
	}

	public void newUser(int userID, int floorIndex) {
		UserObserver userObserver;
		User user;

		userObserver = new UserObserver(userID, this);
		user = new User(floorIndex, userSleepMilliseconds, userID);

		model.addUser(user);
		view.addUser(user.creationFloorIndex, user.ID);
		model.registerUserObserver(userID, userObserver);
		user.notifyObservers();
	}

	public void format(int liftStartingIndex) {
		executor = Executors.newSingleThreadExecutor();

		model.formatObservers();
		model.format(liftStartingIndex);
		view.format();

		for (int i = 0; i < floorCount; i++) {
			model.registerFloorObserver(i, new FloorObserver(this, i));
		}

		model.registerLiftObserver(new LiftObserver(this));
	}

	private void moveUser(User user) {
		String event;
		event = String.format("User %s is moving", user.ID);

		view.moveUser(user.ID);
		model.executeUser(user);
		view.consoleReport(event);
	}

	private void setIsFloorButtonPressed(int floorIndex, boolean buttonPressed) {
		view.setFloorButton(buttonPressed, floorIndex);
		model.setIsFloorButtonPressed(floorIndex, buttonPressed);
	}

	private void setIsLiftButtonPressed(boolean buttonPressed) {
		view.setLiftButton(buttonPressed);
		model.setIsLiftButtonPressed(buttonPressed);
	}
	
	private void executeFloor(int floorIndex, boolean areDoorsOpen) {
		String event;
		event = String.format("Floor %s's doors are moving", floorIndex);

		view.consoleReport(event);
		view.setFloorDoorsOpened(areDoorsOpen, floorIndex);
		model.executeFloor(floorIndex);
		
	}

	public void userUpdate(int userID) {
		int creationFloorIndex;
		String event;

		creationFloorIndex = model.getUserCreationFloorIndex(userID);
		event = String.format("User %s is ", userID);

		switch (model.getUserState(userID)) {
			case 0:
				event += "standing on the floor";

				view.consoleReport(event);

				if (!model.getAreFloorDoorsOpen(creationFloorIndex) && 
					!model.getIsFloorButtonPressed(creationFloorIndex)) {
					model.setUserState(userID, 1);
				}

				break;

			case 1:
				event += "pressing the button on the floor";

				view.consoleReport(event);
				setIsFloorButtonPressed(creationFloorIndex, true);
				model.setUserState(userID, 0);

				break;

			case 2:
				event += "pressing the button in the lift";

				view.consoleReport(event);
				setIsLiftButtonPressed(true);
				model.setUserState(userID, 3);

				break;

			case 3:
				event += "standing in the lift";
				view.consoleReport(event);

				if (!model.getIsLiftButtonPressed()) {
					model.setUserState(userID, 2);
				} else {
					model.notifyFloorObservers(model.getUserCreationFloorIndex(userID));
				}

				break;

			case 4:
				event += "exiting the lift";
				view.consoleReport(event);
				model.notifyFloorObservers(model.getLiftFloorIndex());
				break;

			default:
				model.notifyFloorObservers(model.getLiftFloorIndex());
				break;
		}
	}

	public void floorUpdate(int floorIndex) {
		String event;
		event = String.format("Floor %s has its button ", floorIndex);

		if (!model.getIsFloorButtonPressed(floorIndex)) {
			event += "not ";
		}

		event += "pressed and its doors ";

		if (model.getAreFloorDoorsOpen(floorIndex)) {
			event += "open";
			view.consoleReport(event);

			if (model.getIsFloorButtonPressed(floorIndex)) {
				setIsFloorButtonPressed(floorIndex, false);
				return;
			}
			
			for (User user : model.getUsers()) {
				switch(user.getState()) {
					case 3:
						if (user.creationFloorIndex == floorIndex) {
							break;
						}

						user.setState(4);
						return;

					case 4:
						moveUser(user);
						return;
				}
			}

			for (User user : model.getUsers()) {
				switch(user.getState()) {
					case 0:
					case 2:
						if (user.creationFloorIndex != floorIndex) {
							break;
						}

						moveUser(user);
						return;
				}
			}

			executeFloor(floorIndex, false);
			return;
		}

		event += "closed";
		view.consoleReport(event);

		// Call lift or open doors
		if (model.getIsFloorButtonPressed(floorIndex)) {
			for (int i = 0; i < floorCount; i++) {
				if (i != floorIndex &&
					(model.getAreFloorDoorsOpen(i) ||
					model.getIsFloorButtonPressed(i))) {
					return;
				}
			}

			if (model.getLiftFloorIndex() == floorIndex) {
				executeFloor(floorIndex, true);
				return;
			}
			
			event = "Lift is moving";

			view.consoleReport(event);
			model.executeLift();

			return;
		}

		for (int i = 0; i < floorCount; i++) {
			if (model.getIsLiftButtonPressed() ||
				(i != model.getLiftFloorIndex() &&
				model.getIsFloorButtonPressed(i))) {
				setIsLiftButtonPressed(false);
				event = "Lift is moving";

				view.consoleReport(event);
				model.executeLift();

				return;
			}
		}
	}

	public void liftUpdate() {
		int liftFloorIndex;
		String event;

		liftFloorIndex = model.getLiftFloorIndex();
		event = String.format("Lift is on floor %s with the button ", liftFloorIndex);

		if (model.getIsLiftButtonPressed()) {
			event += "pressed";
			view.consoleReport(event);

			// Set other floor button to pressed
			for (int i = 0; i < floorCount; i++) {
				if (liftFloorIndex != i &&
					!model.getIsFloorButtonPressed(i)) {
					setIsFloorButtonPressed(i, true);
					return;
				}
			}

			if (!model.getAreFloorDoorsOpen(liftFloorIndex)) {
				model.executeLift();
			}
		} else {
			event += "not pressed ";
			view.consoleReport(event);

			if (model.getIsFloorButtonPressed(liftFloorIndex)) {
				executeFloor(liftFloorIndex, true);
			}
		}
	}

	public void testCase(int testCase) {
		switch(testCase) {
			case 1:
				format(0);
				newUser(testCase, 0);
				break;

			case 2:
				format(1);
				newUser(testCase, 0);
				break;

			case 3:
				format(1);

				int lastUserID;
				lastUserID = testCase + 3;
				
				newUser(lastUserID, 1);

				for (int i = testCase; i < lastUserID; i++) {
					newUser(i, 0);
				}

				break;
		}
	}
}
