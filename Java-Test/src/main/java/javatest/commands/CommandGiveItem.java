package main.java.javatest.commands;

import java.util.List;

import main.java.javatest.Main;
import main.java.javatest.init.Items;
import main.java.javatest.items.ItemStack;

public class CommandGiveItem extends Command {

	private static Command.ArgumentType[] types = {Command.ArgumentType.String, Command.ArgumentType.Integer};
	
	public CommandGiveItem() {
		super("giveitem", types, true);
	}
	
	@Override
	public String usage() {
		StringBuilder b = new StringBuilder();
		for (Command.ArgumentType type : types) {
			b.append("<" + type.toString() + "> ");
		}
		
		return "/giveitem : usage -> /giveitem " + b.toString() + " : Gives the specified item!";
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		if (Main.getWorldHandler().getPlayer() != null) {
			if (!argString.isEmpty()) {
				if (Items.findItem(argString.get(0)) != null) {
					if (argInt.isEmpty()) {
						Main.getWorldHandler().getPlayer().getInventory().addItem(new ItemStack(1, Items.findItem(argString.get(0))));
						Main.getCommandConsole().addLine("Given player -> " + argString.get(0) + " x 1");
					} else if (argInt.get(0) <= Items.findItem(argString.get(0)).getMaxStack()) {
						Main.getWorldHandler().getPlayer().getInventory().addItem(new ItemStack(argInt.get(0), Items.findItem(argString.get(0))));
						Main.getCommandConsole().addLine("Given player -> " + argString.get(0) + " x " + argInt.get(0));
					} else {
						Main.getCommandConsole().addLine("* Item count is higher then allowed!");
					}
				} else {
					Main.getCommandConsole().addLine("* Unknown item!");
				}
			} else {
				Main.getCommandConsole().addLine("* Item name needed!");
			}
		} else {
			Main.getCommandConsole().addLine("* Cannot find the player!");
		}
	}
}
