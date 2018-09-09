package com.acm.javafx.invoicesystem.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BaseModel<T> implements Model<T> {

	private Function<T, String> toString;
	private Function<String, T> toObject;
	private List<T> list;
	private String path;

	BaseModel(Function<T, String> toString,
			Function<String, T> toObject, String path) {
		super();
		this.toString = toString;
		this.toObject = toObject;
		this.path = path;

		try {
			this.loadTable();
		} catch (IOException e) {
			// first time
			System.err.println("First Time");
			e.printStackTrace();
		}
	}

	private void loadTable() throws IOException {
		list = new ArrayList<>();
		list.addAll(Files.readAllLines(Paths.get(path))
				.stream()
				.map(toObject)
				.collect(Collectors.toList()));
	}

	@Override
	public void add(T t) throws IOException {
		this.list.add(t);
		List<String> strList = new ArrayList<>();
		
		strList.addAll(list.stream().map(toString).collect(Collectors.toList()));
		
		Files.write(Paths.get(path), strList, StandardOpenOption.CREATE);
	}

	@Override
	public List<T> getAll() {
		return this.list;
	}

	@Override
	public List<T> getWhere(Predicate<T> where) {
		return this.list.stream().filter(where).collect(Collectors.toList());
	}

	@Override
	public T findById(Predicate<T> pred) {
		List<T> result = this.getWhere(pred);
		if(result.size() == 1)
			return result.get(0);
		
		return null;
	}
	
	protected int generate() {
		return list.size() + 1;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void update(int index,T t) throws IOException {
		this.list.set( index, t);
		List<String> strList = new ArrayList<>();
		strList.addAll(list.stream().map(toString).collect(Collectors.toList()));
		Files.write(Paths.get(path), strList, StandardOpenOption.CREATE);
	}

	@Override
	public void remove(int t) throws IOException {
		this.list.remove(t);
		List<String> strList = new ArrayList<>();
		strList.addAll(list.stream().map(toString).collect(Collectors.toList()));
		Files.write(Paths.get(path), strList, StandardOpenOption.CREATE);
	}
	@Override
	public void saveOneData(String s) throws IOException {
		List<String> str= new ArrayList<>();
		str.add(s);
		Files.write(Paths.get(path), str, StandardOpenOption.CREATE);
	}
}
