package userInterface.swing;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ServerTextArea extends JTextArea {

//	static String message;
	
	public ServerTextArea(String name, int rows, int columns) {
		this.setEditable(false);
		this.setText(name);
		this.setRows(rows);
		this.setColumns(columns);
		
	}
}
