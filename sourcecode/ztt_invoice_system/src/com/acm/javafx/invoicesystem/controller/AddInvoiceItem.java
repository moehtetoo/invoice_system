package com.acm.javafx.invoicesystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.controlsfx.control.textfield.TextFields;

import com.acm.javafx.invoicesystem.dao.OthersInvoiceDaoImpl;
import com.acm.javafx.invoicesystem.dao.OthersInvoiceItemDaoImpl;
import com.acm.javafx.invoicesystem.dao.ProductModel;
import com.acm.javafx.invoicesystem.model.OthersInvoiceItems;
import com.acm.javafx.invoicesystem.model.Product;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AddInvoiceItem implements Initializable{
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
	private Label discountError;
	@FXML
	private JFXButton addbtn;
	@FXML	
	private BorderPane root;
	@FXML
	private Text text;
	@FXML
	private JFXButton cancelbtn;
	@FXML
	private GridPane btnGrid;
	@FXML
	private TextField discountValue;
	@FXML
	private ComboBox<String> discountType;
	@FXML
	private JFXRadioButton nonFOC;
	private static final String SHADOW_EFFECT="-fx-effect: dropshadow(three-pass-box, rgba(255,0,0,1), 8, 0, 0, 0);"; 
	private OthersInvoiceDaoImpl invoiceDao= new OthersInvoiceDaoImpl();
	private OthersInvoiceItemDaoImpl itemDao= new OthersInvoiceItemDaoImpl();
	private Predicate<Product> findByBrand = a -> {
		return a.getBrand().getName().equals(OthersInvoiceDaoImpl.getHeader());
	};
	private Predicate<Product> findByName= i-> {
		return i.getName().equals(itemName.getText());
	};
	public static void show(Window owner) {
		Rectangle2D bound= Screen.getPrimary().getVisualBounds();
		try {
			BorderPane root= (BorderPane)FXMLLoader.load(AddInvoiceItem.class.getResource("/com/acm/javafx/invoicesystem/view/AddInvoiceItem.fxml"));
			Scene scene=new Scene(root,bound.getWidth()/3,bound.getHeight()/1.5);
			Stage stage= new Stage();
			if(bound.getWidth()>1366) {
				scene.getStylesheets().add(AddInvoiceItem.class.getResource("/com/acm/javafx/invoicesystem/view/fullResolution.css").toExternalForm());
			}else {
				scene.getStylesheets().add(AddInvoiceItem.class.getResource("/com/acm/javafx/invoicesystem/view/normalResolution.css").toExternalForm());
			}
			stage.setScene(scene);
			stage.getIcons().add(new Image(AddInvoiceItem.class.getResourceAsStream("/com/acm/javafx/invoicesystem/icon/icon.jpg")));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(owner);
			stage.setTitle("New Item");
			stage.showAndWait();
		} catch (IOException e) {
			GeneralHandler.showAlert("Error", AlertType.ERROR, null,e.toString());
			e.printStackTrace();
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
		itemName.textProperty().addListener((a,b,c)->nameError.setText(GeneralHandler.blankValidation(itemName.getText())));
		qty.textProperty().addListener((a,b,c)->qtyError.setText(GeneralHandler.validationField(qty.getText())));
		price.textProperty().addListener((a,b,c)->{
			if(nonFOC.isSelected()) {
				priceError.setText(GeneralHandler.validationField(price.getText()));
			}else {
				priceError.setText("");
			}
		});
		addbtn.setOnAction(a -> this.addItems());
		bindAndAutoComplete();
		discountType.getItems().addAll("Percentage","Amount");
		discountValue.textProperty().addListener(c -> validateDiscount());
		discountType.valueProperty().addListener(c -> validateDiscount());
		nonFOC.selectedProperty().addListener(c -> toggleField());
		
	}
	public void bindAndAutoComplete() {
		OthersInvoiceDaoImpl.getItemName().clear();
		ProductModel.getModel().getWhere(findByBrand).forEach(product ->{
			OthersInvoiceDaoImpl.getItemName().add(product.getName());
		}
		);
		TextFields.bindAutoCompletion(itemName, OthersInvoiceDaoImpl.getItemName()).setOnAutoCompleted(event -> {
			Product p= ProductModel.getModel().findById(findByName);
			price.setText(String.valueOf(p.getPrice()));
		});
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
	public void addItems() {
		if (OthersInvoiceDaoImpl.getItemsList().size()<14) {
			if(validateEmpty(itemName.getText(),"Item Name")&&validateField(qty.getText(),"Quantity")&&validateField(price.getText(),"Price")&&validateDiscount()) {
				OthersInvoiceItems oInvoice= new OthersInvoiceItems();
				if(nonFOC.isSelected()) {
					oInvoice.setItemName(itemName.getText());
					oInvoice.setDiscountType(discountType.getValue());
					oInvoice.setFOC(false);
					oInvoice.setPrice(GeneralHandler.parseInteger(price.getText()));
				}else {
					oInvoice.setPrice(0);
					oInvoice.setFOC(true);
					oInvoice.setItemName(itemName.getText());
					oInvoice.setDiscountType("");
				}
				OthersInvoiceItemDaoImpl.increment();
				oInvoice.setItemsNo(OthersInvoiceItemDaoImpl.getNo());
				oInvoice.setQuantity(GeneralHandler.parseInteger(qty.getText()));
				if (discountType.getValue()=="Percentage") {
					oInvoice.setAmount(this.getTotalAmount(oInvoice,
							this.calculateDiscountByPercentage(itemDao.calculateTotal(oInvoice),
									GeneralHandler.parseInteger(discountValue.getText()))));
					oInvoice.setDiscount(GeneralHandler.parseInteger(discountValue.getText()));

				}else {
					oInvoice.setDiscount(GeneralHandler.parseInteger(discountValue.getText()));
					oInvoice.setAmount(this.getTotalAmount(oInvoice, GeneralHandler.parseInteger(discountValue.getText())));
				}
				OthersInvoiceDaoImpl.addInvoiceItems(oInvoice);
				this.close();
			}
		}else {
			GeneralHandler.showAlert("Warning", AlertType.WARNING, null, "Items list can add max 14. Try in next voucher!");
		}
		
	}
	private int calculateDiscountByPercentage(int totalAmount,int discount) {
		return   invoiceDao.calDiscountByPercentage(totalAmount, discount);
	}
	private int getTotalAmount(OthersInvoiceItems oInvoice,int discount) {
		return invoiceDao.calBalanceToPay(itemDao.calculateTotal(oInvoice), discount);
	}
	/*
	 * this event occur when user is press ENTER key
	 */
	@FXML
	public void onEnter(ActionEvent ev) {
		this.addItems();
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
		addbtn.setPrefWidth(root.getWidth()/4);
		cancelbtn.setPrefWidth(root.getWidth()/4);
		discountType.setPrefWidth(root.getWidth()/1.5);
	}
	public void fitHeight() {
		
	}
	/*
	 * validate discount text field for discount type and discount value.
	 */
	public boolean validateDiscount() {
		 if(discountValue.isFocused()&&discountType.getSelectionModel().getSelectedItem()==null) {
			 discountType.setStyle(SHADOW_EFFECT);
			 discountValue.setStyle(null);
			 discountError.setText("Please select one discount type.");
			 return false;
		}else if(discountValue.isFocused()&&discountValue.getText().isEmpty()){
			discountType.setStyle(null);
			discountValue.setStyle(null);
			discountError.setText(null);
			 return true;
		}else if(discountValue.isFocused()&&!discountValue.getText().matches("\\d{0,7}([\\.]\\d{0,4})?")) {
			discountType.setStyle(null);
			discountValue.setStyle(SHADOW_EFFECT);
			discountError.setText("This field must be number");
			 return false;
		}else if(discountValue.isFocused()&&GeneralHandler.parseInteger(discountValue.getText())<=0) {
			discountType.setStyle(null);
			discountValue.setStyle(SHADOW_EFFECT);
			discountError.setText ("This field must be greater than 0");
			 return false;
		}else if(discountType.getValue()!=null&&
				discountType.getValue().equals("Percentage")&&GeneralHandler.parseInteger(discountValue.getText())>100) {
			discountType.setStyle(null);
			discountValue.setStyle(SHADOW_EFFECT);
			GeneralHandler.showAlert("Error", AlertType.ERROR, null, "if Discount type is percentage ,Discount value must be less than 100%.");
			 return false;
		}else {
			discountType.setStyle(null);
			discountError.setText(null);
			discountValue.setStyle(null);
			 return true;
		}
	}
}
