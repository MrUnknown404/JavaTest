package main.java.javatest.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.init.Blocks;

public class Resource {

	private static final String BASE_LOCATION = "/main/resources/javatest/assets/textures/";
	private static final String FILE_TYPE = ".png";
	
	public static BufferedImage getTexture(ResourceType location, Blocks.EnumBlocks textureName) {
		InputStream f = Resource.class.getResourceAsStream(BASE_LOCATION + location.toString().toLowerCase() + "/" + textureName.toString().toLowerCase() + FILE_TYPE);
		BufferedImage i = null;
		try {
			i = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public static BufferedImage getTexture(ResourceType location, EntityProperties.EntityType textureName) {
		InputStream f = Resource.class.getResourceAsStream(BASE_LOCATION + location.toString().toLowerCase() + "/" + textureName.toString().toLowerCase() + FILE_TYPE);
		BufferedImage i = null;
		try {
			i = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public static BufferedImage getTexture(ResourceType location, String textureName) {
		InputStream f = Resource.class.getResourceAsStream(BASE_LOCATION + location.toString().toLowerCase() + "/" + textureName + FILE_TYPE);
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
		entity(1),
		items (2),
		gui   (3);
		
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
