


/* Model.java */

package Model;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import Controller.Observer;

public class Model {
	private final int liftSleepMilliseconds;
	private ExecutorService executor;
	public final int floorsLength;
	private ArrayList<User> users;
	private Floor[] floors;
	private Lift lift;

	public Model(int liftSleepMilliseconds, int floorsLength) {
		this.liftSleepMilliseconds = liftSleepMilliseconds;
		this.floorsLength = floorsLength;

		format(0);
	}

	public void format(int floorIndex) {
		if (floorsLength < floorIndex) {
			IllegalArgumentException illegalArgumentException;
			String message;

			message = "floorsLength can't be less than floorIndex!";
			illegalArgumentException = new IllegalArgumentException(message);
			throw illegalArgumentException;
		}

		executor = Executors.newSingleThreadExecutor();
		lift = new Lift(liftSleepMilliseconds, floorIndex);
		floors = new Floor[floorsLength];
		users = new ArrayList<User>();

		for (int i = 0; i < floors.length; i++) {
			floors[i] = new Floor(1000, i);
		}
	}

	public void formatObservers() {
		for (User user : users) {
			user.formatObservers();
		}

		for (Floor floor : floors) {
			floor.formatObservers();
		}

		lift.formatObservers();
	}

	// USER
	public void addUser(User user) {
		users.add(user);
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	private User getUser(int userID) {
		for (User user : getUsers()) {
			if (user.ID == userID) {
				return user;
			}
		}

		IllegalArgumentException illegalArgumentException;
		String message;

		message = "userID not found!";
		illegalArgumentException = new IllegalArgumentException(message);
		throw illegalArgumentException;
	}

	public void registerUserObserver(int userID, Observer observer) {
		getUser(userID).registerObserver(observer);
	}

	public int getUserState(int userID) {
		return getUser(userID).getState();
	}

	public void setUserState(int userID, int state) {
		getUser(userID).setState(state);
	}

	public int getUserCreationFloorIndex(int userID) {
		return getUser(userID).creationFloorIndex;
	}

	public void executeUser(User user) {
		executor.execute(user);
	}

	// FLOOR
	public void registerFloorObserver(int floorIndex, Observer observer) {
		floors[floorIndex].registerObserver(observer);
	}

	public void notifyFloorObservers(int floorIndex) {
		floors[floorIndex].notifyObservers();
	}

	public boolean getIsFloorButtonPressed(int floorIndex) {
		return floors[floorIndex].getIsButtonPressed();
	}

	public void setIsFloorButtonPressed(int floorIndex, boolean buttonPressed) {
		floors[floorIndex].setIsButtonPressed(buttonPressed);
	}

	public boolean getAreFloorDoorsOpen(int floorIndex) {
		return floors[floorIndex].getAreDoorsOpen();
	}

	public void executeFloor(int floorIndex) {
		executor.execute(floors[floorIndex]);
	}

	// LIFT
	public void registerLiftObserver(Observer observer) {
		lift.registerObserver(observer);
	}

	public void notifyLiftObservers() {
		lift.notifyObservers();
	}

	public boolean getIsLiftButtonPressed() {
		return lift.getIsButtonPressed();
	}

	public void setIsLiftButtonPressed(boolean buttonPressed) {
		lift.setIsButtonPressed(buttonPressed);
	}

	public int getLiftFloorIndex() {
		return lift.getFloorIndex();
	}

	public void executeLift() {
		executor.execute(lift);
	}
}
