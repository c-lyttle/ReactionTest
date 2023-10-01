import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Clock;

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
	private JLabel reactionTimeLabel;
	private JFrame frame;
	private JPanel panel;
	private JButton button;
	private Boolean clickState = false;
	private Boolean readyState = true;
	private Clock clock = Clock.systemDefaultZone();
	private long startTime = 0;
	private long endTime = 0;
	private long outTime = 0;
	
	public Reaction() {
		
		frame = new JFrame();
		
		button = new JButton("Click me when ready");
		button.addActionListener(this);
		
		welcomeLabel = new JLabel("Welcome to Reaction Time Tester");
		
		reactionLight = new JLabel("•", SwingConstants.CENTER);
		reactionLight.setForeground(Color.green);
		reactionLight.setFont(new Font("Calibri", Font.BOLD, 50));
		
		reactionTimeLabel = new JLabel(" ", SwingConstants.CENTER);
		
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.setLayout(new GridLayout(0,1));
		panel.add(welcomeLabel);
		panel.add(reactionLight);
		panel.add(button);
		panel.add(reactionTimeLabel);
		
		
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
		if (readyState == false) {
			reactionTimeLabel.setText("Too Early!");
			reactionLight.setForeground(Color.green);
			button.setText("Click me when ready");
			clickState = false;
			return;
		}
		clickState = !clickState;
		if (clickState==true) {
			readyState = false;
			button.setText("Don't Click");
			int randomInt = (int)Math.floor(Math.random() * 15);
			if (randomInt == 0) {
				randomInt = 5;
			}
			randomInt = randomInt*100;
			Timer timer = new Timer(randomInt, this::timerMethod);
			timer.setRepeats(false);
			System.out.println(randomInt);
			timer.start();
		}
		else {
			endTime = clock.millis();
			outTime = endTime-startTime;
			reactionLight.setForeground(Color.green);
			button.setText("Click me when ready");
			reactionTimeLabel.setText("Your reaction time is "+ outTime +"ms");
		}
	}
	
	private void timerMethod(ActionEvent e){
		if (clickState == true) {
			startTime = clock.millis();
			readyState = true;
			reactionLight.setForeground(Color.red);
			button.setText("Click!");
		}
		else {
			readyState=true;
		}
	}

}
