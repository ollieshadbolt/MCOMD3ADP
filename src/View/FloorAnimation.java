


/* FloorAnimation.java */

package View;

public class FloorAnimation implements Runnable {
	private final boolean floorDoor;
	private final int floor;
	private View3 view3;

	public FloorAnimation(boolean floorDoor, int floor, View3 view3) {
		this.floorDoor = floorDoor;
		this.floor = floor;
		this.view3 = view3;
	}

	public void run() {
		view3.runFloorAnimation(floorDoor, floor);
	}
}
