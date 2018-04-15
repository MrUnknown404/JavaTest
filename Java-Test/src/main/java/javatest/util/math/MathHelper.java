package main.java.javatest.util.math;

public class MathHelper {
	/** Returns the greatest integer less than or equal to the double argument */
	public static int floor(double value) {
		int i = (int) value;
		return value < (double) i ? i - 1 : i;
	}
	
	/** Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and third parameters */
	public static double clamp(double num, double min, double max) {
		return num < min ? min : (num > max ? max : num);
	}
}
