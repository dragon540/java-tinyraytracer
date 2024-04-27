package java_tinyraytracer;

import java.util.Formatter;

public class Rendering {
	public void createFile() {
		try {
			Formatter form = new Formatter("../java_tinyraytracer/file.txt");
			form.close();
		}
		catch (Exception e) {
			System.out.println("Cannot create file");
		}
	}
}
