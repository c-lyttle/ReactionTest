import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class Reaction implements ActionListener {
	
	private JLabel welcomeLabel;
	private JLabel reactionLight;
	private JLabel reactionTimeLabel;
	private JLabel leaderboardTitle;
	private JFrame frame;
	private JPanel pane1;
	private JPanel pane2;
	private JTabbedPane tabbedPane;
	private JButton button;
	private JButton submitButton;
	private JTextField usernameInput;
	private Boolean clickState = false;
	private Boolean readyState = true;
	private Clock clock = Clock.systemDefaultZone();
	private long startTime = 0;
	private long endTime = 0;
	private long outTime = 0;
	DBManager dbManager = new DBManager();
	private int count = 1;
	
	public Reaction() {
		
		frame = new JFrame();
		
		tabbedPane = new JTabbedPane();
		
		button = new JButton("Click me when ready");
		button.addActionListener(this);
		
		welcomeLabel = new JLabel("Welcome to Reaction Time Tester", SwingConstants.CENTER);
		
		reactionLight = new JLabel("�", SwingConstants.CENTER);
		reactionLight.setForeground(Color.green);
		reactionLight.setFont(new Font("Calibri", Font.BOLD, 100));
		
		reactionTimeLabel = new JLabel(" ", SwingConstants.CENTER);
		
		pane1 = new JPanel();
		pane1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		pane1.setLayout(new GridLayout(0,1));
		pane1.add(welcomeLabel);
		pane1.add(reactionLight);
		pane1.add(button);
		pane1.add(reactionTimeLabel);
		
		leaderboardTitle = new JLabel("Leaderboard:", SwingConstants.CENTER);
		usernameInput = new JTextField("Enter your username here");
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this::submitUsername);
		
		pane2 = new JPanel();
		pane2.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		pane2.setLayout(new GridLayout(0,1));
		pane2.add(leaderboardTitle);
		
		for (String i:dbManager.getLeaderboard()) {
			JLabel lItem = new JLabel(count + ": " + i,SwingConstants.LEFT);
			lItem.setFont(new Font("Calibri", Font.PLAIN, 15));
			pane2.add(lItem);
			count++;
		}
		
		pane2.add(usernameInput);
		pane2.add(submitButton);
		
		tabbedPane.add("Game", pane1);
		tabbedPane.add("Leaderboard", pane2);
		
		frame.add(tabbedPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Reaction Time Tester");
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		//Creates new game object
		new Reaction();
	}

	//Called on button click
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//If the light is not yet red
		if (readyState == false) {
			reactionTimeLabel.setText("Too Early!");
			reactionLight.setForeground(Color.green);
			button.setText("Click me when ready");
			clickState = false;
			//Exits method early (used as a break)
			return;
		}
		
		//Switch the clickState
		clickState = !clickState;
		
		//Click to start game
		if (clickState==true) {
			readyState = false;
			button.setText("Don't Click");
			
			//Generates random time (1000-2500ms)
			int randomInt = (int)Math.floor(Math.random() * 15);
			randomInt = (randomInt+10)*100;
			
			//Timer is initialised and starts counting the random time
			Timer timer = new Timer(randomInt, this::timerMethod);
			timer.setRepeats(false);
			timer.start();
		}
		
		//Click to end game
		else {
			endTime = clock.millis();
			outTime = endTime-startTime;
			reactionLight.setForeground(Color.green);
			button.setText("Click me when ready");
			reactionTimeLabel.setText("Your reaction time is "+ outTime +"ms");
		}
	}
	
	//Called after 'timer' has counted for the random amount of time
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
	
	private void submitUsername(ActionEvent e) {
		String user = this.usernameInput.getText();
		long time = this.outTime;
		this.dbManager.appendItem(user, time);
	}

}
