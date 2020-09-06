


/* View2.java */

package View;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class View2 extends JPanel {
	private JButton[] buttons;

	public View2() {
		buttons = new JButton[3];

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.BLACK);

		for (int i = 0; i < buttons.length; i++) {
			String text;
			JButton jButton;

			text = String.format("Test Case %d", i + 1);
			jButton = new JButton(text); 

			buttons[i] = jButton;
			add(buttons[i]);
		}
	}

	public void addButtonListeners(ActionListener[] buttonListeners) {
		for (int i = 0; i < Math.min(buttons.length, buttonListeners.length); i++) {
			buttons[i].addActionListener(buttonListeners[i]);
		}
	}
}
