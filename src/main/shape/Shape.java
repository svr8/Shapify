package main.shape;

import java.util.ArrayList;
import java.util.Iterator;

import main.util.MatrixCoordinate;

public class Shape {
	
	private ArrayList<MatrixCoordinate> list;
	
	Shape(ArrayList<MatrixCoordinate> list) {
		this.list = new ArrayList<MatrixCoordinate>();
		this.list.addAll(list);
	}
	
	public Iterator<MatrixCoordinate> iterator() {
		return list.iterator();
	}
	
}