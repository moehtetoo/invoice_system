package com.acm.javafx.invoicesystem.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.acm.javafx.invoicesystem.controller.About;
import com.acm.javafx.invoicesystem.dao.BrandModel;
import com.acm.javafx.invoicesystem.dao.OthersInvoiceDaoImpl;
import com.acm.javafx.invoicesystem.model.OthersInvoice;
import com.acm.javafx.invoicesystem.property.WidthHeightProperty;
import com.jfoenix.controls.JFXRadioButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class Home implements Initializable{
	@FXML
	private StackPane view;
	@FXML
	private JFXRadioButton others;
	@FXML
	private JFXRadioButton cityMart;
	@FXML
	private ComboBox<String> othersType;
	@FXML
	private ImageView image;
	@FXML
	private HBox hTitle;
	@FXML
	private HBox hSale;
	@FXML
	private Button sales;
	@FXML
	private Button products;
	private Rectangle2D bounds= Screen.getPrimary().getVisualBounds();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		OthersInvoiceDaoImpl.setHeader(BrandModel.getModel().getAll().get(0).getName());
		OthersInvoice.setImage("21stCentury.png");
		this.initNode("Others");
		othersType.getItems().addAll(BrandModel.getModel().getAllName());
		fitWidthHeight();
		othersType.valueProperty().addListener(a -> {
			if(othersType!=null&&othersType.getSelectionModel().getSelectedItem()!=null&&othersType.getValue()!=null) {
			Notifications.create().title("Information").text("You change to "+othersType.getValue()+" !").showInformation();
			if(others.isSelected()&&othersType.getValue()!=null) {
			OthersInvoiceDaoImpl.setHeader(othersType.getValue());
			OthersInvoice.setImage(othersType.getValue().replaceAll(" ", "").concat(".png"));
			this.initNode("Others");
			}
			}
		});
		others.selectedProperty().addListener(a ->{ 
			setDataToComboBox();
		});
//		products.setOnAction(a-> {
//			hSale.setDisable(true);
//			this.initNode(products.getText());
//		});
//		sales.setOnAction(a -> {
//			hSale.setDisable(false);
//			this.initNode("Others");
//		});
	}
	public void setDataToComboBox() {
		if(others.isSelected()) {
			othersType.getItems().clear();
			othersType.getItems().addAll(BrandModel.getModel().getAllName());
			this.initNode("Others");
		}else {
			othersType.getItems().clear();
			othersType.getItems().addAll(OthersInvoiceDaoImpl.getModernTrade());
			this.initNode("CityMart");
		}
	}
	public void fitWidthHeight() {
		view.widthProperty().addListener(a -> {
			image.setFitWidth(bounds.getWidth()/1.8);
			hTitle.setPrefWidth(bounds.getWidth());
			othersType.setPrefWidth(bounds.getWidth()/5);
//			products.setPrefWidth(bounds.getWidth()/9);
//			sales.setPrefWidth(bounds.getWidth()/9);
			if(bounds.getWidth()>1366) {
				others.setFont(new Font(24));
				cityMart.setFont(new Font(24));
//				sales.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/acm/javafx/invoicesystem/icon/sale-24.png"))));
//				products.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/acm/javafx/invoicesystem/icon/product-24.png"))));
			}else {
//				products.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/acm/javafx/invoicesystem/icon/product-22.png"))));
//				sales.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/acm/javafx/invoicesystem/icon/sale-20.png"))));
				others.setFont(new Font(15));
				cityMart.setFont(new Font(15));
			}
			WidthHeightProperty.setWidth(view.getWidth());
		});
		view.heightProperty().addListener(a -> {
			image.setFitHeight(bounds.getHeight()/3);
			hTitle.setPrefHeight(bounds.getHeight()/15);
			othersType.setPrefHeight(bounds.getHeight()/22);
			WidthHeightProperty.setHeight(view.getHeight());
		});
		
	}
	
	/*
	 * This is for the view to set on the stack pane.
	 */
	public void initNode(String n) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(String.format("%s.fxml", n)));
			view.getChildren().clear();
			view.getChildren().add(loader.load());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	public void close() {
		othersType.getScene().getWindow().hide();
	}
	public void about() {
		About.show(othersType.getScene().getWindow());
	}
	}
