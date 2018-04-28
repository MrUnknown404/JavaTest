package main.java.javatest.util;

public enum EnumRenderKey {
	block (0),
	entity(1);
	
	public final int fId;
	
	private EnumRenderKey(int id) {
		fId = id;
	}

	public static EnumRenderKey getNumber(int id) {
		for (EnumRenderKey type : values()) {
			if (type.fId == id) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid Type id: " + id);
	}
}
