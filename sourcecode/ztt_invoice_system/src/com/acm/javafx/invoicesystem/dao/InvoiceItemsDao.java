package com.acm.javafx.invoicesystem.dao;

import com.acm.javafx.invoicesystem.model.OthersInvoiceItems;

public interface InvoiceItemsDao {
	int calculateTotal( OthersInvoiceItems oItems);
}
