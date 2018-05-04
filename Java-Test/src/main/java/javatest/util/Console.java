package main.java.javatest.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Console {
	
	private static final WarningType[] disableTypes = {};
	
	/** Prints date info to the console Example: <p> [12:34:56:789] [Info] [ExampleClass.exampleMethod.69] [Hour/Minute/Second/Millisecond] */
	public static void getTimeExample() {
		System.out.println( "[" + new SimpleDateFormat("hh:mm:ss:SSS").format(new Date()) + "] [" + WarningType.Info + "] [" + Debug.getCallerName(Console.class.getName()) + "] [Hour/Minute/Second/Millisecond]");
	}
	
	/** Prints date info plus the given string to the console Example: <p> [12:34:56:789] [Debug] [ExampleClass.exampleMethod.69] : Hello! */
	public static void print(WarningType type, String string) {
		if (disableTypes.length != 0) {
			for (int i = 0; i < disableTypes.length; i++) {
				if (disableTypes[i] == type) {
					return;
				}
			}
		}
		
		if (type == WarningType.Error || type == WarningType.FatalError) {
			System.err.println("[" + new SimpleDateFormat("hh:mm:ss:SSS").format(new Date()) + "] [" + type.toString() +  "] [" + Debug.getCallerName(Console.class.getName()) + "] : " + string);
			return;
		}
		System.out.println("[" + new SimpleDateFormat("hh:mm:ss:SSS").format(new Date()) + "] [" + type.toString() +  "] [" + Debug.getCallerName(Console.class.getName()) + "] : " + string);
	}
	
	/** Prints date info plus the given string to the console Example: <p> [12:34:56:789] [Debug] [ExampleClass.exampleMethod.69] : Hello! */
	public static void print(String string) {
		if (disableTypes.length != 0) {
			for (int i = 0; i < disableTypes.length; i++) {
				if (disableTypes[i] == WarningType.Debug) {
					return;
				}
			}
		}
		
		System.out.println("[" + new SimpleDateFormat("hh:mm:ss:SSS").format(new Date()) + "] [" + WarningType.Debug +  "] [" + Debug.getCallerName(Console.class.getName()) + "] : " + string);
	}
	
	public enum WarningType {
		Debug     (0),
		Info      (1),
		Warning   (2),
		Error     (3),
		FatalError(4);
		
		private final int fId;
		
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
