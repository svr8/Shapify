package main.imageprocessing;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import main.util.ImageUtil;

public class Tool {
	
	
	public static TreeMap<Integer, Integer> getHistogramRGB(Image image) {
		TreeMap<Integer, Integer> histogram = new TreeMap<Integer, Integer>();
		
		BufferedImage buf = ImageUtil.toBufferedImage(image);
		int width = buf.getWidth();
		int height = buf.getHeight();
		int color;
		
		for(int x=0;x<width;x++) {
			for(int y=0;y<height;y++) {
				color = buf.getRGB(x, y);
				if(histogram.containsKey(color))
					histogram.put(color, histogram.get(color)+1);
				else
					histogram.put(color, 1);
			}
		}
		
		return histogram;
	}
	
	public static TreeMap<Integer, Double> pmf(TreeMap<Integer, Integer> histogram, double tot) {
		TreeMap<Integer, Double> res = new TreeMap<Integer, Double>();
		
		Iterator it = histogram.entrySet().iterator();
		int f;
		double v;
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        f = (int)(pair.getValue());
	        v = f / tot;
	        res.put((Integer)pair.getKey(), v);
	    }
	    
	    return res;
	}
	
	public static BufferedImage equalizeHistogram(Image image) {
		BufferedImage buf = ImageUtil.toBufferedImage(image);
		int width = buf.getWidth();
		int height = buf.getHeight();
		TreeMap<Integer, Integer> histogram = getHistogramRGB(image);
		TreeMap<Integer, Double> pmf = pmf(histogram, width*height);
		
		TreeMap<Integer, Integer> cdf = new TreeMap<Integer, Integer>();
		Iterator it = pmf.entrySet().iterator();
		double cumulativeSum = 0, p;
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        p = cumulativeSum + (double)pair.getValue();
	        cdf.put((Integer)pair.getKey(), (int)(p*(16581375-1)));
	    }		
	    
	    for(int x=0;x<width;x++) {
	    	for(int y=0;y<height;y++) {
	    		int color = buf.getRGB(x, y);
	    		int newColor = cdf.get(color);
	    		buf.setRGB(x, y, newColor);
	    	}
	    }
	    
	    return buf;
	}
	
	

}
