package com.techelevator.view;

public class MenuItem {

	private String itemName;
	private double itemCost;
	private String itemCategory;
	private int itemCount = 5;
	
	public MenuItem(String itemName, String itemCost, String itemCategory) {
		this.itemName=itemName;
		this.itemCost=Double.parseDouble(itemCost);
		this.itemCategory=itemCategory;

	}
	
	public String getMenuItemName() {
		return itemName;
	}
	public double getMenuItemCost() {
		return itemCost;
	}
	public String getMenuItemCategory() {
		return itemCategory;
	}
	
	
	public String makeNoise() {
		if (itemCategory.equals("Chip")) {
			return "Crunch Crunch, Yum!";
		}
		else if(itemCategory.equals("Candy")) {
			return "Munch Munch,Yum!";
		}
		else if(itemCategory.equals("Drink")) {
			return "Gulg Gulg,Yum!";
		}
		else {
		return "Chew Chew,Yum!";
		}
	}	
		
	public int getItemCount() {
		return itemCount;
	}
	public void sellItem() {
		itemCount = (itemCount - 1);
	}
	
}
