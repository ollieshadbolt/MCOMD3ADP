


/* LiftObserver.java */

package Controller;

public class LiftObserver implements Observer {
	private Controller controller;

	public LiftObserver(Controller controller) {
		this.controller = controller;
	}

	public void update() {
		controller.liftUpdate();
	}
}
