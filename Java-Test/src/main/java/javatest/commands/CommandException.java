package main.java.javatest.commands;

import main.java.javatest.Main;

public class CommandException extends Exception {
	private static final long serialVersionUID = -4961238226937673460L;
	
	public CommandException(String message) {
		super(message);
	}
	
	public CommandException(Exceptions exception) {
		super(exception.toString());
		Main.getCommandConsole().addLine(Main.getCommandConsole().input.trim());
		Main.getCommandConsole().clearInput();
		
		switch (exception) {
			case noSlash:
				Main.getCommandConsole().addLine("* Commands must start with a /");
				System.err.println("Commands must start with a /");
				break;
			case noCommand:
				Main.getCommandConsole().addLine("* No command was written");
				System.err.println("No command was written");
				break;
			case noArgs:
				Main.getCommandConsole().addLine("* No arguments were written");
				System.err.println("No arguments were written");
				break;
			case notEnoughArgs:
				Main.getCommandConsole().addLine("* Not enough arguments were written");
				System.err.println("Not enough arguments were written");
				break;
			case tooManyArgs:
				Main.getCommandConsole().addLine("* Too many arguments were written");
				System.err.println("Too many arguments were written");
				break;
			case wrongArg:
				Main.getCommandConsole().addLine("* One of the written arguments is not the correct type");
				System.err.println("One of the written arguments is not the correct type");
				break;
			case notACommand:
				Main.getCommandConsole().addLine("* No valid command was written");
				System.err.println("No valid command was written");
				break;
			default:
				Main.getCommandConsole().addLine("* Null something broke!");
				System.err.println("Null something broke!");
				break;
		}
	}
	
	public enum Exceptions {
		nil          (0),
		noSlash      (1),
		noCommand    (2),
		noArgs       (3),
		notEnoughArgs(4),
		tooManyArgs  (5),
		wrongArg     (6),
		notACommand  (7);
		
		private final int fId;
		
		private Exceptions(int id) {
			fId = id;
		}

		public static Exceptions getNumber(int id) {
			for (Exceptions type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
