package java_tinyraytracer;

public class Sphere {
	public float x, y, z;
	public Vec3f center;
	public float radius;
	
	public Sphere(float x_coor, float y_coor, float z_coor, float rad) {
		x = x_coor;
		y = y_coor;
		z = z_coor;
		radius = rad;
		
		center = new Vec3f(x, y, z);
	}

}
