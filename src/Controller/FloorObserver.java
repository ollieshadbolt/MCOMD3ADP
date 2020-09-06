


/* FloorObserver.java */

package Controller;

public class FloorObserver implements Observer {
	private Controller controller;
	private int floorIndex;

	public FloorObserver(Controller controller, int floorIndex) {
		this.controller = controller;
		this.floorIndex = floorIndex;
	}

	public void update() {
		controller.floorUpdate(floorIndex);
	}
}
