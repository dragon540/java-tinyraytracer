package java_tinyraytracer;

// Object type to store r, g, b values of every pixel
public class Vec3f {
	public float [] points = new float[3];
	
	public Vec3f(float x, float y, float z) {
		points[0] = x;
		points[1] = y;
		points[2] = z;
	}
	
}
