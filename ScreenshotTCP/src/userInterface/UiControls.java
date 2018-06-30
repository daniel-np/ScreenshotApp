package userInterface;

import java.io.IOException;

import tcp.Host;
import tcp.PipedStream;
import tcp.Server;

public class UiControls {

	Server server = new Server();
	Host host = new Host();
	PipedStream stream;

	/**
	 * Starts a server thread with default timer 30 sec
	 */
	public void startServer() {
		server.start();
	}

	/**
	 * Starts a server thread with specific timer
	 * 
	 * @param timer
	 * @throws IOException
	 */
	public void startServer(int timer) {
		if (stream == null)
			stream = new PipedStream();

		server.setTimer(timer);
		server.start();
	}

	/**
	 * Starts a host thread
	 * 
	 * @throws IOException
	 */
	public void startClient() {
		if (stream == null)
			stream = new PipedStream();
		host.start();
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

	}
	
	public PipedStream getStream() {
		return stream;
	}

}
