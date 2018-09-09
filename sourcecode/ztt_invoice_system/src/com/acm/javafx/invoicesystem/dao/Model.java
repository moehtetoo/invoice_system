package com.acm.javafx.invoicesystem.dao;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public interface Model<T> {

	public void add(T t) throws IOException;
	public List<T> getAll();
	public List<T> getWhere(Predicate<T> pred);
	public T findById(Predicate<T> pred);
	public void update(int index,T t) throws IOException;
	public void remove(int t) throws IOException;
	public int size();
	public void saveOneData(String s) throws IOException;
}
