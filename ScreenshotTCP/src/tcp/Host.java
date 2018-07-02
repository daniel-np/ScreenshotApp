package tcp;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Host implements Runnable {

	private Thread t;
	
	public void start() {
		if(t == null) {
			t = new Thread(this);
			t.start();
		}
	}
	
	public void stop() {
		
	}

	@Override
	public void run() {

		byte[] addr = { 127, 0, 0, 1 };

		BufferedImage image;
		try {
			InetAddress host = InetAddress.getByAddress(addr);
			Socket klientSocket = new Socket(host, 5194);
			System.out.println("Connected to: " + klientSocket.getInetAddress().getHostAddress());

			InputStream inputStream = klientSocket.getInputStream();

			DataInputStream input = new DataInputStream(inputStream);

			int counter = input.readInt();

			String path = "/Users/Daniel/Desktop/Egenprosjekter/git/ScreenshotTCP/Screenshots/" + "Screenshot" + counter
					+ ".png";

			System.out.println("Starting transaction...");
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

					if (progress % (size / 1000) == 0) {
						System.out.print("Receiving: ");
						currentProgress = ((1 - progress / size) * 100);
						System.out.printf("%.1f", currentProgress);
						System.out.println("% remaining.");
					}
					progress++;
				}
			}
			System.out.println("Transaction complete!");
			image = Screenshot.constructImage(rgb, 1);

			Screenshot.saveImage(path, image);

			klientSocket.close();
		} catch (IOException io) {
			io.printStackTrace();

		}
	}


}
