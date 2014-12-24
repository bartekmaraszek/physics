package pl.bmaraszek;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
	
	public static Color bgColor = Color.BLUE;
	
	ControlPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new JButton("Button1"));
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(new JButton("Button2"));
		this.setBackground(bgColor);
		this.setBorder(BorderFactory.createMatteBorder(0, 15, 0, 15, bgColor));
		this.setVisible(true);
	}
	
}
