package main.shape;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import main.util.ColorUtil;
import main.util.MatrixCoordinate;
import main.util.ImageUtil;

public class ImageShaper {
	
	private BufferedImage root;
	private int rootWidth;
	private int rootHeight;
	
	private Comparator<MatrixCoordinate> matrixComparator;
	
	private ArrayList<Shape> shapeList;
	
	public ImageShaper(Image image) {
		root = ImageUtil.copyImage(image);
		shapeList = new ArrayList<Shape>();
		matrixComparator = new Comparator<MatrixCoordinate>() {
			@Override public int compare(MatrixCoordinate a, MatrixCoordinate b) {
				return a.row!=b.row ? a.row-b.row : a.col-b.col;
			}
		};
//		System.out.println("Shapifying...");
		parseShapeList();
//		System.out.println("Shapification Complete: " + shapeList.size() +" shapes found");
	}
	
	public int getShapeCount() {
		return shapeList.size();
	}
	
	public Shape getShape(int index) {
		return shapeList.get(index);
	}
	
	public BufferedImage getShapeTrace(int[]... indexList) {
		BufferedImage res = new BufferedImage(rootWidth, rootHeight, BufferedImage.TYPE_INT_BGR);
		for(int i=0;i<rootWidth;i++) {
			for(int j=0;j<rootHeight;j++)
				res.setRGB(i, j, 16777215);
		}
		
		for(int[] shapeInfo : indexList) {
			Shape shape = getShape(shapeInfo[1]);
			Iterator<MatrixCoordinate> iterator = shape.iterator();
			while(iterator.hasNext()) {
				MatrixCoordinate coordinate = iterator.next();
				res.setRGB(coordinate.col, coordinate.row, shapeInfo[0]);
			}
		}
		
		return res;
	}
	
	public BufferedImage getColoredShapes() {
		int shapeCount = shapeList.size();
		BufferedImage rootCopy = ImageUtil.copyImage(root);
		
		for(int i=0;i<shapeCount;i++)
			addColorToShape(rootCopy, i);
		
		return rootCopy;		
	}
	
	private void addColorToShape(BufferedImage image, int shapeIndex) {
		Shape shape = shapeList.get(shapeIndex);
		
		Iterator<MatrixCoordinate> iterator = shape.iterator();
		while(iterator.hasNext()) {
			MatrixCoordinate coordinate = iterator.next();
			root.setRGB(coordinate.col, coordinate.row, 65280);
		}
		
	}
	
	private void parseShapeList() {
		rootWidth = root.getWidth();
		rootHeight = root.getHeight();
		
		boolean[][] v = new boolean[rootHeight][rootWidth];
		
		for(int row=0;row<rootHeight;row++) {
			for(int col=0;col<rootWidth;col++) {
				if(v[row][col]) continue;
				// System.out.println("> Shape found at " + row + " " + col);
				Shape shape = parseShapeAt(v, row, col);
				shapeList.add(shape);
			}
		}	
	}
	
	private Shape parseShapeAt(boolean[][] v, int rootRow, int rootCol) {
		// BFS to mark shape that root is part of
		
		PriorityQueue<MatrixCoordinate> queue = new PriorityQueue<MatrixCoordinate>(100, matrixComparator);
		MatrixCoordinate curCoordinate = new MatrixCoordinate(rootRow, rootCol);
		queue.add(curCoordinate);
		ArrayList<MatrixCoordinate> coordinates = new ArrayList<MatrixCoordinate>();
		int curColor = root.getRGB(rootCol, rootRow);
		
		while(queue.size() > 0) {
			curCoordinate = queue.poll();
			
//			if(isBorderCoordinate(curCoordinate))
			coordinates.add(curCoordinate);
			
			v[curCoordinate.row][curCoordinate.col] = true;
//			curColor = root.getRGB(curCoordinate.col, curCoordinate.row);
			int nextColor;
			
			// top
			if(curCoordinate.row-1 >= 0 && !v[curCoordinate.row-1][curCoordinate.col]) {
				nextColor = root.getRGB(curCoordinate.col, curCoordinate.row-1);
				if(ColorUtil.isColorSimilar(curColor, nextColor)) {
					v[curCoordinate.row-1][curCoordinate.col] = true; 
					queue.add(new MatrixCoordinate(curCoordinate.row-1, curCoordinate.col));
				}
			}
			
			// right
			if(curCoordinate.col+1 < rootWidth && !v[curCoordinate.row][curCoordinate.col+1]) {
				nextColor = root.getRGB(curCoordinate.col+1, curCoordinate.row);
				if(ColorUtil.isColorSimilar(curColor, nextColor)) {
					v[curCoordinate.row][curCoordinate.col+1] = true;
					queue.add(new MatrixCoordinate(curCoordinate.row, curCoordinate.col+1));
				}
			}
			
			// bottom
			if(curCoordinate.row+1 < rootHeight && !v[curCoordinate.row+1][curCoordinate.col]) {
				nextColor = root.getRGB(curCoordinate.col, curCoordinate.row+1);
				if(ColorUtil.isColorSimilar(curColor, nextColor)) {
					v[curCoordinate.row+1][curCoordinate.col] = true;
					queue.add(new MatrixCoordinate(curCoordinate.row+1, curCoordinate.col));
				}
			}
			
			// left
			if(curCoordinate.col-1 >= 0 && !v[curCoordinate.row][curCoordinate.col-1]) {
				nextColor = root.getRGB(curCoordinate.col-1, curCoordinate.row);
				if(ColorUtil.isColorSimilar(curColor, nextColor)) {
					v[curCoordinate.row][curCoordinate.col-1] = true;
					queue.add(new MatrixCoordinate(curCoordinate.row, curCoordinate.col-1));
				}
			}
			
		}
		
		return new Shape(coordinates);		
	}
	
	/*
	private boolean isBorderCoordinate(MatrixCoordinate coordinate) {

		boolean top = coordinate.row-1 >=0 && 
						ColorUtil.isColorSimilar(
								root.getRGB(coordinate.col, coordinate.row), 
								root.getRGB(coordinate.col, coordinate.row-1)
					   );
		boolean right = coordinate.col+1<rootWidth && 
				ColorUtil.isColorSimilar(
						root.getRGB(coordinate.col, coordinate.row), 
						root.getRGB(coordinate.col+1, coordinate.row)
			   );
		boolean bottom = coordinate.row+1<rootHeight && 
				ColorUtil.isColorSimilar(
						root.getRGB(coordinate.col, coordinate.row), 
						root.getRGB(coordinate.col, coordinate.row+1)
			   );
		boolean left = coordinate.col-1>=0 && 
				!ColorUtil.isColorSimilar(
						root.getRGB(coordinate.col, coordinate.row), 
						root.getRGB(coordinate.col-1, coordinate.row)
			   );
		return !(top && right && bottom && left);		
	}
	*/
}
