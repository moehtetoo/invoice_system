package com.acm.javafx.invoicesystem.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.acm.javafx.invoicesystem.controller.GeneralHandler;
import com.acm.javafx.invoicesystem.model.Brand;

import javafx.scene.control.Alert.AlertType;

public class BrandModel extends BaseModel<Brand>{
	private static final String DEL="\t";
	private static final String FORMAT="%d"+DEL+"%d";
	private static BrandModel MODEL;
	
	BrandModel() {
		super(b -> String.format(FORMAT, b.getId(), b.getName()),
				b -> {
					String[] strs =b.split(DEL);
					try {
						Brand brand= new Brand();
						brand.setId(Integer.parseInt(strs[0]));
						brand.setName(strs[1]);
						return brand;
					} catch (Exception e) {
						GeneralHandler.showAlert("Error", AlertType.ERROR, null, "Brand input output error!");
						e.printStackTrace();
					}
					return null;
				}, "databases/brand.txt");
	}
	public static BrandModel getModel() {
		if(null == MODEL)
			MODEL= new BrandModel();
		return MODEL;
	}
	@Override
	public void add(Brand t) throws IOException {
		t.setId(generate());
		super.add(t);
	}
	public List<String> getAllName() {
		List<String> name= new ArrayList<>();
		super.getAll().forEach(a -> {
			name.add(a.getName());
		});
		return name;
	}
	@Override
	public Brand findById(Predicate<Brand> pred) {
		return super.findById(pred);
	}
	@Override
	public List<Brand> getWhere(Predicate<Brand> where) {
		return super.getWhere(where);
	}
	@Override
	public List<Brand> getAll() {
		return super.getAll();
	}
	@Override
	public void remove(int t) throws IOException {
		super.remove(t);
	}
	@Override
	public void update(int index, Brand t) throws IOException {
		super.update(index, t);
	}
}
