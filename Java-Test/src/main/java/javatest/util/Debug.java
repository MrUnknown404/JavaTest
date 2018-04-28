package main.java.javatest.util;

public class Debug {
	/** Gets what class is called */
	public static String getCallerName(String className) {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		for (int i = 1; i < stElements.length; i++) {
			StackTraceElement ste = stElements[i];
			if (!ste.getClassName().equals(className) && !ste.getClassName().equals(Debug.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
				return ste.getClassName().replaceAll(ste.getClassName().substring(0, ste.getClassName().lastIndexOf('.') + 1), "") + "." + ste.getMethodName() + "." + ste.getLineNumber();
			}
		}
		return null;
	}
}
