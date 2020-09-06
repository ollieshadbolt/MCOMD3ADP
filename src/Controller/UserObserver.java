


/* UserObserver.java */

package Controller;

public class UserObserver implements Observer {
	private Controller controller;
	private int userID;

	public UserObserver(int userID, Controller controller) {
		this.controller = controller;
		this.userID = userID;
	}

	public void update() {
		controller.userUpdate(userID);
	}
}
