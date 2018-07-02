package userInterface.swing;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ServerTextArea extends JTextArea implements Observer {

	public ServerTextArea(String name, int rows, int columns) {
		this.setEditable(false);
		this.setText(name);
		this.setRows(rows);
		this.setColumns(columns);
	}

	@Override
	public void update(Observable o, Object arg) {
		append("\n");
		append((String) arg);
	}

}
