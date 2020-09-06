


/* User.java */

package Model;

import java.util.ArrayList;
import Controller.Observer;

public class User implements Subject, Runnable {
	private ArrayList<Observer> observers;
	public final int creationFloorIndex;
	private final int sleepMilliseconds;
	public final int ID;
	private int state;

	public User(int creationFloorIndex, int sleepMilliseconds, int ID) {
		if (sleepMilliseconds < 0) {
			IllegalArgumentException illegalArgumentException;
			String message;

			message = "runSleepMilliseconds can't be negative!";
			illegalArgumentException = new IllegalArgumentException(message);
			throw illegalArgumentException;
		}

		this.creationFloorIndex = creationFloorIndex;
		this.sleepMilliseconds = sleepMilliseconds;
		this.ID = ID;

		formatObservers();
	}

	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update();
		}
	}

	public void formatObservers() {
		observers = new ArrayList<Observer>();
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		if (getState() == state) {
			return;
		}

		this.state = state;
		notifyObservers();
	}

	public void run() {
		try {
			Thread.sleep(sleepMilliseconds);

			switch(getState()) {
				case 0:
					setState(3);
					break;

				case 4:
					setState(-1);
					break;
			}

		} catch (InterruptedException interruptedException) {
			interruptedException.printStackTrace();
		}
	}
}
