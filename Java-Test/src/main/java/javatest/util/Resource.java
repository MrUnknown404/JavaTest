package main.java.javatest.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.entity.util.EntityProperties;

public class Resource {

	private static final String BASE_LOCATION = "C:\\Github\\JavaTest\\Java-Test\\src\\main\\resources\\javatest\\assets\\textures\\";
	private static final String FILE_TYPE = ".png";
	
	public static BufferedImage getTexture(ResourceType location, BlockProperties.BlockType textureName) {
		File f = new File(BASE_LOCATION + location.toString().toLowerCase() + "\\" + textureName.toString().toLowerCase() + FILE_TYPE);
		BufferedImage i = null;
		try {
			i = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public static BufferedImage getTexture(ResourceType location, EntityProperties.EntityType textureName) {
		File f = new File(BASE_LOCATION + location.toString().toLowerCase() + "\\" + textureName.toString().toLowerCase() + FILE_TYPE);
		BufferedImage i = null;
		try {
			i = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public enum ResourceType {
		blocks(0),
		entity(1);
		
		private final int fId;
		
		private ResourceType(int id) {
			fId = id;
		}

		public static ResourceType getNumber(int id) {
			for (ResourceType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
