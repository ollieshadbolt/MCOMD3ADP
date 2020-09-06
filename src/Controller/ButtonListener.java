


/* ButtonListener.java */

package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executor;

class ButtonListener implements ActionListener, Runnable {
	private Controller controller;
	private Executor executor;
	private int testCase;

	public ButtonListener(Controller controller, Executor executor, int testCase) {
		this.controller = controller;
		this.executor = executor;
		this.testCase = testCase;
	}

	public void actionPerformed(ActionEvent arg0) {
		executor.execute(this);
	}

	public void run() {
		controller.testCase(testCase);
	}
}
