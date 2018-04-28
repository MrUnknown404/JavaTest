package main.java.javatest.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Console {
	
	public static final WarningType DEFAULT_TYPE = WarningType.Debug;
	
	/** Adds console info Example: <p> [12:34:56:789] [Info] [ExampleClass.exampleMethod] [Hour/Minute/Second/Millisecond] */
	public static String getTimeExample() {
		Date date = new Date();
		String strDateFormat = "hh:mm:ss:SSS";
		DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		String formattedDate = dateFormat.format(date);
		
		return "[" + formattedDate + "] [" + WarningType.Info + "] [" + Debug.getCallerName(Console.class.getName()) + "] [Hour/Minute/Second/Millisecond]";
	}
	
	/** Adds console info Example: <p> [12:34:56:789] [Fatal Error] [ExampleClass.exampleMethod] : Hello! */
	public static String info(WarningType type) {
		Date date = new Date();
		String strDateFormat = "hh:mm:ss:SSS";
		DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		String formattedDate = dateFormat.format(date);
		
		return "[" + formattedDate + "] [" + type.toString() +  "] [" + Debug.getCallerName(Console.class.getName()) + "] : ";
	}
	
	/** Adds console info Example: <p> [12:34:56:789] [Debug] [ExampleClass.exampleMethod] : Hello! */
	public static String info() {
		Date date = new Date();
		String strDateFormat = "hh:mm:ss:SSS";
		DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		String formattedDate = dateFormat.format(date);
		
		return "[" + formattedDate + "] [" + DEFAULT_TYPE + "] [" + Debug.getCallerName(Console.class.getName()) + "] : ";
	}
	
	public enum WarningType {
		Debug     (0),
		Info      (1),
		Warning   (2),
		Error     (3),
		FatalError(4);
		
		public final int fId;
		
		private WarningType(int id) {
			fId = id;
		}

		public static WarningType getNumber(int id) {
			for (WarningType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
