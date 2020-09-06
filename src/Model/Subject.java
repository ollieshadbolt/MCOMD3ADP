


/* Subject.java */

package Model;

import Controller.Observer;

public interface Subject {
	public void registerObserver(Observer observer);
	public void notifyObservers();
	public void formatObservers();
}
