package com.techelevator.view;

public class Inventory {


	private int itemCount = 5;
	
	public Inventory(int itemCount) {
	
		this.itemCount=itemCount;
	}
	
	public int getItemCount() {
		return itemCount;
	}
//	public void setItemCount(int numberOfItemBought) {
//		itemCount = (itemCount - numberOfItemBought);
//	}
	
}
