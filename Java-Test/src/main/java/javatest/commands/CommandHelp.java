package main.java.javatest.commands;

import java.util.List;

import main.java.javatest.Main;

public class CommandHelp extends Command {
	
	public CommandHelp() {
		super("help", 0, null);
	}
	
	@Override
	public String useage() {
		return "/help : usage -> /help : Prints all commands and there useage!";
	}
	
	@Override
	public void doCommand(List<Integer> arg1, List<Float> arg2, List<Double> arg3, List<Boolean> arg4, List<String> arg5) {
		for (Command cmd : Main.getCommandConsole().commands) {
			if (cmd instanceof CommandHelp) {
				Main.getCommandConsole().addLine(useage());
			} else if (cmd instanceof CommandDebug) {
				Main.getCommandConsole().addLine(((CommandDebug) cmd).useage());
			}
		}
	}
}
