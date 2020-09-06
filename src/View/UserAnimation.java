


/* UserAnimation.java */

package View;

public class UserAnimation implements Runnable {
	private final int userID;
	private View3 view3;

	public UserAnimation(View3 view3, int userID) {
		this.userID = userID;
		this.view3 = view3;
	}

	public void run() {
		view3.runUserAnimation(userID);
	}
}
