package main.java.javatest.commands;

import java.util.List;

import javax.annotation.Nullable;

public abstract class Command {

	protected boolean isArgsOptional;
	private String name;
	private int amountOfArgs;
	private ArgumentType[] typeOfArgs;
	
	public Command(String name, @Nullable ArgumentType[] typeOfArgs, boolean isArgsOptional) {
		this.name = name;
		this.amountOfArgs = typeOfArgs.length;
		this.isArgsOptional = isArgsOptional;
		if (typeOfArgs != null) {
			this.typeOfArgs = typeOfArgs;
		}
	}
	
	public abstract String usage();
	public abstract void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString);
	
	public String getName() {
		return name;
	}
	
	public int getAmountOfArgs() {
		return amountOfArgs;
	}
	
	public ArgumentType[] getArgumentType() {
		return typeOfArgs;
	}
	
	public enum ArgumentType {
		Integer(0),
		Float  (1),
		Double (2),
		Boolean(3),
		String (4);
		
		private final int fId;
		
		private ArgumentType(int id) {
			fId = id;
		}
		
		public static ArgumentType getNumber(int id) {
			for (ArgumentType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
