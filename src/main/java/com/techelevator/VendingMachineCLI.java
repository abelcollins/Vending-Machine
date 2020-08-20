package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import com.techelevator.view.Menu;
import com.techelevator.view.MenuItem;
import com.techelevator.view.VendingMachine;

public class VendingMachineCLI{
	
	// String of Menu options
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTION_PURCHASE_OPTIONS = new String [] {"Feed Money","Select Product","Finish Transaction"};
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	
	//Array of Strings with Menu options
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};

	
	//Create object called menu from menu
	private Menu menu;
	//create object called vendingMachine
	private VendingMachine vendingMachine = new VendingMachine();
	
	
	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	//Getting choice from customer
	public void run() {
		//Create audit file for owner
		File auditFile = new File("AuditFile.txt");
		
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			// display vending machine items
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
			//Display    1) Feed Money     2) Select Product     3) Finish Transaction 	
				vendingMachine.displayItemsInInventory();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
			//call method to purchase options	
				purchaseItemsOptions();	
			}
		}
	}
	
	
	public static void main(String[] args) {
		
		
		Menu menu = new Menu(System.in, System.out);
		//created and object called cli from VendingMachineCLI
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		//used the method called run() 
		cli.run();
	}
	
	public void purchaseItemsOptions() {
		
		
			
		//created boolean to keep loop for transaction
		boolean inAPurchaseTransaction = true;
		//created string for the users choice
		String choice = "";
		//Create date formatter
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss");
		//Create Printwriter
		PrintWriter pw = null;
		//try and catch if the audit file cannot be created
		try {
			pw = new PrintWriter("AuditFile.txt");
		} catch (FileNotFoundException e1) {
			System.out.println("There was a problem writing the audit file!");
			e1.printStackTrace();
		}
		//while loop to keep user in transaction
		while(inAPurchaseTransaction & pw != null){
		//gets choice from user and checks it make sure it is valid
		choice= (String) menu.getChoiceFromOptions(MAIN_MENU_OPTION_PURCHASE_OPTIONS);
		try {
			
			
			if(choice.equals("Feed Money")) {
				//call method to get deposited amount and set to variable userInputForAmountToDeposit
				int userInputForAmountToDeposit = menu.getUserDepositedAmount();
				//call method to add that amount to current customer balance to updated it
				vendingMachine.addCustomerBalance(userInputForAmountToDeposit);
				//saves the deposit to audit file
				pw.println(dtf.format(LocalDateTime.now()) + " FEED MONEY: $" + userInputForAmountToDeposit +" $"+ Math.round(vendingMachine.getCustomerBalance()));
				//prints out customers deposit
				System.out.println(" ");
				System.out.println("Your current balance is " + vendingMachine.getCustomerBalance());
			}
			else if(choice.equals("Select Product")) {
				if(vendingMachine.getCustomerBalance() <=0) {
					//Print message that customer cannot buy because balance is 0
					System.out.println(" ");
					System.out.println("Your balance is 0, Feed Money.");
					
				}
				else { 
					//if balance is higher than 0 allow customer to purchase
					String userInputSlotNumber = menu.getSlotNumber(vendingMachine.getValidSlots());
					//make object of MenuItem
					MenuItem menuItem = vendingMachine.getMenuItem(userInputSlotNumber);
					//if item is available continue
					if (menuItem.getItemCount()> 0) {
					//if customer has enough to buy item continue	
					if (vendingMachine.getCustomerBalance()> menuItem.getMenuItemCost()) {
						//updated new customer balance
						vendingMachine.removeCustomerBalance(menuItem.getMenuItemCost());
						//update the inventory
						menuItem.sellItem();
						//Print to audit file
						pw.println(dtf.format(LocalDateTime.now()) + " "+ menuItem.getMenuItemName() + " " + userInputSlotNumber +" $"+ menuItem.getMenuItemCost()+" $"+ Math.round(vendingMachine.getCustomerBalance()));
						
						//make sound based on item selected
						System.out.println(menuItem.makeNoise());
							
					}
					//if customer doesn't have enough to buy item
					else {
						System.out.println(" ");
						System.out.println("Insufficent Funds");
					}
					}
					//if item is not available tell customer sold out
					else {
						System.out.println(" ");
						System.out.println("Sorry, this item is Sold Out");
					}
				}
			}
			//once customer chooses finish transaction
			else if(choice.equals("Finish Transaction")) {
				inAPurchaseTransaction = false;
				int numberOfQuarters = 0;
				int numberOfDimes= 0;
				int numberOfNickels= 0;
				int numberOfPennies= 0;
				double amountOfChange = vendingMachine.getCustomerBalance();
				
				if (vendingMachine.getCustomerBalance() > .25) {
					numberOfQuarters = (int) (vendingMachine.getCustomerBalance()/.25);  
					vendingMachine.setCustomerBalance(vendingMachine.getCustomerBalance() - (numberOfQuarters*.25));
				
				}
				if (vendingMachine.getCustomerBalance() > .10) {
					numberOfDimes = (int) (vendingMachine.getCustomerBalance() /.10); 
					vendingMachine.setCustomerBalance(vendingMachine.getCustomerBalance() - (numberOfDimes*.10));
				}
				if (vendingMachine.getCustomerBalance() > .05) {
					numberOfNickels = (int) (vendingMachine.getCustomerBalance() /.05); 
							vendingMachine.setCustomerBalance(vendingMachine.getCustomerBalance() - (numberOfNickels*.05));
				}
				if (vendingMachine.getCustomerBalance() > .01) {
					numberOfPennies = (int) (vendingMachine.getCustomerBalance() /.01); 
							vendingMachine.setCustomerBalance(vendingMachine.getCustomerBalance() - (numberOfPennies*.01));
				}
				//print out the customers change
				System.out.println(" ");
				System.out.println("Your change is " + numberOfQuarters + " Quaters, "+ numberOfDimes+" Dimes, "+ numberOfNickels + " Nickels, "+ numberOfPennies+" Pennies.");
				//save giving change transaction to audit file
				pw.println(dtf.format(LocalDateTime.now()) + " GIVE CHANGE: $" + amountOfChange +" $"+ Math.round(vendingMachine.getCustomerBalance()));
				pw.close();
				System. exit(0);
			}
			//makes sure the customer choose "Feed Money, Select Product or Finish Transaction
			else {
				System.out.println(" ");
				System.out.println("Please enter valid selection!");
			}
		} catch (NumberFormatException e) {
			// exception, an error message will be displayed below since choice will be null
		}
		
		}
		
	}
	
}

