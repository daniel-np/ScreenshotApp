package tcp;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;

import org.apache.commons.validator.routines.InetAddressValidator;

public class Client extends Observable implements Runnable {

	private Thread t;
	private byte[] address = { 127, 0, 0, 1 };
	private boolean transferInProgress = false;
	private long startTime, endTime, duration;
	private boolean isRunning = false;
	private int screenshotTimer = 10000;

	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	@Override
	public void run() {
		String message;
		BufferedImage image;
		try {
			
			message = "Client started...";
			messageOut(message);

			while (isRunning) {
				transferInProgress = true;
				InetAddress host = InetAddress.getByAddress(address);
				Socket klientSocket = new Socket(host, 5194);
				message = "Connected to: " + klientSocket.getInetAddress().getHostAddress();
				messageOut(message);
				
				InputStream inputStream = klientSocket.getInputStream();
				BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
				DataInputStream input = new DataInputStream(bufferedInputStream);

				int counter = input.readInt();

				String path = "/Users/Daniel/Desktop/Egenprosjekter/git/ScreenshotTCP/Screenshots/" + "Screenshot"
						+ counter + ".png";

				message = "Starting transaction...";

				// Duration
				startTime = System.nanoTime();

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
				transferInProgress = false;
				// Duration
				endTime = System.nanoTime();
				duration = (endTime - startTime) / 1000000;// time in seconds
				message = "Transaction completed in " + duration + "ms!";
				messageOut(message);

				image = Screenshot.constructImage(rgb, 1);

				Screenshot.saveImage(path, image);
				message = "Screenshot saved!";
				messageOut(message);

				klientSocket.close();
				
				waitForScreenshot(screenshotTimer);
			}
		} catch (IOException io) {
			io.printStackTrace();

		}

	}

	private void messageOut(String message) {
		setChanged();
		notifyObservers(message);
	}

	private synchronized void waitForScreenshot(int timer) {
		try {
			this.wait(timer);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getAddress() {
		String stringAddress = "";
		for (int i = 0; i < address.length - 1; i++) {
			stringAddress += String.valueOf(address[i] + ".");
		}
		stringAddress += String.valueOf(address[address.length - 1]);

		return stringAddress;
	}

	public void setAddress(String address) {
		if (transferInProgress == false) {
			InetAddressValidator validator = new InetAddressValidator();
			if (validator.isValidInet4Address(address)) {

				byte[] ipBytes = new byte[4];
				String[] array = address.split("\\.");

				for (byte x : ipBytes) {
					for (String y : array) {
						x = (byte) Integer.parseInt(y);
					}
				}

				this.address = ipBytes;
				messageOut("Ip-address set to: " + address);
			} else {
				messageOut("Invalid ip-address!");
			}
		} else {
			messageOut("Transfer in progress!");
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public int getScreenshotTimer() {
		return screenshotTimer;
	}

	public void setScreenshotTimer(int screenshotTimer) {
		this.screenshotTimer = screenshotTimer;
	}

}
