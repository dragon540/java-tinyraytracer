package java_tinyraytracer;

// Object type to store r, g, b values of every pixel
public class Vec3f {
	public float [] points = new float[3];
	
	public Vec3f(float x, float y, float z) {
		points[0] = x;
		points[1] = y;
		points[2] = z;
	}
	
	public float dot_product(Vec3f vec1, Vec3f vec2) {
		float res = ( vec1.points[0]*vec2.points[0] +
					  vec1.points[1]*vec2.points[1] +
					  vec1.points[2]*vec2.points[2]);
		return res;
	}
	
	public Vec3f normalise(float x, float y, float z) {
		float norm = (float)Math.sqrt(Math.abs(x*x + y*y + z*z));
		Vec3f vec = new Vec3f((x*x)/norm, (y*y)/norm, (z*z)/norm);
		
		return vec;
	}
}
