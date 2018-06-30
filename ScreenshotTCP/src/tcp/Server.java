package tcp;

import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JTextArea;

public class Server implements Runnable {

	// to turn off server
	private AtomicBoolean isServerOn = new AtomicBoolean(true);
	private Thread t;

	// Timer may have to be atomic without lock or
	// volatile with lock
	private int timer = 30;

	JTextArea textArea;

	public Server() {
	}

	// Timer is still just index not real time!!! Fix in SwingFrame perhaps?
	public Server(int timer) {
		this.timer = timer;
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
		try {
			server = new ServerSocket(5194, 100);
			String message = "Server started...";
			// System.out.println(message);
			// textArea.setText(message);
			// PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));

			System.out.println();
			while (isServerOn.get()) {
				connection = server.accept();
				message = "Connection received from: " + connection.getInetAddress().getHostAddress();
				System.out.println(message);
				// Create outputstream
				DataOutputStream output = new DataOutputStream(connection.getOutputStream());

				output.writeInt(counter++);

				// Image screenshot part
				BufferedImage image = Screenshot.captureWholeScreen();
				int[][] rgb = Screenshot.deconstructImage(image);

				// Transmit image width and height as first and second transaction
				output.writeInt(image.getWidth());
				output.writeInt(image.getHeight());
				// Transmit image as a series of int

				boolean failed = false;
				message = "Sending to: " + connection.getInetAddress().getHostAddress();
				System.out.println(message);
				endTransaction: if (!failed)
					for (int i = 0; i < rgb.length; i++) {
						for (int j = 0; j < rgb[0].length; j++) {
							try {
								output.writeInt(rgb[i][j]);

							} catch (SocketException e) {
								message = "Transaction failed...";
								System.out.println(message);
								failed = true;
								break endTransaction;
							}
						}
					}
				if (!failed) {
					message = "Transaction complete!";
					System.out.println(message);
				}
			}
		} catch (EOFException eof) {
			String message = "Client terminated connection";
			System.out.println(message);
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public boolean getIsServerOn() {
		return isServerOn.get();
	}

	public void setIsServerOn(boolean newValue) {
		boolean existingValue = getIsServerOn();

		if (isServerOn.compareAndSet(existingValue, newValue))
			return;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public Thread getThread() {
		return t;
	}
}
