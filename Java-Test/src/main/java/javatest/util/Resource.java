package main.java.javatest.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.javatest.blocks.util.BlockProperties;

public class Resource {

	private static final String baseLocation = "C:\\Github\\JavaTest\\Java-Test\\src\\main\\resources\\javatest\\assets\\textures\\";
	private static final String fileType = ".png";
	
	public static BufferedImage getTexture(EnumResourceType location, BlockProperties.EnumBlockType textureName) {
		File f = new File(baseLocation + location.toString().toLowerCase() + "\\" + textureName.toString().toLowerCase() + fileType);
		BufferedImage i = null;
		try {
			i = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public enum EnumResourceType {
		blocks(0);
		
		public final int fId;
		
		private EnumResourceType(int id) {
			fId = id;
		}

		public static EnumResourceType getNumber(int id) {
			for (EnumResourceType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
