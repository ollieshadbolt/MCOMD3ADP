


/* Lift.java */

package Model;

import java.util.ArrayList;
import Controller.Observer;

public class Lift implements Subject, Button, Runnable {
	private ArrayList<Observer> observers;
	private int runSleepMilliseconds;
	private boolean isButtonPressed;
	private int floorIndex;

	public Lift(int runSleepMilliseconds, int floorIndex) {
		if (runSleepMilliseconds < 0) {
			IllegalArgumentException illegalArgumentException;
			String message;

			message = "runSleepMilliseconds can't be negative!";
			illegalArgumentException = new IllegalArgumentException(message);
			throw illegalArgumentException;
		}

		this.runSleepMilliseconds = runSleepMilliseconds;
		this.floorIndex = floorIndex;

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

	public boolean getIsButtonPressed() {
		return isButtonPressed;
	}

	public void setIsButtonPressed(boolean buttonPressed) {
		this.isButtonPressed = buttonPressed;
		notifyObservers();
	}

	public int getFloorIndex() {
		return floorIndex;
	}

	private void setFloorIndex(int floorIndex) {
		this.floorIndex = floorIndex;
		notifyObservers();
	}

	public void run() {
		try {
			Thread.sleep(runSleepMilliseconds);

			if (floorIndex == 0) {
				setFloorIndex(1);
			} else {
				setFloorIndex(0);
			}
		} catch (InterruptedException interruptedException) {
			interruptedException.printStackTrace();
		}
	}
}
