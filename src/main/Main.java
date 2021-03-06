package main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import main.imageprocessing.Tool;
import main.shape.ImageShaper;

public class Main {
	
	public static void main(String[] args)throws Exception {
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream sourceSteam = classLoader.getResourceAsStream("image.jpeg");
		Image sourceImage = ImageIO.read(sourceSteam);
		sourceSteam.close();
		
		ImageShaper shaper = new ImageShaper(sourceImage);
		int[] color = new int[] {15078163, 3775186, 2999361 };
		int cIndex = 0;
		
		int shapeCount = shaper.getShapeCount();
		System.out.println("> "+shapeCount+" shapes found");
		
		shapeCount = 1000;
		int[][] shapeInfo = new int[shapeCount][2];
		for(int i=40;i<shapeCount;i++) {
			shapeInfo[i]= new int[] {color[cIndex], i};
			cIndex = (cIndex+1)%3;
		}
		
		BufferedImage shapfiedImage = shaper.getShapeTrace(shapeInfo);
		
		OutputStream shapeStream = new FileOutputStream("images/shapified2.jpeg");
		ImageIO.write(shapfiedImage, "jpeg", shapeStream);
		shapeStream.close();
		
		/*
		
		BufferedImage eq = Tool.equalizeHistogram(sourceImage);
		OutputStream eqStream = new FileOutputStream("images/eq.jpeg");
		ImageIO.write(eq, "jpeg", eqStream);
		eqStream.close();
		
		shaper = new ImageShaper(eq);
		color = new int[] {15078163, 3775186, 2999361 };
		cIndex = 0;
		
		shapeCount = shaper.getShapeCount();
		System.out.println("> "+shapeCount+" shapes found");
		
		shapeInfo = new int[shapeCount][2];
		for(int i=0;i<shapeCount;i++) {
			shapeInfo[i]= new int[] {color[cIndex], i};
			cIndex = (cIndex+1)%3;
		}
		
		shapfiedImage = shaper.getShapeTrace(shapeInfo);
		
		shapeStream = new FileOutputStream("images/shapified2.jpeg");
		ImageIO.write(shapfiedImage, "jpeg", shapeStream);
		shapeStream.close(); */
	}

}
