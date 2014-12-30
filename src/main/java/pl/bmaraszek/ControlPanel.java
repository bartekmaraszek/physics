package pl.bmaraszek;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pl.bmaraszek.integrators.IntegratorFactory;
import pl.bmaraszek.integrators.IntegratorFactory.IntegratorType;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {

	/* Parent physics panel */
	PhysicsPanel physicsPanel;
	public static Color bgColor = Color.BLUE;
	
	JButton verlet = new JButton("Verlet");
	JButton euler = new JButton("Euler");
	JButton rk4 = new JButton("RK4");
	JButton velocity = new JButton("Velocity Verlet");
	JLabel label = new JLabel("Verlet");

	ControlPanel() {
		verlet.setActionCommand(IntegratorFactory.IntegratorType.VERLET.name());
		euler.setActionCommand(IntegratorFactory.IntegratorType.EULER.name());
		rk4.setActionCommand(IntegratorFactory.IntegratorType.RK4.name());
		velocity.setActionCommand(IntegratorFactory.IntegratorType.VELOCITY_VERLET.name());
		
		label.setFont(new Font("Courier New", Font.BOLD, 12));
		label.setForeground(Color.gray);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new JButton("Button1"));
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(verlet);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(euler);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(rk4);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(verlet);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(label);
		this.setBackground(bgColor);
		this.setBorder(BorderFactory.createMatteBorder(0, 15, 0, 15, bgColor));
		this.setVisible(true);
	}

	public PhysicsPanel getPhysicsPanel() {
		return physicsPanel;
	}

	public void setPhysicsPanel(PhysicsPanel physicsPanel) {
		this.physicsPanel = physicsPanel;
		myActionListener l = new myActionListener(physicsPanel.getSimulationPanel().getWorld(), label);
		verlet.addActionListener(l);
		euler.addActionListener(l);
		rk4.addActionListener(l);
		velocity.addActionListener(l);
	}

}

class myActionListener implements ActionListener {

	Physics world;
	JLabel label;

	public myActionListener(Physics world, JLabel label) {
		this.world = world;
		this.label = label;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		IntegratorType actionCommand = IntegratorType.valueOf(e.getActionCommand());
		switch (actionCommand) {
		case VERLET:
			world.setIntegrator(IntegratorFactory.create(IntegratorType.VERLET));
			label.setText("VERLET");
			break;
		case EULER:
			world.setIntegrator(IntegratorFactory.create(IntegratorType.EULER));
			label.setText("EULER");
			break;
		case RK4:
			world.setIntegrator(IntegratorFactory.create(IntegratorType.RK4));
			label.setText("RK4");
			break;
		case VELOCITY_VERLET:
			world.setIntegrator(IntegratorFactory.create(IntegratorType.VELOCITY_VERLET));
			label.setText("VELOCITY VERLET");
		default:
			break;
		}
	}

}
