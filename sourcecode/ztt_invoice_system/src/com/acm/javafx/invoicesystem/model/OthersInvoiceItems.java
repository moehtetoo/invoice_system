package com.acm.javafx.invoicesystem.model;

public class OthersInvoiceItems {
	private int itemsNo;
	private String itemName;
	private int quantity;
	private int price;
	private int amount;
	private int discount;
	private boolean isFOC;
	private String discountType;
	public int getItemsNo() {
		return itemsNo;
	}
	public void setItemsNo(int itemsNo) {
		this.itemsNo = itemsNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public boolean isFOC() {
		return isFOC;
	}
	public void setFOC(boolean isFOC) {
		this.isFOC = isFOC;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	
}
