package main.java.javatest.commands;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class DebugConsole {
	public List<Command> commands = new ArrayList<Command>();
	
	private static final int MAX_ARGS = 5;
	private static final int MAX_LINES = 6;
	public boolean isConsoleOpen;
	public String input = "";
	public List<String> lines = new ArrayList<String>();;
	
	public DebugConsole() {
		lines.add("");
	}
	
	public void addKey(Character c) {
		input += c;
	}
	
	public void removeKey() {
		input = input.substring(0, input.length() - 1);
	}
	
	public void addLine(String line) {
		if (lines.size() > MAX_LINES) {
			lines.remove(lines.size() - 1);
		}
		lines.add(1, line);
	}
	
	public void addLine() {
		if (lines.size() > MAX_LINES) {
			lines.remove(lines.size() - 1);
		}
		lines.add(1, "");
	}
	
	public void clearInput() {
		input = "";
	}
	
	public Command findCommand(String name) {
		for (int i = 0; i < commands.size(); i++) {
			if (commands.get(i).getName().equals(name)) {
				return commands.get(i);
			}
		}
		return null;
	}
	
	public void finishCommand() throws CommandException {
		String cmd = input.trim();
		if (!cmd.startsWith("/")) {
			throw new CommandException(CommandException.Exceptions.noSlash);
		}
		
		boolean didFind = false;
		for (int i = 0; i < cmd.length(); i++) {
			if (cmd.charAt(i) != KeyEvent.VK_SLASH && input.trim().charAt(i) != 0) {
				didFind = true;
				break;
			}
		}
		if (!didFind) {
			throw new CommandException(CommandException.Exceptions.noCommand);
		}
		
		String cmdName = null;
		if (cmd.indexOf(" ") != -1) {
			cmdName = cmd.substring(1, cmd.indexOf(" "));
		} else {
			cmdName = cmd.substring(1, cmd.length());
		}
		
		Command command = null;
		for (int i = 0; i < commands.size(); i++) {
			if (commands.get(i).getName().equals(cmdName)) {
				command = commands.get(i);
				break;
			}
		}
		if (command == null) {
			throw new CommandException(CommandException.Exceptions.notACommand);
		}
		
		List<Integer> intArgs = new ArrayList<Integer>();
		List<Float> floatArgs = new ArrayList<Float>();
		List<Double> doubleArgs = new ArrayList<Double>();
		List<Boolean> boolArgs = new ArrayList<Boolean>();
		List<String> stringArgs = new ArrayList<String>();
		
		String[] unFormatedArgs = null;
		List<String> formatedArgs = new ArrayList<String>();
		if (cmd.indexOf(" ") != -1) {
			unFormatedArgs = cmd.substring(cmd.indexOf(" "), cmd.length()).split(" ");
			
			for (int i = 0; i < unFormatedArgs.length; i++) {
				if (!unFormatedArgs[i].isEmpty()) {
					formatedArgs.add(unFormatedArgs[i]);
				}
			}
			
			if (formatedArgs.size() < command.getAmountOfArgs()) {
				throw new CommandException(CommandException.Exceptions.notEnoughArgs);
			} else if (formatedArgs.size() > MAX_ARGS || formatedArgs.size() > command.getAmountOfArgs()) {
				throw new CommandException(CommandException.Exceptions.tooManyArgs);
			}
			
			for (int i = 0; i < formatedArgs.size(); i++) {
				if (command.getArgumentType()[i].equals(Command.ArgumentType.Integer)) {
					try {
						intArgs.add(Integer.parseInt(formatedArgs.get(i)));
					} catch (NumberFormatException e) {
						throw new CommandException(CommandException.Exceptions.wrongArg);
					}
				} else if (command.getArgumentType()[i].equals(Command.ArgumentType.Float)) {
					try {
						floatArgs.add(Float.parseFloat(formatedArgs.get(i)));
					} catch (NumberFormatException e) {
						throw new CommandException(CommandException.Exceptions.wrongArg);
					}
				} else if (command.getArgumentType()[i].equals(Command.ArgumentType.Double)) {
					try {
						doubleArgs.add(Double.parseDouble(formatedArgs.get(i)));
					} catch (NumberFormatException e) {
						throw new CommandException(CommandException.Exceptions.wrongArg);
					}
				} else if (command.getArgumentType()[i].equals(Command.ArgumentType.Boolean)) {
					if (formatedArgs.get(i).equals("true")) {
						boolArgs.add(true);
					} else if (formatedArgs.get(i).equals("false")) {
						boolArgs.add(false);
					} else {
						throw new CommandException(CommandException.Exceptions.wrongArg);
					}
				} else if (command.getArgumentType()[i].equals(Command.ArgumentType.String)) {
					try {
						Integer.parseInt(formatedArgs.get(i));
						throw new CommandException(CommandException.Exceptions.wrongArg);
					} catch (NumberFormatException e1) {
						try {
							Float.parseFloat(formatedArgs.get(i));
							throw new CommandException(CommandException.Exceptions.wrongArg);
						} catch (NumberFormatException e2) {
							try {
								Double.parseDouble(formatedArgs.get(i));
								throw new CommandException(CommandException.Exceptions.wrongArg);
							} catch (NumberFormatException e3) {
								if (!formatedArgs.get(i).equals("true") && !formatedArgs.get(i).equals("false")) {
									stringArgs.add(formatedArgs.get(i));
								} else {
									throw new CommandException(CommandException.Exceptions.wrongArg);
								}
							}
						}
					}
				} else {
					throw new CommandException(CommandException.Exceptions.nil);
				}
			}
		} else if (command.getAmountOfArgs() != 0 && !command.isArgsOptional) {
			throw new CommandException(CommandException.Exceptions.noArgs);
		}
		
		if (!(command instanceof CommandHelp)) {
			addLine(cmd);
		}
		command.doCommand(intArgs, floatArgs, doubleArgs, boolArgs, stringArgs);
		clearInput();
	}
	
	public int getMaxLines() {
		return MAX_LINES;
	}
}
