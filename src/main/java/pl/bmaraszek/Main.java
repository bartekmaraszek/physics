package pl.bmaraszek;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimulationPanel s = new SimulationPanel();
		JFrame a = new PhysicsPanel(new ControlPanel(), s);
		a.setVisible(true);
		s.start();
	}

}
