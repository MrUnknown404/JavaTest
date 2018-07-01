package main.java.javatest.commands;

import java.util.List;

import main.java.javatest.Main;

public class CommandHelp extends Command {
	
	private static Command.ArgumentType[] types = {Command.ArgumentType.String};
	
	public CommandHelp() {
		super("help", types, true);
	}
	
	@Override
	public String usage() {
		StringBuilder b = new StringBuilder();
		for (Command.ArgumentType type : types) {
			b.append("<" + type.toString() + "> ");
		}
		
		return "/help : usage -> /help (optional) " + b.toString() + " : Prints all commands and there useage!";
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		boolean tb = false;
		for (Command cmd : Main.getCommandConsole().commands) {
			if (argString.isEmpty()) {
				Main.getCommandConsole().addLine(cmd.usage());
				tb = true;
			} else {
				if (Main.getCommandConsole().findCommand(cmd.getName()).getName().equals(argString.get(0))) {
					Main.getCommandConsole().addLine(cmd.usage());
					tb = true;
				}
			}
		}
		if (!tb) {
			Main.getCommandConsole().addLine("/" + getName() + " " + argString.get(0));
			Main.getCommandConsole().addLine("* Did not find any commands named " + argString.get(0) + "!");
		}
	}
}
