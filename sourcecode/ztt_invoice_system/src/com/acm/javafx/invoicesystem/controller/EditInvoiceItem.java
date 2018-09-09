package com.acm.javafx.invoicesystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.acm.javafx.invoicesystem.dao.InvoiceItemsDao;
import com.acm.javafx.invoicesystem.dao.OthersInvoiceDaoImpl;
import com.acm.javafx.invoicesystem.dao.OthersInvoiceItemDaoImpl;
import com.acm.javafx.invoicesystem.model.OthersInvoiceItems;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

public class EditInvoiceItem implements Initializable{
	@FXML
	private TextField itemName;
	@FXML
	private TextField qty;
	@FXML
	private TextField price;
	@FXML
	private Label nameError;
	@FXML
	private Label qtyError;
	@FXML
	private Label priceError;
	@FXML
	private JFXButton updatebtn;
	@FXML
	private JFXButton closebtn;
	@FXML
	private BorderPane root;
	@FXML
	private GridPane btnGrid;
	@FXML
	private TextField discountValue;
	@FXML
	private ComboBox<String> discountType;
	@FXML
	private JFXRadioButton nonFOC;
	@FXML
	private JFXRadioButton FOC;
	@FXML
	private Label discountError;
	private static final String SHADOW_EFFECT="-fx-effect: dropshadow(three-pass-box, rgba(255,0,0,1), 8, 0, 0, 0);"; 
	private OthersInvoiceDaoImpl invoiceDao= new OthersInvoiceDaoImpl();
	private InvoiceItemsDao itemsDao =new OthersInvoiceItemDaoImpl();
	public static void show(Window owner) {
		Rectangle2D bound= Screen.getPrimary().getVisualBounds();
		try {
			Parent root= FXMLLoader.load(EditInvoiceItem.class.getResource("/com/acm/javafx/invoicesystem/view/EditInvoiceItem.fxml"));
			Scene scene=new Scene(root,bound.getWidth()/3,bound.getHeight()/1.5);
			Stage stage= new Stage();
			if(bound.getWidth()>1366) {
				scene.getStylesheets().add(AddInvoiceItem.class.getResource("/com/acm/javafx/invoicesystem/view/fullResolution.css").toExternalForm());
			}else {
				scene.getStylesheets().add(AddInvoiceItem.class.getResource("/com/acm/javafx/invoicesystem/view/normalResolution.css").toExternalForm());
			}
			stage.getIcons().add(new Image(AddInvoiceItem.class.getResourceAsStream("/com/acm/javafx/invoicesystem/icon/icon.jpg")));
			stage.setScene(scene);
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
		itemName.getScene().getWindow().hide();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.init();
	}
	private void init() {
		fitWidth();
		fitHeight();
		fitWidthHeight();
		itemName.setText(getInvoiceItem().getItemName().replaceAll("<FOC>", ""));
		qty.setText(String.valueOf(getInvoiceItem().getQuantity()));
		price.setText(String.valueOf(getInvoiceItem().getPrice()));
		nonFOC.setSelected(!getInvoiceItem().isFOC());
		FOC.setSelected(getInvoiceItem().isFOC());
//			discountValue.setText(String.valueOf(invoiceDao.getPercentage(itemsDao.calculateTotal(getInvoiceItem()), getInvoiceItem().getDiscount())));
		discountValue.setText(String.valueOf(getInvoiceItem().getDiscount()));		
		discountType.setValue(getInvoiceItem().getDiscountType());
		itemName.textProperty().addListener((a,b,c)->nameError.setText(GeneralHandler.blankValidation(itemName.getText())));
		qty.textProperty().addListener((a,b,c)->qtyError.setText(GeneralHandler.validationField(qty.getText())));
		price.textProperty().addListener((a,b,c)->{
			if (nonFOC.isSelected()) {
				priceError.setText(GeneralHandler.validationField(price.getText()));
			}else {
				priceError.setText("");
			}
		});
		updatebtn.setOnAction(a -> this.updateItems());
		closebtn.setOnAction(a -> this.close());
		discountType.getItems().addAll("Percentage","Amount");
		discountValue.textProperty().addListener(c -> validateDiscount());
		discountType.valueProperty().addListener(c -> validateDiscount());
		nonFOC.selectedProperty().addListener(c ->toggleField());
		toggleField();
	}
	private void toggleField() {
		if(nonFOC.isSelected()) {
			price.setDisable(false);
			discountValue.setDisable(false);
			discountType.setDisable(false);
		}else {
			price.setText("0");
			discountValue.setText("0");
			discountType.getSelectionModel().select(null);
			price.setDisable(true);
			discountValue.setDisable(true);
			discountType.setDisable(true);
		}
	}
	/*
	 * validate field  if user add or enter without fill require field
	 */
	public boolean validateField(String text,String fieldName) {
		if(!text.matches("\\d{0,7}([\\.]\\d{0,4})?")&&nonFOC.isSelected()) {
			GeneralHandler.showAlert("Error", AlertType.WARNING, null, fieldName+" must be number!");
			return false;
		}else if(text.isEmpty()&&nonFOC.isSelected()) {
			GeneralHandler.showAlert("Error", AlertType.WARNING, null, fieldName+" cannot be empty!");
			return false;
		}else if(GeneralHandler.parseInteger(text)<=0&&nonFOC.isSelected()) {
			GeneralHandler.showAlert("Error", AlertType.WARNING, null, fieldName+" must be greater than 0!");
			return false;
		}
		return true;
	}
	/*
	 * only validate empty string if user add or enter without fill require field
	 */
	public boolean validateEmpty(String text,String fieldName) {
		if(text.isEmpty()) {
			GeneralHandler.showAlert("Error", AlertType.WARNING, null, fieldName+" cannot be empty!");
			return  false;
		}
		return true;
	}
	public OthersInvoiceItems getInvoiceItem() {
		return OthersInvoiceDaoImpl.getInvoiceItem();
	}
	public void updateItems() {
		if(validateEmpty(itemName.getText(),"Item Name")&&validateField(qty.getText(),"Quantity")&&validateField(price.getText(),"Price")) {
			if(nonFOC.isSelected()) {
				getInvoiceItem().setItemName(itemName.getText());
				getInvoiceItem().setDiscountType(discountType.getValue());
			}else {
				getInvoiceItem().setItemName(itemName.getText().concat("<FOC>"));
				getInvoiceItem().setDiscountType("");
			}
			getInvoiceItem().setPrice(GeneralHandler.parseInteger(price.getText()));
			getInvoiceItem().setQuantity(GeneralHandler.parseInteger(qty.getText()));
			if (discountType.getSelectionModel().getSelectedItem()=="Percentage") {
				getInvoiceItem().setDiscount(GeneralHandler.parseInteger(discountValue.getText()));
				getInvoiceItem().setAmount(this.getTotalAmount(getInvoiceItem(), this.calculateDiscountByPercentage(itemsDao.calculateTotal(getInvoiceItem()), 
						GeneralHandler.parseInteger(discountValue.getText()))));
			}else {
				getInvoiceItem().setDiscount(GeneralHandler.parseInteger(discountValue.getText()));
				getInvoiceItem().setAmount(this.getTotalAmount(getInvoiceItem(), GeneralHandler.parseInteger(discountValue.getText())));
			}
			getInvoiceItem().setFOC(!nonFOC.isSelected());
			
			OthersInvoiceDaoImpl.updateInvoiceItem(getInvoiceItem());
			this.close();
		}
	}
	@FXML
	public void onEnter(ActionEvent ev) {
		this.updateItems();
	}
	public void fitWidthHeight() {
		root.widthProperty().addListener(c -> {
			fitWidth();
		});
		root.heightProperty().addListener(c -> {
			fitHeight();
		});
		itemName.widthProperty().addListener(c -> discountType.setPrefWidth(itemName.getWidth()));
	}
	public void fitWidth() {
		updatebtn.setPrefWidth(root.getWidth()/4);
		closebtn.setPrefWidth(root.getWidth()/4);
		discountType.setPrefWidth(root.getWidth()/1.5);
	}
	public void fitHeight() {
		
	}
	/*
	 * validate discount text field for discount type and discount value.
	 */
	public void validateDiscount() {
		 if(discountValue.isFocused()&&discountType.getSelectionModel().getSelectedItem()==null) {
			 discountType.setStyle(SHADOW_EFFECT);
			 discountError.setText("Please select one discount type.");
		}else if(discountValue.isFocused()&&discountValue.getText().isEmpty()){
			discountType.setStyle(null);
			discountError.setText(null);
		}else if(discountValue.isFocused()&&!discountValue.getText().matches("\\d{0,7}([\\.]\\d{0,4})?")) {
			discountType.setStyle(null);
			discountError.setText("This field must be number");
		}else if(discountValue.isFocused()&&GeneralHandler.parseInteger(discountValue.getText())<=0) {
			discountType.setStyle(null);
			discountError.setText ("This field must be greater than 0");
		}else if(discountType.getValue()!=null&&
				discountType.getValue().equals("Percentage")&&GeneralHandler.parseInteger(discountValue.getText())>100) {
			discountType.setStyle(null);
			GeneralHandler.showAlert("Error", AlertType.ERROR, null, "if Discount type is percentage ,Discount value must be less than 100%.");
		}else {
			discountType.setStyle(null);
			discountError.setText(null);
		}
	}
	private int calculateDiscountByPercentage(int totalAmount,int discount) {
		return   invoiceDao.calDiscountByPercentage(totalAmount, discount);
	}
	private int getTotalAmount(OthersInvoiceItems oInvoice,int discount) {
		return invoiceDao.calBalanceToPay(itemsDao.calculateTotal(oInvoice), discount);
	}
}
