package tcp;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedStream {
	private static PipedOutputStream output;
	private static PipedInputStream input;

	public PipedStream() {
		output = new PipedOutputStream();
		try {
			input = new PipedInputStream(output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static PipedOutputStream getOutput() {
		return output;
	}

	public static void setOutput(PipedOutputStream output) {
		PipedStream.output = output;
		String s = "";
	}

	public static PipedInputStream getInput() {
		return input;
	}

	public static void setInput(PipedInputStream input) {
		PipedStream.input = input;
	}

}
