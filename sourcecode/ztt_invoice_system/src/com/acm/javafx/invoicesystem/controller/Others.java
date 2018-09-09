package com.acm.javafx.invoicesystem.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.commons.collections.map.HashedMap;

import com.acm.javafx.invoicesystem.cell.ActionColCellEditor;
import com.acm.javafx.invoicesystem.cell.AmountColCellEditor;
import com.acm.javafx.invoicesystem.cell.DiscountColCellEditor;
import com.acm.javafx.invoicesystem.cell.PriceColCellEditor;
import com.acm.javafx.invoicesystem.dao.OthersInvoiceDaoImpl;
import com.acm.javafx.invoicesystem.dao.OthersInvoiceItemDaoImpl;
import com.acm.javafx.invoicesystem.dao.VoucherNoModel;
import com.acm.javafx.invoicesystem.model.OthersInvoice;
import com.acm.javafx.invoicesystem.model.OthersInvoiceItemFormatter;
import com.acm.javafx.invoicesystem.model.OthersInvoiceItems;
import com.acm.javafx.invoicesystem.property.WidthHeightProperty;
import com.acm.javafx.invoicesystem.report.PrintReport;

import javafx.application.Platform;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;

public class Others  implements Initializable{
	@FXML
	private  TextField customerName;
	@FXML
	private TextField address;
	@FXML
	private TextField voucherNo;
	@FXML
	private DatePicker date;
	@FXML
	private ComboBox<String> discountType;
	@FXML
	private TextField discountValue;
	@FXML
	private ComboBox<String> creditTerms;
	@FXML
	private Label totalAmount;
	@FXML
	private Label discount;
	@FXML
	private Label btp;
	@FXML
	private Button print;
	@FXML
	private Button addItems;
	@FXML
	private Button clean;
	@FXML
	private Button preview;
	@FXML
	private TableView<OthersInvoiceItems> oItems;
	@FXML
	private RadioButton credit;
	@FXML
	private RadioButton consign;
	@FXML
	private RadioButton cash;
	@FXML
	private HBox hboxCreditTerms;
	@FXML
	private TableColumn<OthersInvoiceItems, Object> actionCol;
	@FXML
	private Label discountError;
	@FXML
	private Label header;
	@FXML
	private TextArea remarks;
	@FXML
	private BorderPane bPane;
	@FXML
	private ImageView hand;
	@FXML
	private VBox vBox;
	@FXML
	private TextField phone;
	@FXML
	private TableColumn<OthersInvoiceItems, Integer> itemNoCol;
	@FXML
	private TableColumn<OthersInvoiceItems, String> itemNameCol;
	@FXML
	private TableColumn<OthersInvoiceItems, Integer> qtyCol;
	@FXML
	private TableColumn<OthersInvoiceItems, Object> priceCol;
	@FXML
	private TableColumn<OthersInvoiceItems, Object> amountCol;
	@FXML
	private TableColumn<OthersInvoiceItems, Object> discountCol;
	@FXML
	private HBox handHBox;
	@FXML
	private HBox totalHBox;
	@FXML
	private HBox discountHBox;
	@FXML
	private HBox btpHBox;
	@FXML
	private HBox paymentHBox;
	@FXML
	private Label phoneError;
	@FXML
	private Label othersPayment;
	@FXML
	private VBox totalVBox;
	@FXML
	private GridPane gPane;
	@FXML
	private TextField paymentInput;
	@FXML
	private Label paymentLabel;
	@FXML
	private Label customerNameError;
	@FXML
	private Label addressError;
	@FXML
	private Label voucherError;
	@FXML
	private Label paymentSelectError;
	@FXML
	private Label paymentInputError;
	@FXML
	private DatePicker dueDate;
	private OthersInvoiceDaoImpl invoiceDao= new OthersInvoiceDaoImpl();
	private static final String SHADOW_EFFECT="-fx-effect: dropshadow(three-pass-box, rgba(255,0,0,1), 8, 0, 0, 0);";
	private static final String	PATTERN="00000";
	private static final DecimalFormat DF= new DecimalFormat(PATTERN);
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.init();
		creditTerms.setDisable(true);
		hboxCreditTerms.setDisable(true);
		paymentHBox.setDisable(true);
		paymentInput.setDisable(true);
		credit.selectedProperty().addListener((a,b,c)-> toggleCreditTerms());
		cash.selectedProperty().addListener((a,b,c)-> {
			toggleCreditTerms();
		});
		creditTerms.getItems().addAll(OthersInvoiceDaoImpl.getCreditTerms());
		creditTerms.valueProperty().addListener(c ->toggleCreditTerms());
		discountType.getItems().addAll("Percentage","Amount");
		customizeTableCell();
		discountValue.textProperty().addListener((a,b,c)->setDiscountAndBTP());
		discountType.valueProperty().addListener(a->setDiscountAndBTP());
		print.setOnAction(a -> this.print());
		remarks.textProperty().addListener(a -> OthersInvoice.setRemarks(remarks.getText()));
		phone.textProperty().addListener(c -> {
			OthersInvoice.setPhone(phone.getText());
			validatePhone();
		});
		paymentInput.textProperty().addListener(c ->{
			if(!paymentInput.getText().isEmpty()) {
				OthersInvoice.setCreditTerms(paymentInput.getText());
				paymentInputError.setText("");
				paymentInput.setStyle(null);
			}
		});
	}
	private void validatePhone() {
		if(phone.isFocused()) {
			phoneError.setText(GeneralHandler.blankValidation(phone.getText()));
		}
	}
	public void setDiscountAndBTP() {
		this.setDiscountValue();
		this.setBalanceToPay();
		validateDiscount();
	}
	/*
	 * validate discount text field for discount type and discount value.
	 */
	public void validateDiscount() {
		 if(discountValue.isFocused()&&discountType.getValue().equals("Select One")) {
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
	/*
	 * show hide drop down
	 */
	public void toggleCreditTerms(){
		if (paymentInput.isDisable()) {
			
		}
		if(cash.isSelected()) {
			creditTerms.setDisable(true);
			hboxCreditTerms.setDisable(true);
			paymentInput.setDisable(true);
			paymentHBox.setDisable(true);
			OthersInvoice.setCredit(false);
			paymentLabel.setText("");
			othersPayment.setText("");
			creditTerms.setValue("Select One");
		}else {
			toggleCreditPayment();
			toggleLabel();
			creditTerms.setDisable(false);
			hboxCreditTerms.setDisable(false);
			OthersInvoice.setCredit(true);
		}
		
	}
	private void toggleLabel() {
		if(credit.isSelected()) {
			paymentLabel.setText("Credit Payment");
		}else {
			paymentLabel.setText("Consign Payment");
		}
	}
	public void toggleCreditPayment() {
		if(credit.isSelected()&&creditTerms.getValue().equals("Others")) {
			paymentHBox.setDisable(false);
			paymentInput.setDisable(false);
			dueDate.setValue(null);
			othersPayment.setText("Credit Others Payment");
		}else if(consign.isSelected()&&creditTerms.getValue().equals("Others")) {
			othersPayment.setText("Consign Others Payment");
			paymentHBox.setDisable(false);
			paymentInput.setDisable(false);
		}else {
			LocalDate now= LocalDate.now();
			if(creditTerms.getValue().equals("One Month")) {
				dueDate.setValue(now.plusMonths(1));
			}else if(creditTerms.getValue().equals("Two Week")){
				dueDate.setValue(now.plusDays(14));
			}
			paymentHBox.setDisable(true);
			paymentInput.setDisable(true);
		}
	}
	public void addItems() {
		AddInvoiceItem.show(oItems.getScene().getWindow());
	}
	private void init() {
		fitWidth();
		fitHeight();
		fitWidthHeight();
		header.setText(OthersInvoiceDaoImpl.getHeader());
		print.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/com/acm/javafx/invoicesystem/icon/print.png"))));
		getInvoice();
		totalAmount.setText(String.format("%,d", invoiceDao.calTotalAmount()));
		this.loadData();
		this.refreshData();
		setDiscountAndBTP();
		setInvoice();
		if(!credit.isSelected()) {
			creditTerms.setValue("Select One");
		}
		preview.setOnAction(this::preview);
		voucherNo.setText(DF.format(VoucherNoModel.getModel().increment()));
	}
	public void loadData() {
		oItems.getItems().clear();
		oItems.getItems().addAll(OthersInvoiceDaoImpl.getItemsList());
	}
	/*
	 * this is changed data for add or edit or remove invoice item 
	 */
	public void refreshData() {
		OthersInvoiceDaoImpl.getItemsList().addListener((Change<? extends OthersInvoiceItems> c) -> {
			if(c.next()) {
				if(c.wasAdded()) {
					totalAmount.setText(String.format("%,d", invoiceDao.calTotalAmount()));
					oItems.setStyle(null);
					this.loadData();
					setDiscountAndBTP();
				}
				if(c.wasRemoved()) {
					totalAmount.setText(String.format("%,d", invoiceDao.calTotalAmount()));
					OthersInvoiceItemDaoImpl.setNo(0);
					OthersInvoiceDaoImpl.getItemsList().forEach(invoiceItems->{
						OthersInvoiceItemDaoImpl.increment();
						invoiceItems.setItemsNo(OthersInvoiceItemDaoImpl.getNo());
					});
					if(OthersInvoiceDaoImpl.getItemsList().size()>0) {
						oItems.setStyle(null);
					}
					this.loadData();
					setDiscountAndBTP();
				}
			}	
			});
	}
	/*
	 * calculate discount value if user selected discount type by percentage
	 */
	public int discountByPercentage() {
		return invoiceDao.calDiscountByPercentage(GeneralHandler.parseInteger(totalAmount.getText().replaceAll(",", "")), 
				GeneralHandler.parseInteger(discountValue.getText()));
	}
	public void setDiscountValue() {
		if(discountType.getValue()!=null&&discountValue.getText()!=null) {
			if(discountType.getValue().equals("Percentage")) {
				discount.setText("("+discountValue.getText()+"%)\t".concat(String.format("%,d",discountByPercentage())));
			}else if(!discountType.getValue().equals("Select One")){
				discount.setText(discountValue.getText().isEmpty()? "0": String.format("%,d",GeneralHandler.parseInteger(discountValue.getText())));
			}else {
				discount.setText("0");
			}
		}else {
			discount.setText("0");
		}
	}
	public void setBalanceToPay() {
		if(getBalanceToPay()<=0) {
			btp.setText("0");
		}else {
			btp.setText(String.format("%,d",this.getBalanceToPay()));
		}
	}
	public int getBalanceToPay() {
		return invoiceDao.calBalanceToPay(GeneralHandler.parseInteger(totalAmount.getText().replaceAll(",", ""))
				, GeneralHandler.parseInteger(splitPercentage(1).replaceAll(",", "")));
	}
	public void customizeTableCell() {
		itemNoCol.setCellValueFactory(new PropertyValueFactory<>("itemsNo"));
		itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		discountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
		Callback<TableColumn<OthersInvoiceItems, Object>, 
        TableCell<OthersInvoiceItems, Object>> priceCellFactory =(TableColumn<OthersInvoiceItems, Object> p) -> new PriceColCellEditor();
        priceCol.setCellFactory(priceCellFactory);
        
        Callback<TableColumn<OthersInvoiceItems, Object>, 
        TableCell<OthersInvoiceItems, Object>> amountCellFactory =(TableColumn<OthersInvoiceItems, Object> p) -> new AmountColCellEditor();
        amountCol.setCellFactory(amountCellFactory);
        
        Callback<TableColumn<OthersInvoiceItems, Object>, 
        TableCell<OthersInvoiceItems, Object>> discountCellFactory =(TableColumn<OthersInvoiceItems, Object> p) -> new DiscountColCellEditor();
        discountCol.setCellFactory(discountCellFactory);
        
		Callback<TableColumn<OthersInvoiceItems, Object>, 
        TableCell<OthersInvoiceItems, Object>> actionCellFactory =(TableColumn<OthersInvoiceItems, Object> p) -> new ActionColCellEditor();
        actionCol.setCellFactory(actionCellFactory);
	}
	public void cleanAll() {
		Optional<ButtonType> result = GeneralHandler.showConfirmAlert("Confirm Dialog",
				AlertType.CONFIRMATION, null, "Are you sure want to clean all field and invoice item list?").showAndWait();
		if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
			OthersInvoice.setAddress(null);
			OthersInvoice.setCredit(false);
			OthersInvoice.setCreditTerms(null);
			OthersInvoice.setCustomerName(null);
			OthersInvoice.setDate(null);
			OthersInvoice.setDiscount(0);
			OthersInvoice.setDiscountType(null);
			OthersInvoice.setDiscountValue(null);
			OthersInvoice.setTotalAmount(0);
			OthersInvoice.setVoucherNo(null);
			this.getInvoice();
			OthersInvoiceDaoImpl.getItemsList().clear();
		}
	}
	public void setInvoice() {
		address.textProperty().addListener(a -> {
			if (!address.getText().isEmpty()) {
				OthersInvoice.setAddress(address.getText());
				addressError.setText("");
				address.setStyle(null);
			}
		});
		btp.textProperty().addListener(a -> {
			if (btp.getText()!= "0") {
				OthersInvoice.setBalanceToPay(GeneralHandler.parseInteger(btp.getText().replaceAll(",", "")));
			}
		});
		OthersInvoice.setCredit(credit.isSelected());
		creditTerms.valueProperty().addListener(a -> {
			if(creditTerms.getSelectionModel().getSelectedItem()!=null||!creditTerms.getValue().equals("Select One")) {
				OthersInvoice.setCreditTerms(creditTerms.getValue());
				paymentSelectError.setText("");
				creditTerms.setStyle(null);
			}
		});
		customerName.textProperty().addListener(a -> {
			if(!customerName.getText().isEmpty()) {
				OthersInvoice.setCustomerName(customerName.getText());
				customerNameError.setText("");
				customerName.setStyle(null);
			}
		});
		date.valueProperty().addListener(a -> {
			if(!date.getValue().equals(null)) {
				OthersInvoice.setDate(date.getValue());
			}else {
				OthersInvoice.setDate(LocalDate.now());
			}
		});
		discount.textProperty().addListener(a -> {
			if(discount.getText()!="0") {
				OthersInvoice.setDiscount(GeneralHandler.parseInteger(splitPercentage(1).replaceAll(",", "")));
			}
		});
		totalAmount.textProperty().addListener(a -> {
			if(totalAmount.getText()!="0") {
				
				OthersInvoice.setTotalAmount(GeneralHandler.parseInteger(totalAmount.getText().replaceAll(",", "")));
			}
		});
		voucherNo.textProperty().addListener(a -> {
			if(!voucherNo.getText().isEmpty()) {
				OthersInvoice.setVoucherNo(voucherNo.getText());
				voucherError.setText("");
				voucherNo.setStyle(null);
			}
		});
		discountType.valueProperty().addListener(a -> {
			if(discountType.getValue()!=null) {
				OthersInvoice.setDiscountType(discountType.getValue());
			}
		});
		discountValue.textProperty().addListener(a -> {
			if(!discountValue.getText().isEmpty()) {
				OthersInvoice.setDiscountValue(discountValue.getText());
			}
		});
		phone.textProperty().addListener(c -> {
			if(!phone.getText().isEmpty()) {
				OthersInvoice.setPhone(phone.getText());
				phoneError.setText("");
				phone.setStyle(null);
			}
		});
	}
	public String splitPercentage(int index) {
		String[] strs=discount.getText().split("\t");
		if(strs.length>1&&index>0) {
			return strs[index];
		}
		
		return strs[0];
	}
	public void getInvoice() {
		setAddressField();
		setCreditTermsField();
		setCustomerNameField();
		setDateField();
		setVoucherNoField();
		setDiscountTypeField();
		setDiscountValueField();
		setRemarksField();
		setPhoneField();
	}
	private void setPhoneField() {
		if(OthersInvoice.getPhone()!=null&&OthersInvoice.getPhone()!="") {
			phone.setText(OthersInvoice.getPhone());
		}else {
			phone.setText("");
		}
	}
	private void setDiscountValueField() {
		if(OthersInvoice.getDiscountValue()!=null&&OthersInvoice.getDiscountValue()!="") {
			discountValue.setText(OthersInvoice.getDiscountValue());
		}else {
			discountValue.setText("");
		}
	}
	private void setDiscountTypeField() {
		if (OthersInvoice.getDiscountType()!=null&&OthersInvoice.getDiscountType()!="Select One") {
			discountType.setValue(OthersInvoice.getDiscountType());
		}else {
			discountType.setValue("Select One");
		}
	}
	private void setVoucherNoField() {
		if(OthersInvoice.getVoucherNo()!=null&&OthersInvoice.getVoucherNo()!="") {
			voucherNo.setText(OthersInvoice.getVoucherNo());
		}else {
			voucherNo.setText("");
		}
	}
	private void setDateField() {
		if (OthersInvoice.getDate()!=null&&!OthersInvoice.getDate().equals("")) {
			date.setValue(OthersInvoice.getDate());
		}else {
			date.setValue(LocalDate.now());
			OthersInvoice.setDate(LocalDate.now());
		}
	}
	private void setCustomerNameField() {
		if(OthersInvoice.getCustomerName()!=null&&OthersInvoice.getCustomerName()!="") {
			customerName.setText(OthersInvoice.getCustomerName());
		}else {
			customerName.setText("");
		}
	}
	private void setCreditTermsField() {
		if (OthersInvoice.getCreditTerms()!=null&&OthersInvoice.getCreditTerms()!="Select One") {
			creditTerms.setValue(OthersInvoice.getCreditTerms());
		}else {
			creditTerms.setValue("Select One");
		}
	}
	private void setAddressField() {
		if(OthersInvoice.getAddress()!=null&&OthersInvoice.getAddress()!="") {
			address.setText(OthersInvoice.getAddress());
		}else {
			address.setText("");
		}
	}
	private void setRemarksField() {
		if(OthersInvoice.getRemarks()!=null&&OthersInvoice.getRemarks()!="") {
			remarks.setText(OthersInvoice.getRemarks());
		}else {
			remarks.setText("");
		}
	}
	public void print() {
		if(isReadyForPrint()) {
			String image="images/";
			String jasperFile="ztt_invoice.jasper";
			DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
			List<OthersInvoiceItemFormatter> formatterList= new ArrayList<>();
			InvoiceItemFormatter(formatterList);
			Map<String, Object> parameters = bindJasperParameter(image, formatter);
			Runnable r = new Runnable() {
				@Override
				public void run() {
					startRun(jasperFile, parameters,formatterList);
				}
			};
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			thread.start();
		}
		
	}
	public void preview(ActionEvent ev)  {
		if(isReadyForPrint()) {
			String image="images/";
			String jasperFile="ztt_invoice.jasper";
			DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
			List<OthersInvoiceItemFormatter> formatterList= new ArrayList<>();
			InvoiceItemFormatter(formatterList);
			Map<String, Object> parameters = bindJasperParameter(image, formatter);
			try {
				PrintReport.previewAndPrint(jasperFile, parameters, formatterList, preview.getScene().getWindow());
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public Map<String, Object> bindJasperParameter(String image, DateTimeFormatter formatter) {
		Map<String, Object> parameters= new HashedMap();
		parameters.put("voucherNo", OthersInvoice.getVoucherNo());
		parameters.put("customerName", OthersInvoice.getCustomerName());
		parameters.put("address", OthersInvoice.getAddress().concat(" , "+OthersInvoice.getPhone()));
		parameters.put("discount",discount.getText());
		parameters.put("date", OthersInvoice.getDate().format(formatter));
		parameters.put("creditTerms",
				creditTerms.getValue().equals("Select One")? "" : OthersInvoice.getCreditTerms());
		parameters.put("totalAmount",String.format("%,d", OthersInvoice.getTotalAmount()));
		parameters.put("balanceToPay",String.format("%,d", OthersInvoice.getBalanceToPay()));
		parameters.put("isCredit", OthersInvoice.isCredit());
		parameters.put("remarks", OthersInvoice.getRemarks());
		parameters.put("image", image.concat(OthersInvoice.getImage()));
		return parameters;
	}
	/*
	 * Change format thousand separater for jasper
	 */
	private void InvoiceItemFormatter(List<OthersInvoiceItemFormatter> formatterList) {
		OthersInvoiceDaoImpl.getItemsList().forEach(item -> {
			OthersInvoiceItemFormatter formatInvoice= new OthersInvoiceItemFormatter();
			formatInvoice.setNo(String.valueOf(item.getItemsNo()));
			formatInvoice.setItemName(item.getItemName());
			formatInvoice.setQuantity(String.valueOf(item.getQuantity()));
			formatInvoice.setPrice(item.isFOC()? "FOC":String.format("%,d", item.getPrice()));
			formatInvoice.setAmount(item.isFOC()? "FOC":String.format("%,d", item.getAmount()));
			formatterList.add(formatInvoice);
		});
	}
	private boolean isReadyForPrint() {
		if(customerName.getText().isEmpty()||customerName.getText().equals(" ")) {
			customerName.setStyle(SHADOW_EFFECT);
			customerNameError.setText("Customer name cannot be empty!");
			return false;
		}
		if(address.getText().isEmpty()||address.getText().equals(" ")) {
			address.setStyle(SHADOW_EFFECT);
			addressError.setText("Address cannot be empty!");
			return false;
		}
		if(phone.getText().isEmpty()||phone.getText().equals(" ")) {
			phone.setStyle(SHADOW_EFFECT);
			phoneError.setText("Phone number cannot be empty!");
			return false;
		}
		if(voucherNo.getText().isEmpty()||voucherNo.getText().equals(" ")) {
			voucherNo.setStyle(SHADOW_EFFECT);
			voucherError.setText("Voucher number cannot be empty!");
			return false;
		}
		if(!creditTerms.isDisable()&&creditTerms.getValue().equals("Select One")) {
			creditTerms.setStyle(SHADOW_EFFECT);
			paymentSelectError.setText("Select one credit terms!");
			return false;
		}
		if(OthersInvoiceDaoImpl.getItemsList().size()==0) {
			oItems.setStyle(SHADOW_EFFECT);
			GeneralHandler.showAlert("Information", AlertType.INFORMATION, null, "Your invoice items cannot be empty!");
			return false;
		}
		if(!paymentInput.isDisable()&&paymentInput.getText().isEmpty()) {
			paymentInput.setStyle(SHADOW_EFFECT);
			paymentInputError.setText("This Field cannot be empty");
			return false;
		}
		setStyleNone();
		return true;

	}
	private void setStyleNone() {
		customerName.setStyle(null);
		address.setStyle(null);
		phone.setStyle(null);
		voucherNo.setStyle(null);
		creditTerms.setStyle(null);
		oItems.setStyle(null);
		paymentInput.setStyle(null);
	}
	public void startRun(String jasperFile, Map<String, Object> parameters,List<OthersInvoiceItemFormatter> itemList) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					print.setDisable(true);
					PrintReport.print(jasperFile, parameters, itemList,print.getScene().getWindow());
					VoucherNoModel.getModel().saveOneData(voucherNo.getText());
				} catch (Exception e) {
					GeneralHandler.showAlert("Error", AlertType.ERROR, null, e.toString());
				}finally {
					print.setDisable(false);
					voucherNo.setText(DF.format(GeneralHandler.parseInteger(voucherNo.getText())+1));
				}
			}
		});
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
		bPane.setPrefHeight(WidthHeightProperty.getHeight()-20);
		if(WidthHeightProperty.getHeight()>1366) {
			hand.setFitHeight(40);
		}else {
			hand.setFitHeight(20);
		}
		vBox.setPrefHeight(WidthHeightProperty.getHeight()/2.3);
		handHBox.setPrefHeight(WidthHeightProperty.getHeight()/25);
	}
	public void fitWidth() {
		bPane.setPrefWidth(WidthHeightProperty.getWidth()-20);
		date.setPrefWidth(WidthHeightProperty.getWidth()/4);
		discountType.setPrefWidth(WidthHeightProperty.getWidth()/4);
		creditTerms.setPrefWidth(WidthHeightProperty.getWidth()/4);
		if(WidthHeightProperty.getWidth()>1366) {
			hand.setFitWidth(40);
			gPane.setVgap(10);
		}else {
			hand.setFitWidth(20);
			gPane.setVgap(0);
		}
		clean.setPrefWidth(WidthHeightProperty.getWidth()/10);
		addItems.setPrefWidth(WidthHeightProperty.getWidth()/10);
		print.setPrefWidth(WidthHeightProperty.getWidth()/10);
		preview.setPrefWidth(WidthHeightProperty.getWidth()/10);
		dueDate.setPrefWidth(WidthHeightProperty.getWidth()/4);
		oItems.setPrefWidth(WidthHeightProperty.getWidth()/1.3);
		itemNoCol.setPrefWidth(WidthHeightProperty.getWidth()/30);
		itemNameCol.setPrefWidth(WidthHeightProperty.getWidth()/4.5);
		qtyCol.setMinWidth(WidthHeightProperty.getWidth()/25);
		qtyCol.setPrefWidth(WidthHeightProperty.getWidth()/25);
		priceCol.setPrefWidth(WidthHeightProperty.getWidth()/9);
		discountCol.setPrefWidth(WidthHeightProperty.getWidth()/9);
		amountCol.setPrefWidth(WidthHeightProperty.getWidth()/9);
		actionCol.setPrefWidth(WidthHeightProperty.getWidth()/7);
		totalVBox.setPrefWidth(WidthHeightProperty.getWidth()/5);
		totalHBox.setPrefWidth(WidthHeightProperty.getWidth()-10);
		discountHBox.setPrefWidth(WidthHeightProperty.getWidth()-10);
		btpHBox.setPrefWidth(WidthHeightProperty.getWidth()-10);
	}
}
