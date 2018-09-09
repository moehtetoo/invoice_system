package com.acm.javafx.invoicesystem.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.acm.javafx.invoicesystem.property.WidthHeightProperty;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class CityMart implements Initializable{
	@FXML
	private HBox titleHBox;
	@FXML
	private ImageView hand;
	@FXML
	private Label titleHand;
	@FXML
	private BorderPane root;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fitWidth();
		fitHeight();
		fitWidthHeight();
	}
	public void fitWidthHeight() {
		WidthHeightProperty.widthProperty().addListener(c -> {
			fitWidth();
		});
		WidthHeightProperty.heightProperty().addListener(c -> {
			fitHeight();
		});
	}
	public void fitWidth() {
		root.setPrefWidth(WidthHeightProperty.getWidth()-20);
		if(WidthHeightProperty.getWidth()>1300) {
			hand.setFitWidth(36);
		}else {
			hand.setFitWidth(20);
		}
	}
	public void fitHeight() {
		root.setPrefHeight(WidthHeightProperty.getHeight()-20);
		titleHBox.setPrefHeight(WidthHeightProperty.getHeight()/40);
		if(WidthHeightProperty.getHeight()>1300) {
			hand.setFitHeight(36);
		}else {
			hand.setFitHeight(20);
		}
	}
}

