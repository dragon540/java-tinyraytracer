package java_tinyraytracer;

import java.io.File;
import java.util.Formatter;
import java.util.Scanner;

public class Rendering {
	public void createPPMFile(int width, int height) {
		try {
			Formatter form = new Formatter("../java_tinyraytracer/img.ppm");
			// inserting "magic" PPM identifier on top of the file 
			form.format("%s\n", "P6");
			// inserting width, height, and max. value of color component
			form.format("%s %s\n%s\n", Integer.toString(width), Integer.toString(height), "255");
			
			for(int i=0;i<height*width;i++) {
				for(int j=0;j<3;j++) {
					if(j==0) {
						form.format("%c", 255);
					}
					else if(j==1) {
						form.format("%c", 192);
					}
					else if(j==2){
						form.format("%c", 203);
					}
				}
			}
			form.close();
		}
		catch (Exception e) {
			System.out.println("Cannot create file");
		}
	}
	
	/***public void renderToFile() {
		Vec3f vec3f = new Vec3f(0, 0, 255);
		createPPMFile(256, 256);
		try {
			File form = new File("../java_tinyraytracer/img.ppm");
			Scanner scan = new Scanner(form);
			while(scan.hasNext()) {
				scan.next();
			}
		}
		catch (Exception e) {
			
		}
		
	}****/
}
