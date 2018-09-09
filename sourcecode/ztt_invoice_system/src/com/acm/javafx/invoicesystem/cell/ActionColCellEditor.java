package com.acm.javafx.invoicesystem.cell;

import java.util.Optional;

import com.acm.javafx.invoicesystem.controller.EditInvoiceItem;
import com.acm.javafx.invoicesystem.controller.GeneralHandler;
import com.acm.javafx.invoicesystem.dao.OthersInvoiceDaoImpl;
import com.acm.javafx.invoicesystem.model.OthersInvoiceItems;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

public class ActionColCellEditor extends TableCell<OthersInvoiceItems, Object>{
	private Button remove;
	private Button edit;
	private HBox hbox;
	private String removeFile;
	private String editFile;
	private Rectangle2D bounds= Screen.getPrimary().getVisualBounds();
	private Tooltip editTp= new Tooltip("Edit");
	private Tooltip removeTp= new Tooltip("Remove");
	
	@Override
	protected void updateItem(Object item, boolean empty) {
		super.updateItem(item, empty);
		if(bounds.getWidth()>1366) {
			removeFile="/com/acm/javafx/invoicesystem/icon/remove-23.png";
			editFile="/com/acm/javafx/invoicesystem/icon/edit-23.png";
		}else {
			removeFile="/com/acm/javafx/invoicesystem/icon/remove-16.png";
			editFile="/com/acm/javafx/invoicesystem/icon/edit-16.png";
		}
		Image removeIcon= new Image(getClass().getResourceAsStream(removeFile));
		Image editIcon = new Image(getClass().getResourceAsStream(editFile));
		remove = new JFXButton("", new ImageView(removeIcon));
		edit = new JFXButton("",new ImageView(editIcon));
		hbox= new HBox(4);
		fitWidth();
		fitHeight();
		fitWidthHeight();
		custButtom();
		if (empty) {
            setGraphic(null);
            setText(null);
        } else {
            remove.setOnAction(e -> this.removeAction());
            edit.setOnAction(a->{
            	OthersInvoiceDaoImpl.setIndex(getIndex());
            	EditInvoiceItem.show(edit.getScene().getWindow());
            });
            hbox.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
            hbox.getChildren().addAll(edit,remove);
            setGraphic(hbox);
            setText(null);
        }
	}
	public void custButtom() {
		edit.setPrefWidth(50);
		remove.setPrefWidth(50);
		edit.setCursor(Cursor.HAND);
		remove.setCursor(Cursor.HAND);
		editTp.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");
		edit.setTooltip(editTp);
		removeTp.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");
		remove.setTooltip(removeTp);
		((JFXButton) edit).setButtonType(ButtonType.RAISED);
		((JFXButton) remove).setButtonType(ButtonType.RAISED);
		((JFXButton) edit).setStyle("-fx-background-color:  #00ACC1");
		((JFXButton) remove).setStyle("-fx-background-color:   #00ACC1");
		hbox.setAlignment(Pos.CENTER); 
	}
	private void removeAction() {
		Optional<javafx.scene.control.ButtonType> result =
				GeneralHandler.showConfirmAlert("Confirm Dialog", AlertType.CONFIRMATION, null,
						"Are you sure want to delete?").showAndWait();
		if ((result.isPresent()) && (result.get() == javafx.scene.control.ButtonType.OK)) {
			OthersInvoiceDaoImpl.getItemsList().remove(getIndex());
		}
	}
	public void fitWidthHeight() {
		hbox.widthProperty().addListener(c -> {
			fitWidth();
		});
		hbox.heightProperty().addListener(c -> {
			fitHeight();
		});
	}
	public void fitWidth() {
		edit.setPrefWidth(hbox.getWidth()/5);
		remove.setPrefWidth(hbox.getWidth()/5);
	}
	public void fitHeight() {
		edit.setPrefHeight(hbox.getHeight()/4);
		remove.setPrefHeight(hbox.getHeight()/4);
	}
}
