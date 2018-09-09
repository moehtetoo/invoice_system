package com.acm.javafx.invoicesystem.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class WidthHeightProperty {
	private static DoubleProperty width= new SimpleDoubleProperty();
	private static DoubleProperty height= new SimpleDoubleProperty();
	public static double getWidth() {
		return width.get();
	}
	public static void setWidth(double width) {
		WidthHeightProperty.width.set(width);
	}
	public static double getHeight() {
		return height.get();
	}
	public static void setHeight(double height) {
		WidthHeightProperty.height.set(height);
	}
	public static DoubleProperty widthProperty() {
		return width;
	}
	public static DoubleProperty heightProperty() {
		return height;
	}
	
}
