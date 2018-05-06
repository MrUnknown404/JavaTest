package main.java.javatest.commands;

import java.util.List;

import javax.annotation.Nullable;

import main.java.javatest.Main;

public abstract class Command {

	private String name;
	private int amountOfArgs;
	private ArgumentType[] typeOfArgs;
	
	public Command(String name, int amountOfArgs, @Nullable ArgumentType[] typeOfArgs) {
		this.name = name;
		this.amountOfArgs = amountOfArgs;
		if (typeOfArgs != null) {
			this.typeOfArgs = typeOfArgs;
		}
		Main.getCommandConsole().commands.add(this);
	}
	
	public abstract String useage();
	public abstract void doCommand(List<Integer> arg1, List<Float> arg2, List<Double> arg3, List<Boolean> arg4, List<String> arg5);
	
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
