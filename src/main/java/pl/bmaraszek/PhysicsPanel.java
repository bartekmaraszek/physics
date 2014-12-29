package pl.bmaraszek;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PhysicsPanel extends JFrame{
	
	private final Dimension size = new Dimension(600, 600);
	
	public PhysicsPanel(JPanel simulationPanel){
		setLayout(new BorderLayout(0,0));
		this.add(simulationPanel, BorderLayout.CENTER);
		
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setResizable(false);
		
		setTitle("Bartek Maraszek: Physics simulation");
		setVisible(true);
		setLocationRelativeTo(null);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
