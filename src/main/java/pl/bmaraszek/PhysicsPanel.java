package pl.bmaraszek;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class PhysicsPanel extends JFrame {

	private final Dimension size = new Dimension(800, 600);
	private final ControlPanel controlPanel;
	private final SimulationPanel simulationPanel;

	public PhysicsPanel(ControlPanel controlPanel, SimulationPanel simulationPanel) {
		setLayout(new BorderLayout(0, 0));
		this.add(controlPanel, BorderLayout.LINE_END);
		this.add(simulationPanel, BorderLayout.CENTER);

		this.controlPanel = controlPanel;
		this.simulationPanel = simulationPanel;
		controlPanel.setPhysicsPanel(this);
		simulationPanel.setPhysicsPanel(this);

		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setResizable(false);

		setTitle("Bartek Maraszek: Physics simulation");
		setVisible(true);
		setLocationRelativeTo(null);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	public SimulationPanel getSimulationPanel() {
		return simulationPanel;
	}

}
