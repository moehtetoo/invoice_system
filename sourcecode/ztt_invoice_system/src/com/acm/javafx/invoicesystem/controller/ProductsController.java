package com.acm.javafx.invoicesystem.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.acm.javafx.invoicesystem.dao.BrandModel;
import com.acm.javafx.invoicesystem.model.Brand;
import com.acm.javafx.invoicesystem.model.Product;
import com.acm.javafx.invoicesystem.property.WidthHeightProperty;
import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProductsController implements Initializable{
	@FXML
	private BorderPane root;
	@FXML
	private VBox leftVBox;
	@FXML
	private GridPane leftGrid;
	@FXML
	private ComboBox<String> brandName;
	@FXML
	private JFXButton newBrand;
	@FXML
	private JFXButton addProduct;
	@FXML
	private JFXButton clear;
	@FXML
	private RadioButton active;
	@FXML
	private TextField productName;
	@FXML
	private TextField price;
	@FXML
	private TableView<Brand> brandList;
	@FXML
	private TableColumn<Brand, Object> brandNameCol;
	@FXML
	private TableColumn<Brand, Object> brandActionCol;
	@FXML
	private TableView<Product> productList;
	@FXML
	private TableColumn<Product, Object> productNameCol;
	@FXML
	private TableColumn<Product, Object> productBrandCol;
	@FXML
	private TableColumn<Product, Object> priceCol;
	@FXML
	private TableColumn<Product, Object> activeCol;
	@FXML
	private TableColumn<Product, Object> productActionCol;
	@FXML
	private HBox titleHBox;
	@FXML
	private ImageView hand;
	private Tooltip newBrandTp= new Tooltip("Add new brand");
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
		fitWidth();
		fitHeight();
		fitWidthHeight();
	}
	private void init() {
		newBrand.setTooltip(newBrandTp);
		brandName.getItems().addAll(BrandModel.getModel().getAllName());
	}
	/*
	 * fit width height when changes width height
	 */
	public void fitWidthHeight() {
		WidthHeightProperty.widthProperty().addListener(c -> {
			fitWidth();
		});
		WidthHeightProperty.heightProperty().addListener(c -> {
			fitHeight();
		});
	}
	public void fitHeight() {
		root.setPrefHeight(WidthHeightProperty.getHeight()-20);
		titleHBox.setPrefHeight(WidthHeightProperty.getHeight()/30);
		if(WidthHeightProperty.getHeight()>1366) {
		}else {
		}
	}
	public void fitWidth() {
		root.setPrefWidth(WidthHeightProperty.getWidth()-20);
		addProduct.setPrefWidth(WidthHeightProperty.getWidth()/15);
		productNameCol.setPrefWidth(WidthHeightProperty.getWidth()/3);
		priceCol.setPrefWidth(WidthHeightProperty.getWidth()/12);
		clear.setPrefWidth(WidthHeightProperty.getWidth()/15);
		if(WidthHeightProperty.getWidth()>1366) {
			hand.setFitWidth(40);
		}else {
			hand.setFitWidth(20);
		}
	}
}
