package com.acm.javafx.invoicesystem.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.acm.javafx.invoicesystem.controller.GeneralHandler;
import com.acm.javafx.invoicesystem.model.Product;

import javafx.scene.control.Alert.AlertType;

public class ProductModel extends BaseModel<Product> {
	private static final String DEL="\t";
	private static final String FORMAT="%d"+DEL+"%d"+DEL+"%d"+DEL+"%d";
	private static ProductModel MODEL;
	ProductModel() {
		super(p -> String.format(FORMAT, p.getId(),p.getName(),p.getPrice(),p.getBrand()),
				p -> {
					String strs[]=p.split(DEL);
					try {
						Product product= new Product();
						product.setId(Integer.parseInt(strs[0]));
						product.setName(strs[1]);
						product.setPrice(Integer.parseInt(strs[2]));
						product.setBrand(BrandModel.getModel().findById(a -> a.getId()== Integer.parseInt(strs[3])));
						return product;
					} catch (Exception e) {
						GeneralHandler.showAlert("Error", AlertType.ERROR, null, "Product input output error!");
						e.printStackTrace();
					}
			return null;
		}, "databases/product.txt");
	}
	public static ProductModel getModel() {
		if(null == MODEL)
			MODEL = new ProductModel();
		return MODEL;
	}
	@Override
	public void add(Product t) throws IOException {
		t.setId(generate());
		super.add(t);
	}
	@Override
	public Product findById(Predicate<Product> pred) {
		return super.findById(pred);
	}
	@Override
	public List<Product> getAll() {
		return super.getAll();
	}
	@Override
	public List<Product> getWhere(Predicate<Product> where) {
		return super.getWhere(where);
	}
	public List<String> getAllName() {
		List<String> name= new ArrayList<>();
		super.getAll().forEach(a -> {
			name.add(a.getName());
		});
		return name;
	}
	@Override
	public void remove(int t) throws IOException {
		super.remove(t);
	}
	@Override
	public void update(int index, Product t) throws IOException {
		super.update(index, t);
	}
	
}
