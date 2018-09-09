package com.acm.javafx.invoicesystem.dao;


public interface InvoiceDao {
	int calTotalAmount();
	int calDiscountByPercentage(int totalAmount,int discount);
	int calBalanceToPay(int totalAmount,int discountAmount);
}
