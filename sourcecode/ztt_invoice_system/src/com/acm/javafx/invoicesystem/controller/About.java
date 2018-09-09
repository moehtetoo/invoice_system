package com.acm.javafx.invoicesystem.controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class About {
	@FXML
	private JFXButton btn;
	public static void show(Window owner) {
		try {
			Parent root= FXMLLoader.load(EditInvoiceItem.class.getResource("/com/acm/javafx/invoicesystem/view/About.fxml"));
			Stage stage= new Stage();
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(owner);
			stage.setTitle("Update Item");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
			GeneralHandler.showAlert("Error", AlertType.ERROR, null,e.toString());
		}
	}
	public void close() {
		btn.getScene().getWindow().hide();
	}
}
