package main.util;

public class ColorUtil {
	
	public static boolean isColorSimilar(int color1, int color2) {
        int[] c1 = splitColor(color1);
        int[] c2 = splitColor(color2);

        double d = 0.0;
        for(int i=1;i<=3;i++)
            d += Math.pow(c1[i]-c2[i], 2);
        d = Math.sqrt(d);

        return d <= 100;
    }
	
	public static int wrapColor(int[] a) {
        int c = (a[0]<<24) | (a[1]<<16) | (a[2]<<8) | a[3];
        return c;
    }

    public static int[] splitColor(int c) {
        int a = (c >> 24) & 0xff;
        int r = (c >> 16) & 0xff;
        int g = (c >> 8) & 0xff;
        int b = c & 0xff;
        return new int[] {a, r, g, b};
    }

}
