package main.util;

public class MatrixCoordinate {
	public int row;
	public int col;
	
	public MatrixCoordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public String toString() {
		return "("+row+","+col+")";
	}
}
