


/* Floor.java */

package Model;

import java.util.ArrayList;
import Controller.Observer;

public class Floor implements Subject, Button, Runnable {
	private ArrayList<Observer> observers;
	private int sleepMilliseconds;
	private boolean isButtonPressed;
	private boolean areDoorsOpen;

	public Floor(int sleepMilliseconds, int floorIndex) {
		this.sleepMilliseconds = sleepMilliseconds;
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

	public void setIsButtonPressed(boolean isButtonPressed) {
		this.isButtonPressed = isButtonPressed;
		notifyObservers();
	}

	public boolean getAreDoorsOpen() {
		return areDoorsOpen;
	}

	private void setAreDoorsOpen(boolean areDoorsOpen) {
		this.areDoorsOpen = areDoorsOpen;
		notifyObservers();
	}

	public void run() {
		try {
			Thread.sleep(sleepMilliseconds);

			if (getAreDoorsOpen()) {
				setAreDoorsOpen(false);
			} else {
				setAreDoorsOpen(true);
			}

		} catch (InterruptedException interruptedException) {
			interruptedException.printStackTrace();
		}
	}
}
