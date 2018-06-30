package userInterface.text;

import java.util.Scanner;

import userInterface.UiControls;

public class TextMenu extends UiControls {

	Scanner scan = new Scanner(System.in);

	public void start() {
		System.out.println("Welcome to client!");
		System.out.println();
		menu();
	}

	private void menu() {
		System.out.println("(1) Start server");
		System.out.println("(2) Request screenshot");
		System.out.println("(3) Settings");
		System.out.println("(q) Quit client");

		menuInput();
	}

	private void menuInput() {
		while (true) {
			String input = scan.nextLine().toLowerCase();
			choices(input);
		}
	}

	private void choices(String input) {
		switch (input) {
		case "1":
			startServer();
			break;
		case "2":
			startClient();
			break;
		case "3":
			settings();
			break;
		case "q":
			System.exit(1);
			break;
		}
	}

}
