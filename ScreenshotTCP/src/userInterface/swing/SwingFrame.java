package userInterface.swing;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import userInterface.UiControls;;

@SuppressWarnings("serial")
public class SwingFrame extends JFrame {
	
	UiControls uiControls = new UiControls();

	JPanel bgPanel = new JPanel();
	JPanel textAreaPanel = new JPanel();
	JPanel buttonAreaPanel = new JPanel();

	ClientTextArea clientOutput = new ClientTextArea("Client", 5, 10);
	ServerTextArea serverOutput = new ServerTextArea("Server", 5, 10);
	JScrollPane clientScrollPane = new JScrollPane(clientOutput);
	JScrollPane serverScrollPane = new JScrollPane(serverOutput);
	
	
	JButton startButton = new JButton("Start");
	JButton stopButton = new JButton("Stop");

	String[] choices = { "30 sec", "1 min", "10 min" };

	JComboBox<?> comboBox = new JComboBox<Object>(choices);



	public SwingFrame() {

		super("Swing App");
		setSize(500, 400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// JLabel message = new JLabel("What's up?");

		// Overview of layout
		// 		Grid(0,1)
		// 			Grid(0,2)
		// 				TextAreaClient
		// 				TextAreaServer
		// 			Flowlayout
		// 				ButtonStart/stop
		// 				DropDown

		
		bgPanel.setLayout(new GridLayout(0, 1));

			textAreaPanel.setLayout(new GridLayout(0, 2));
			clientScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			serverScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			textAreaPanel.add(clientScrollPane);
			textAreaPanel.add(serverScrollPane);
			

		bgPanel.add(textAreaPanel);
			buttonAreaPanel.setLayout(new FlowLayout());
			buttonAreaPanel.add(startButton);
				startButton.addActionListener((new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						uiControls.startServer(comboBox.getSelectedIndex());
						uiControls.startClient();
						startButton.setText("Stop");
					};
				}));
				buttonAreaPanel.add(comboBox);
		bgPanel.add(buttonAreaPanel);
		add(bgPanel);
		setVisible(true);
		
		uiControls.getClient().addObserver(clientOutput);
		uiControls.getServer().addObserver(serverOutput);
		
	}
}
