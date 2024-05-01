package java_tinyraytracer;

import java.io.FileOutputStream;
import java.util.Formatter;
import java.util.ArrayList;

public class Rendering {
	public void createPPMFile(int width, int height) {
		try {
			Formatter form = new Formatter("../java_tinyraytracer/img.ppm");
			// inserting "magic" PPM identifier on top of the file 
			form.format("%s\n", "P6");
			// inserting width, height, and max. value of color component
			form.format("%s %s\n%s\n", Integer.toString(width), Integer.toString(height), "255");
			form.close();
		}
		catch (Exception e) {
			System.out.println("Cannot create file");
		}
	}
	
	public void renderToFile(int width, int height) {
		createPPMFile(width, height);
		try {
			/**
			 * Using FileOutputStream instead of FileWriter and BufferedWriter since binary data is to
			 * be written to the P6 PPM format
			 */
			FileOutputStream fstream = new FileOutputStream("../java_tinyraytracer/img.ppm", true);
			
			ArrayList<Vec3f> framebuffer = new ArrayList<Vec3f>(width*height);
			
			// filling Vec3f(rgb values) to an arraylist for each pixel of the image file  
			for (int j = 0; j<height; j++) {
		        for (int i = 0; i<width; i++) {
		        	// filling pixels with a gradient of colors
		        	framebuffer.add(i+j*width, new Vec3f(j/(float)(height),i/(float)(width), 0));
		        }
		    }
			// write pixel values to .ppm image file
			for (int i = 0; i < height*width; i++) {
		        for (int j = 0; j<3; j++) {
		            fstream.write((byte)( 255 * Math.max(0.f, Math.min(1.f, (framebuffer.get(i)).rgb[j]))));
		        }
		    }
			
			/***for(int i = 0; i < height*width; i++) {
		        for(int j = 0; j<3; j++) {
		        	byte val = (byte)(framebuffer.get(i)).rgb[j];
		        	fstream.write(val);
		        }
		    }***/
			fstream.close();
		}
		catch (Exception e) {
			System.out.println("Cannot open img.ppm to write");
		}
		
	}
}
