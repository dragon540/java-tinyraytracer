package java_tinyraytracer;

// Object type to store r, g, b values of every pixel
public class Vec3f {
	public float [] rgb = new float[3];
	
	public Vec3f(float red, float green, float blue) {
		rgb[0] = red;
		rgb[1] = green;
		rgb[2] = blue;
	}
}
