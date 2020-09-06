


/* View3User.java */

package View;

public class View3User {
	public final int creationFloorIndex;
	public final int ID;
	private int x;

	public View3User(int creationFloorIndex, int ID) {
		this.creationFloorIndex = creationFloorIndex;
		this.ID = ID;
	}

	public int getX() {
		return x;
	}

	public void incrementX() {
		x++;
	}
}
