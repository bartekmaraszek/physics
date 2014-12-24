package pl.bmaraszek;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PhysicsPanel extends JFrame{
	
	private final Dimension size = new Dimension(800, 600);
	private final JPanel controlPanel;
	private final JPanel simulationPanel;
	
	public PhysicsPanel(JPanel controlPanel, JPanel simulationPanel){
		setLayout(new BorderLayout(0,0));
		this.add(controlPanel, BorderLayout.LINE_END);
		this.add(simulationPanel, BorderLayout.CENTER);
		
		this.controlPanel = controlPanel;
		this.simulationPanel = simulationPanel;
		
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setResizable(false);
		
		setTitle("Bartek Maraszek: Physics simulation");
		setVisible(true);
		setLocationRelativeTo(null);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
