import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;



public class Reaction implements ActionListener {
	
	private JLabel welcomeLabel;
	private JLabel reactionLight;
	private JFrame frame;
	private JPanel panel;
	private JButton button;
	private Boolean clickState = false;
	
	public Reaction() {
		
		frame = new JFrame();
		
		button = new JButton("Click me when ready");
		button.addActionListener(this);
		
		welcomeLabel = new JLabel("Welcome to Reaction Time Tester");
		
		reactionLight = new JLabel("•", SwingConstants.CENTER);
		reactionLight.setForeground(Color.green);
		reactionLight.setFont(new Font("Calibri", Font.BOLD, 50));
		
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.setLayout(new GridLayout(0,1));
		panel.add(welcomeLabel);
		panel.add(reactionLight);
		panel.add(button);
		
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Reaction Time Tester");
		frame.setSize(new Dimension(400,300));
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Reaction();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		reactionLight.setForeground(Color.green);
		button.setText("Don't Click");
		int randomInt = (int)Math.floor(Math.random() * 15);
		if (randomInt == 0) {
			randomInt = 5;
		}
		randomInt = randomInt*100;
		Timer timer = new Timer(randomInt, this::timerMethod);
		timer.setRepeats(false);
		System.out.println(randomInt);
		//wait(randomInt);
		timer.start();
	}
	
	private void timerMethod(ActionEvent e){
		reactionLight.setForeground(Color.red);
		button.setText("Click!");
	}

}
