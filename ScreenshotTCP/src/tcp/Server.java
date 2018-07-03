package tcp;

import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;

public class Server extends Observable implements Runnable {

	private Thread t;
	private int timer;
	private boolean isRunning;

	public Server() {
	
	}

	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	public void run() {

		ServerSocket server;
		Socket connection;
		int counter = 1;
		String message;
		try {
			server = new ServerSocket(5194, 100);
			message = "Server started...";
			messageOut(message);
			while (true) {
				connection = server.accept();
				message = "Connection received from: " + connection.getInetAddress().getHostAddress();
				messageOut(message);
				// Create outputstream
				DataOutputStream output = new DataOutputStream(connection.getOutputStream());

				output.writeInt(counter++);

				// Image screenshot part
				BufferedImage image = Screenshot.captureWholeScreen();
				message = "Screen Captured";
				messageOut(message);
				int[][] rgb = Screenshot.deconstructImage(image);

				// Transmit image width and height as first and second transaction
				output.writeInt(image.getWidth());
				output.writeInt(image.getHeight());
				// Transmit image as a series of int

				boolean failed = false;
				message = "Sending to: " + connection.getInetAddress().getHostAddress();
				messageOut(message);
				endTransaction: if (!failed)
					for (int i = 0; i < rgb.length; i++) {
						for (int j = 0; j < rgb[0].length; j++) {
							try {
								output.writeInt(rgb[i][j]);

							} catch (SocketException e) {
								message = "Transaction failed...";
								messageOut(message);
								failed = true;
								break endTransaction;
							}
						}
					}
				if (!failed) {
					message = "Transaction complete!";
					messageOut(message);
				}
			}
		} catch (EOFException eof) {
			message = "Client terminated connection";
			messageOut(message);
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
	
	private void messageOut(String message) {
		setChanged();
		notifyObservers(message);
	}
	public int getTimer() {
		return timer;
	}
	public void setTimer(int timer) {
		this.timer = timer;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
