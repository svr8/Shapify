package main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

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
		
		int[][] shapeInfo = new int[shapeCount][2];
		for(int i=0;i<shapeCount;i++) {
			shapeInfo[i]= new int[] {color[cIndex], i};
			cIndex = (cIndex+1)%3;
		}
		
		BufferedImage shapfiedImage = shaper.getShapeTrace(shapeInfo);
		
		OutputStream shapeStream = new FileOutputStream("images/shapified.jpeg");
		ImageIO.write(shapfiedImage, "jpeg", shapeStream);
		shapeStream.close();
	}

}
