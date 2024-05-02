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
		        	framebuffer.add(i+j*width, new Vec3f(j/(float)height, i/(float)width, 0));
		        }
		    }
			
			// red
			Vec3f c1 = new Vec3f(127, 0, 0);
			// blue
			Vec3f c2 = new Vec3f(0, 0, 127);
			
			Vec3f origin = new Vec3f(50, 50, 0);
			
			Sphere sphere = new Sphere(100, 100, 0, 50);
			
			cast_ray(framebuffer, height, width, origin, sphere, c1, c2);
			
			
			// write pixel values to .ppm image file
			for (int i = 0; i < height*width; i++) {
		        for (int j = 0; j<3; j++) {
		            //fstream.write((byte)( 255 * Math.max(0.f, Math.min(1.f, (framebuffer.get(i)).points[j]))));
		        	fstream.write((byte)  (framebuffer.get(i)).points[j] );
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
	
	/**
	 * To check if a given ray intersects a sphere (defined in Sphere.java),
	 * when the origin of the ray and the direction of the ray is given.
	 **/
	public boolean ray_intersect(Vec3f origin, Vec3f dir, Sphere sphere) {
		Vec3f vec_obj = new Vec3f(0, 0, 0);
		
		dir.points[0] = dir.points[0] - origin.points[0];
		dir.points[1] = dir.points[1] - origin.points[1];
		dir.points[2] = dir.points[2] - origin.points[2];
		
		// dist is the perpendicular distance form the center of the circle to the ray
		float dist = Math.abs(vec_obj.dot_product(dir, sphere.center));
		if(dist <= sphere.radius) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public void cast_ray(ArrayList<Vec3f> framebuffer, int height, int width,
										Vec3f origin, Sphere sphere,
										Vec3f c1, Vec3f c2) {
		for(int j=0;j<height;j++) {
			for(int i=0;i<width;i++) {
				//Vec3f dir = new Vec3f(i-origin.points[0], j-origin.points[1], 0);
				/***double norm = Math.sqrt(Math.abs(dir.points[0]*dir.points[0] + dir.points[1]*dir.points[1] + 
						dir.points[2]*dir.points[2]));
				dir.points[0] = dir.points[0]/(float)norm;
				dir.points[1] = dir.points[1]/(float)norm;
				dir.points[2] = dir.points[2]/(float)norm;****/
				
				float x =  (float) ((2*(i + 0.5)/(float)width  - 1)* ((float)Math.tan(60.00))*width/(float)height);
	            float y = (float) (-(2*(j + 0.5)/(float)height - 1)*Math.tan(60));
	            Vec3f dir = new Vec3f(x, y, -1);
	            dir = dir.normalise(dir.points[0], dir.points[1], dir.points[2]);
				
				if(ray_intersect(origin, dir, sphere) == false) {
					// give color1 to pixel
					framebuffer.set(i+j*width, new Vec3f(c1.points[0], c1.points[1], c1.points[2]));
				}
				else {
					// give color2 to pixel
					framebuffer.set(i+j*width, new Vec3f(c2.points[0], c2.points[1], c2.points[2]));
				}
			}
		}
	}
	
	
}
