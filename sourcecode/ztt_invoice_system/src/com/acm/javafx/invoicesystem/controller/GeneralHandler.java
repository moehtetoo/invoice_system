package com.acm.javafx.invoicesystem.controller;

import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GeneralHandler {
	public static final String phoneNumberRegEx = "\\d{2}-\\d{9}";
	public static final Pattern PHONE_PATTERN = Pattern.compile(phoneNumberRegEx);
	public static double parseDouble(String value) {
		try {
			return value.isEmpty()||value.equals("")? 0.0 : Double.parseDouble(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0.0;
	}
	public static int parseInteger(String value) {
		try {
			return value.isEmpty()? 0 : Integer.parseInt(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/* 
	 * for number validation and blank validation
	 */
	public static String validationField(String text) {
		if(!text.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
			return "This field must be number";
		}else if(text.isEmpty()) {
			return "This field cannot be blank";
		}else if(GeneralHandler.parseDouble(text)<=0) {
			return "This field must be greater than 0";
		}
		return null;
	}
	public static String blankValidation(String text) {
		return text.isEmpty() ? "This field cannot be blank" : "";
	}
	public static void showAlert(String title,AlertType alertType,String headerText,String contentText) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
	public static Alert showConfirmAlert(String title,AlertType alertType,String headerText,String contentText) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		return alert;
	}
	public static String toStringWithComma(int value) {
		return String.format("%,d", value);
	}
	public static String phoneFormatValidation(String text) {
		if(!PHONE_PATTERN.matcher(text).matches()) {
			return "Invalid phone number!  eg: 09-970320441";
		}
		return "";
	}
}
