package main.java.javatest.commands;

import java.util.List;

import main.java.javatest.util.Console;

public class CommandDebug extends Command {

	private static Command.ArgumentType[] types = {Command.ArgumentType.Float, Command.ArgumentType.String};
	
	public CommandDebug() {
		super("debug", 2, types);
	}
	
	@Override
	public String useage() {
		StringBuilder b = new StringBuilder();
		for (Command.ArgumentType type : types) {
			b.append("<" + type.toString() + "> ");
		}
		
		return "/debug : usage -> /debug " + b.toString() + ": Does nothing used for testing!";
	}
	
	@Override
	public void doCommand(List<Integer> arg1, List<Float> arg2, List<Double> arg3, List<Boolean> arg4, List<String> arg5) {
		Console.print("SUCCESSFUL!" + " :" + arg2.get(0) + ":" + arg5.get(0));
	}
}
