package userInterface;

import java.io.IOException;

import tcp.Client;
import tcp.Server;

public class UiControls {

	Server server = new Server();
	Client client = new Client();



	/**
	 * Starts a server thread
	 * 
	 * @throws IOException
	 */
	public void startServer() {
		server.start();
	}

	/**
	 * Starts a host thread with timer
	 * 
	 * @throws IOException
	 */
	public void startClient(int timer) {
		if (!client.isRunning()) {
			client.setRunning(true);
			client.start();
		} else {
			switch (timer) {
			case 0:
				client.setScreenshotTimer(30000);
				break;
			case 1:
				client.setScreenshotTimer(60000);
				break;
			case 2:
				client.setScreenshotTimer(600000);
				break;
			}
		}
	}

	public void pingServer() {
		// ICMP protocol not available in java
		// Open socket send 1 char byte '\n' or something and return it.
		// Maybe use UDP?
	}

	public void settings() {
		// Not sure what I want here
	}

	public void readLine() {

	}

	public void stopTransfer() {
		// Stop transfer while keeping connection open. If possible?
		// In the meantime, terminate host thread
//		client.setRunning(false);
		client.setScreenshotTimer(0);
	}

	public Server getServer() {
		return server;
	}

	public Client getClient() {
		return client;
	}

	public String getClientAddress() {
		return client.getAddress();
	}

	public void setClientAddress(String address) {
		client.setAddress(address);
	}

}
