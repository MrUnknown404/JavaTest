package main.java.javatest.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Console {
	
	public static final EnumWarningType DEFAULT_TYPE = EnumWarningType.Debug;
	
	/** Adds console info Example: <p> [12:34:56:789] [Info] [ExampleClass.exampleMethod] [Hour/Minute/Second/Millisecond] */
	public static String getTimeExample() {
		Date date = new Date();
		String strDateFormat = "hh:mm:ss:SSS";
		DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		String formattedDate = dateFormat.format(date);
		
		return "[" + formattedDate + "] [" + EnumWarningType.Info + "] [" + getCallerClassName() + "] [Hour/Minute/Second/Millisecond]";
	}
	
	/** Adds console info Example: <p> [12:34:56:789] [Fatal Error] [ExampleClass.exampleMethod] : Hello! */
	public static String info(EnumWarningType type) {
		Date date = new Date();
		String strDateFormat = "hh:mm:ss:SSS";
		DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		String formattedDate = dateFormat.format(date);
		
		return "[" + formattedDate + "] [" + type.toString() +  "] [" + getCallerClassName() + "] : ";
	}
	
	/** Adds console info Example: <p> [12:34:56:789] [Debug] [ExampleClass.exampleMethod] : Hello! */
	public static String info() {
		Date date = new Date();
		String strDateFormat = "hh:mm:ss:SSS";
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
	
	public enum EnumWarningType {
		Debug     (0),
		Info      (1),
		Warning   (2),
		Error     (3),
		FatalError(4);
		
		public final int fId;
		
		private EnumWarningType(int id) {
			fId = id;
		}

		public static EnumWarningType getNumber(int id) {
			for (EnumWarningType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
