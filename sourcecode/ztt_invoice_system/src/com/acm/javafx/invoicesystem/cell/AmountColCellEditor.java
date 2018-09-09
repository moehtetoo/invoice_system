package com.acm.javafx.invoicesystem.cell;

import com.acm.javafx.invoicesystem.dao.OthersInvoiceDaoImpl;
import com.acm.javafx.invoicesystem.model.OthersInvoiceItems;

import javafx.scene.control.TableCell;

public class AmountColCellEditor extends TableCell<OthersInvoiceItems, Object>{
	@Override
	protected void updateItem(Object item, boolean empty) {
		super.updateItem(item, empty);
		if(getItem()!=null&&!empty) {
			if(OthersInvoiceDaoImpl.getInvoiceItem().isFOC()) {
				setText("FOC");
			}
			else {
				setText(getString());
				}
		}else {
			setText(null);
		}
	}
	private String getString() {
        return getItem() == null ? "" : String.format("%,d", getItem());
    }
}
