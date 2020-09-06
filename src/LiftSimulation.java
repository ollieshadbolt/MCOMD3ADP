


/* LiftSimulation.java */

import Model.Model;
import View.View;
import Controller.Controller;

public class LiftSimulation {
	public static void main(String[] args) {
		int liftSleepMilliseconds;
		int floorCount;
		Model model;
		View view;

		liftSleepMilliseconds = 5000;
		floorCount = 2;

		model = new Model(liftSleepMilliseconds, floorCount);
		view = new View(floorCount);

		new Controller(view, model, floorCount);
		view.setVisible(true);
	}
}
