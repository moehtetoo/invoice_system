package com.acm.javafx.invoicesystem.dao;

import java.util.ArrayList;
import java.util.List;

import com.acm.javafx.invoicesystem.model.OthersInvoiceItems;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OthersInvoiceDaoImpl implements InvoiceDao{
	private static ObservableList<OthersInvoiceItems> itemsList;
	private static String file;
	private static StringProperty header= new SimpleStringProperty();
	private  int totalAmount;
	private static int index;
	private static final String[] CREDIT_TERMS= {"One Month","Two Week","Others"};
	private static final String[] OTHERS_TYPE= {" 21st Century"," TruLife"," Super Tech"," Nature Organics"," Hype Energy"};
	private static final String[] MODERN_TRADE= {" City Mart Holding"," Health & Beauty Store"};
	private static List<String> itemName= new ArrayList<>();
	public static String[] getModernTrade() {
		return MODERN_TRADE;
	}

	static {
		itemsList= FXCollections.observableArrayList();
	}
	
	public static ObservableList<OthersInvoiceItems> getItemsList() {
		return itemsList;
	}

	public static void setItemsList(ObservableList<OthersInvoiceItems> itemsList) {
		OthersInvoiceDaoImpl.itemsList = itemsList;
	}

	public  int getTotalAmount() {
		return totalAmount;
	}

	public  void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	/*
	 * to store invoice items 
	 */
	public static void addInvoiceItems(OthersInvoiceItems items) {
		itemsList.add(items);
	}
	/*
	 * calculate discount value if user selected discount type by percentage
	 */
	@Override
	public int calDiscountByPercentage(int totalAmount, int discount) {
		return (totalAmount*discount)/100;
	}
	/*
	 * calculate balance to pay 
	 */
	@Override
	public int calBalanceToPay(int totalAmount, int discountAmount) {
		return totalAmount-discountAmount;
	}
	/*
	 * get total amount
	 */
	@Override
	public int calTotalAmount() {
		totalAmount=0;
		itemsList.forEach(invoiceItem-> {
			totalAmount+=invoiceItem.getAmount();
		});
		return totalAmount;
	}

	public static int getIndex() {
		return index;
	}

	public static void setIndex(int index) {
		OthersInvoiceDaoImpl.index = index;
	}
	public static OthersInvoiceItems getInvoiceItem() {
		return getItemsList().get(getIndex());
	}
	public static void updateInvoiceItem(OthersInvoiceItems item) {
		getItemsList().set(getIndex(), item);
	}

	public static String getFile() {
		return file;
	}

	public static void setFile(String file) {
		OthersInvoiceDaoImpl.file = file;
	}

	public static String getHeader() {
		return header.get();
	}

	public static void setHeader(String header) {
		OthersInvoiceDaoImpl.header.set(header);
	}
	public static StringProperty headerProperty() {
		return header;
	}
	public static String[] getCreditTerms() {
		return CREDIT_TERMS;
	}

	public static String[] getOthersType() {
		return OTHERS_TYPE;
	}

	
	public static List<String> getItemName() {
		return itemName;
	}

	public int getPercentage(int amount,int discount) {
		return (discount*100)/amount;
	}

	public static void setItemName(List<String> itemName) {
		OthersInvoiceDaoImpl.itemName = itemName;
	}
	
}
