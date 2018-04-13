package main.java.javatest.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Console {
	
	public static final EnumWarningType DEFAULT_TYPE = EnumWarningType.Debug;
	
	/** Adds console info Example --> [12:34:56 AM] [Fatal Error] [ExampleClass] : Hello! */
	public static String info(EnumWarningType type) {
		Date date = new Date();
		String strDateFormat = "hh:mm:ss a";
		DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		String formattedDate = dateFormat.format(date);
		
		return "[" + formattedDate + "] [" + type.toString() +  "] [" + getCallerClassName() + "] : ";
	}
	
	/** Adds console info Example --> [12:34:56 AM] [Fatal Error] [ExampleClass] : Hello! */
	public static String info() {
		Date date = new Date();
		String strDateFormat = "hh:mm:ss a";
		DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		String formattedDate = dateFormat.format(date);
		
		return "[" + formattedDate + "] [" + DEFAULT_TYPE + "] [" + getCallerClassName() + "] : ";
	}
	
	private static String getCallerClassName() { 
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		for (int i = 1; i < stElements.length; i++) {
			StackTraceElement ste = stElements[i];
			if (!ste.getClassName().equals(Console.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
				return ste.getClassName().replaceAll(ste.getClassName().substring(0, ste.getClassName().lastIndexOf('.') + 1), "") + "." + ste.getMethodName();
			}
		}
		return null;
	}
}
