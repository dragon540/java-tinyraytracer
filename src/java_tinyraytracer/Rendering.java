package java_tinyraytracer;

import java.io.FileWriter;
import java.io.BufferedWriter;
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
	
	public void renderToFile() {
		createPPMFile(256, 256);
		try {
			FileWriter fwrite = new FileWriter("../java_tinyraytracer/img.ppm", true);
			BufferedWriter bwrite = new BufferedWriter(fwrite);
			
			
			  ArrayList<Vec3f> framebuffer = new ArrayList<Vec3f>(256*256);
			  
			  for(int i=0;i<256*256;i++) {
				  framebuffer.add(new Vec3f(0, 0, 100));
			  }
			 
			
			for (int i = 0; i < 256*256; ++i) {
		        for (int j = 0; j<3; j++) {
		            bwrite.write((char) (int)(framebuffer.get(i)).rgb[j]);
		        }
		    }
			
			
			/****for(int i=0;i<256*256;i++) {
				for(int j=0;j<3;j++) {
					if(i<(256*256)/2) {
						if(j==0) {
							bwrite.write((char)255);
						}
						else if(j==1) {
							bwrite.write((char)255);
						}
						else if(j==2){
							bwrite.write((char)255);
						}
					}
					else {
						if(j==0) {
							bwrite.write((char)255);
						}
						else if(j==1) {
							bwrite.write((char)255);
						}
						else if(j==2){
							bwrite.write((char)255);
						}
					}
					
				}
			}****/
			bwrite.close();
			
		}
		catch (Exception e) {
			
		}
		
	}
}
