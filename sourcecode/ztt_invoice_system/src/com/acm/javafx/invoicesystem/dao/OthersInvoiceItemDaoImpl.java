package com.acm.javafx.invoicesystem.dao;

import com.acm.javafx.invoicesystem.model.OthersInvoiceItems;

public class OthersInvoiceItemDaoImpl implements InvoiceItemsDao{
	private static int no;
	public static void increment() {
		setNo(getNo() + 1);
	}
	public static void decre() {
		setNo(getNo()-1);
	}
	@Override
	public int calculateTotal( OthersInvoiceItems oItems) {
		return oItems.getPrice()*oItems.getQuantity();
	}
	public static int getNo() {
		return no;
	}
	public static void setNo(int no) {
		OthersInvoiceItemDaoImpl.no = no;
	}

}
