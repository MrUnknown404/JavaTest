package main.java.javatest.util;

public enum EnumWarningType {
	Debug     (0),
	Info      (1),
	Warning   (2),
	Error     (3),
	FatalError(4);
	
	public final int fId;

	private EnumWarningType(int id) {
		this.fId = id;
	}

	public static EnumWarningType getNumber(int id) {
		for (EnumWarningType type : values()) {
			if (type.fId == id) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid Type id: " + id);
	}
}
