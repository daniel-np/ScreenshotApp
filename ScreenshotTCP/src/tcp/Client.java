package tcp;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;

public class Client extends Observable implements Runnable {

	private Thread t;

	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	public void stop() {

	}

	@Override
	public void run() {

		byte[] addr = { 127, 0, 0, 1 };

		String message;
		BufferedImage image;
		try {
			message = "Client started...";
			messageOut(message);
			InetAddress host = InetAddress.getByAddress(addr);
			Socket klientSocket = new Socket(host, 5194);
			message = "Connected to: " + klientSocket.getInetAddress().getHostAddress();
			messageOut(message);

			InputStream inputStream = klientSocket.getInputStream();

			DataInputStream input = new DataInputStream(inputStream);

			int counter = input.readInt();

			String path = "/Users/Daniel/Desktop/Egenprosjekter/git/ScreenshotTCP/Screenshots/" + "Screenshot" + counter
					+ ".png";

			message = "Starting transaction...";
			messageOut(message);
			// Receive width, heigh and rgb
			int width = input.readInt();
			int height = input.readInt();

			int[][] rgb = new int[width][height];
			double size = width * height;
			double progress = 0;
			double currentProgress = 0;

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					rgb[i][j] = input.readInt();

					if (progress % (size / 100) == 0) {
						message = "Receiving: ";
						currentProgress = ((1 - progress / size) * 100);
						message += String.format("%.1f", currentProgress);
						message += "% remaining.";
						messageOut(message);
					}
					progress++;
				}
			}
			message = "Transaction complete!";
			messageOut(message);
			image = Screenshot.constructImage(rgb, 1);

			Screenshot.saveImage(path, image);
			message = "Screenshot saved!";
			messageOut(message);
			
			klientSocket.close();
		} catch (IOException io) {
			io.printStackTrace();

		}
	}

	private void messageOut(String message) {
		setChanged();
		notifyObservers(message);
	}

}
