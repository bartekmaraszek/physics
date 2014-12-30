package pl.bmaraszek;

import javax.swing.JFrame;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SimulationPanel s = new SimulationPanel();
		ControlPanel c = new ControlPanel();
		JFrame a = new PhysicsPanel(c, s);
		a.setVisible(true);
		s.start();
	}

}
