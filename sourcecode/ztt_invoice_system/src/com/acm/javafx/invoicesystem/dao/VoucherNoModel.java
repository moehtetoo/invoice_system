package com.acm.javafx.invoicesystem.dao;

import java.io.IOException;
import com.acm.javafx.invoicesystem.controller.GeneralHandler;
import com.acm.javafx.invoicesystem.model.VoucherNo;

import javafx.scene.control.Alert.AlertType;

public class VoucherNoModel extends BaseModel<VoucherNo>{
	private static VoucherNoModel MODEL;
	VoucherNoModel() {
		super(n-> String.format("%d", n.getNo()), 
				n-> {
					try {
						VoucherNo voucher= new VoucherNo();
						voucher.setNo(Integer.parseInt(n));
						return voucher;
					} catch (Exception e) {
						GeneralHandler.showAlert("Error", AlertType.ERROR, null, "Voucher No input output error!");
						e.printStackTrace();
					}
					return null;
				}, "databases/voucher-no.txt");
	}
	@Override
	public void add(VoucherNo t) throws IOException {
		super.add(t);
	}
	public int get() {
		return super.getAll().get(0).getNo();
	}
	public int increment() {
		return this.get()+1;
	}
	@Override
	public void saveOneData(String s) throws IOException {
		super.saveOneData(s);
	}
	public static VoucherNoModel getModel() {
		if(null == MODEL)
			MODEL = new VoucherNoModel();
		return MODEL;
	}
}
