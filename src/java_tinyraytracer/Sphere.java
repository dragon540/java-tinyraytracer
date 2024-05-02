package java_tinyraytracer;

public class Sphere {
	float x, y, z;
	Vec3f center = new Vec3f(x, y, z);
	float radius;
	
	public Sphere(float x_coor, float y_coor, float z_coor, float rad) {
		x = x_coor;
		y = y_coor;
		z = z_coor;
		radius = rad;
	}

}
