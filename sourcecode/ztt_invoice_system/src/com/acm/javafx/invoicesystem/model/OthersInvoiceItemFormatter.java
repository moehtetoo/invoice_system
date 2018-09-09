package com.acm.javafx.invoicesystem.model;

import com.acm.javafx.invoicesystem.dao.OthersInvoiceDaoImpl;

public class OthersInvoiceItemFormatter {
	private String no;
	private String itemName;
	private String quantity;
	private String price;
	private String amount;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}
