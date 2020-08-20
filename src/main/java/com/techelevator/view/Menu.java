package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Set;

public class Menu {

	private PrintWriter out;
	private Scanner in;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	// Gets user choice checks it to be valid and returns it
	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	// Makes sure user input is not negative or not an option
	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will
			// be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}

	//
	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		System.out.println(" ");
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}
	//method that will get the users deposited amount
	public int getUserDepositedAmount() {
		int choice = -1;
		while (choice < 0) {
			System.out.println(" ");
			out.println("How much would you like to deposit?");
			out.flush();
			String userInput = in.nextLine();
			try {
				int selectedAmount = Integer.valueOf(userInput);
				if (selectedAmount > 0 && selectedAmount <= 20) {
					choice = selectedAmount;
				}
			} catch (NumberFormatException e) {
				// eat the exception, an error message will be displayed below since choice will
				// be null
			}
			if (choice == -1) {
				out.println("\n*** " + userInput + " is not a valid option ***\n");
			}
		}
		return choice;

	}
	public String getSlotNumber(Set<String> validSlots) {
		String choice = null;
		while (choice == null) {
			System.out.println(" ");
			out.println("Enter Item Slot Number:");
			out.flush();
			String userInput = in.nextLine();
			if(!(validSlots.contains(userInput))) {
				choice = null;
			}
			else {
				choice = userInput;
			}
			if (choice == null) {
				out.println("\n*** " + userInput + " is not a valid slot number ***\n");
				out.flush();
			}
		}
		return choice;
	}
}
