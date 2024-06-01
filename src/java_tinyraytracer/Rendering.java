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
		        	framebuffer.add(i+j*width, new Vec3f(127, 0, 0));
		        	//framebuffer.add(i+j*width, new Vec3f(j/(float)height, i/(float)width, 0));
		        }
		    }
			
			// background : blue
			Vec3f c1 = new Vec3f(48, 213, 200);
			// foreground : brown
			Vec3f c2 = new Vec3f(181, 101, 29);
			
			Sphere sphere = new Sphere(100, 100, 0, 15);
			
			render2(height, width, sphere, framebuffer, c1, c2);
			
			
			// write pixel values to .ppm image file
			for (int i = 0; i < height*width; i++) {
		        for (int j = 0; j<3; j++) {
		            //fstream.write((byte)( 255 * Math.max(0.f, Math.min(1.f, (framebuffer.get(i)).points[j]))));
		        	fstream.write((byte)  (framebuffer.get(i)).points[j] );
		        }
		    }
			
			fstream.close();
		}
		catch (Exception e) {
			System.out.println("Cannot open img.ppm to write");
		}
		
	}
	
	/*** Helper function to calculate dot product of 2 Vec3f objects ***/
	public float dot_product(Vec3f v1, Vec3f v2) {
		return ( (v1.points[0] * v2.points[0]) +
				 (v1.points[1] * v2.points[1]) +
				 (v1.points[2] * v2.points[2]) );
 	}
	
	/*** Helper function to calculate cross product of 2 Vec3f objects ***/
	public Vec3f cross_product(Vec3f v1, Vec3f v2) {
		float v1a = v1.points[0];
		float v1b = v1.points[1];
		float v1c = v1.points[2];
		
		float v2a = v1.points[0];
		float v2b = v1.points[1];
		float v2c = v1.points[2];
		Vec3f resultant = new Vec3f( (v1b*v2c - v2b*v1c), (v1a*v2c - v2a*v1c), (v1a*v2b - v2a*v1b));
		
		return resultant;
		
	}
	
	/*** Helper function to calculate unit vector of Vec3f ***/
	public Vec3f unit_vectorise(Vec3f pixel) {
		float x = pixel.points[0];
		float y = pixel.points[1];
		float z = pixel.points[2];
		float norm = (float)Math.sqrt(x*x + y*y + z*z);
		Vec3f vec = new Vec3f(x/norm, y/norm, z/norm);
	
		return vec;
	}
	
	/*** Helper function to subtract two Vec3f ***/
	public Vec3f sub(Vec3f v1, Vec3f v2) {
		Vec3f res = new Vec3f( (v1.points[0] - v2.points[0]), 
							   (v1.points[1] - v2.points[1]),
							   (v1.points[2] - v2.points[2]) );
		return res;
	}
	
	/*** Helper function to find distance between two vec3f ***/
	public float dist(Vec3f v1, Vec3f v2) {
		float x_comp = (float)Math.pow((double)(v1.points[0] - v2.points[0]), 2);
		float y_comp = (float)Math.pow((double)(v1.points[1] - v2.points[1]), 2);
		float z_comp = (float)Math.pow((double)(v1.points[2] - v2.points[2]), 2);
		
		float dist = (float)Math.sqrt(x_comp + y_comp + z_comp);
		
		return dist;
	}
	
	/*** Helper function to find magnitude of a Vec3f ***/
	public float magn(Vec3f v) {
		float x = v.points[0];
		float y = v.points[1];
		float z = v.points[2];
		return (float)Math.sqrt(x*x + y*y + z*z);
		
	}
	
	/**
	 * To check if a given ray intersects a sphere (defined in Sphere.java),
	 * when the origin of the ray and the direction of the ray is given.
	 **/
	public boolean ray_intersect(Vec3f origin, Vec3f dir, Sphere sphere, float i, float j) {
		Vec3f scenterToinit = sub(sphere.center, new Vec3f(i, j, 0));
		
		float x_vec = scenterToinit.points[0];
		float y_vec = scenterToinit.points[1];
		float z_vec = scenterToinit.points[2];
		
		float angle_cosine = dot_product(scenterToinit, dir)/(magn(scenterToinit));
		
		System.out.println(magn(dir)); // for debugging
		
		Vec3f sphereCenterOnRay = new Vec3f(x_vec*angle_cosine, y_vec*angle_cosine, z_vec*angle_cosine);
		
		float distance = dist(sphere.center, sphereCenterOnRay);
		
		System.out.println("radius" + sphere.radius);
		System.out.println("distance" + distance);
		System.out.println("cosine" + angle_cosine);
		System.out.println("magn(scenter)" + magn(scenterToinit));
		System.out.println("_x" + scenterToinit.points[0]);
		System.out.println("_y" + scenterToinit.points[1]);
		System.out.println("_z" + scenterToinit.points[2]);
		
		if(distance > sphere.radius) {
			return false;
		}
		else {
			return true;
		}
	}
	
	
	public Vec3f cast_ray(Vec3f origin, Vec3f dir, Sphere sphere, Vec3f col1, Vec3f col2, float i, float j) {
		if(!ray_intersect(origin, dir, sphere, i, j)) {
			return col1;
		}
		else {
			return col2;
		}
	}
	
	public void render2(int height, int width, Sphere sphere, ArrayList<Vec3f> framebuffer, 
			Vec3f col1, Vec3f col2) {
		
		Vec3f origin = new Vec3f(0, 0, 0);
		for(int j=0;j<height;j++) {
			for(int i=0;i<width;i++) {
				//float x =  (float) ((2*(i + 0.5)/(float)width  - 1)* ((float)Math.tan(60.00))*width/(float)height);
	            //float y = (float) (-(2*(j + 0.5)/(float)height - 1)*Math.tan(60));
				
				float x = i - origin.points[0];
				float y = j - origin.points[1];
	            
	            Vec3f dir = unit_vectorise(new Vec3f(x, y, 0));
	            framebuffer.add(i+j*width, 
	            		cast_ray(origin, dir, sphere, col1, col2, x, y));
			}
		}
	}
}
