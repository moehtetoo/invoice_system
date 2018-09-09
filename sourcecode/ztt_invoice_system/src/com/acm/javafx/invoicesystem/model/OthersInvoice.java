package com.acm.javafx.invoicesystem.model;

import java.time.LocalDate;

public class OthersInvoice {
	private static String voucherNo;
	private static String customerName;
	private static String address;
	private static int discount;
	private static LocalDate date;
	private static String creditTerms;
	private static int totalAmount;
	private static int balanceToPay;
	private static boolean isCredit;
	private static String discountValue;
	private static String discountType;
	private static String image;
	private static String remarks;
	private static String phone;
	public static String getVoucherNo() {
		return voucherNo;
	}
	public static void setVoucherNo(String voucherNo) {
		OthersInvoice.voucherNo = voucherNo;
	}
	public static String getCustomerName() {
		return customerName;
	}
	public static void setCustomerName(String customerName) {
		OthersInvoice.customerName = customerName;
	}
	public static String getAddress() {
		return address;
	}
	public static void setAddress(String address) {
		OthersInvoice.address = address;
	}
	public static LocalDate getDate() {
		return date;
	}
	public static void setDate(LocalDate date) {
		OthersInvoice.date = date;
	}
	public static String getCreditTerms() {
		return creditTerms;
	}
	public static void setCreditTerms(String creditTerms) {
		OthersInvoice.creditTerms = creditTerms;
	}
	public static boolean isCredit() {
		return isCredit;
	}
	public static void setCredit(boolean isCredit) {
		OthersInvoice.isCredit = isCredit;
	}
	public static String getDiscountValue() {
		return discountValue;
	}
	public static void setDiscountValue(String discountValue) {
		OthersInvoice.discountValue = discountValue;
	}
	public static String getDiscountType() {
		return discountType;
	}
	public static void setDiscountType(String discountType) {
		OthersInvoice.discountType = discountType;
	}
	public static String getImage() {
		return image;
	}
	public static void setImage(String image) {
		OthersInvoice.image = image;
	}
	public static int getDiscount() {
		return discount;
	}
	public static void setDiscount(int discount) {
		OthersInvoice.discount = discount;
	}
	public static int getTotalAmount() {
		return totalAmount;
	}
	public static void setTotalAmount(int totalAmount) {
		OthersInvoice.totalAmount = totalAmount;
	}
	public static int getBalanceToPay() {
		return balanceToPay;
	}
	public static void setBalanceToPay(int balanceToPay) {
		OthersInvoice.balanceToPay = balanceToPay;
	}
	public static String getRemarks() {
		return remarks;
	}
	public static void setRemarks(String remarks) {
		OthersInvoice.remarks = remarks;
	}
	public static String getPhone() {
		return phone;
	}
	public static void setPhone(String phone) {
		OthersInvoice.phone = phone;
	}
	
}
