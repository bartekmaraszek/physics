package pl.bmaraszek;

import javax.swing.JFrame;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimulationPanel s = new SimulationPanel();
		JFrame a = new PhysicsPanel(s);
		a.setVisible(true);
		s.start();
	}

}
