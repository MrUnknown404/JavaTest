package main.java.javatest.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Resource {

	private static final String BASE_LOCATION = "/main/resources/javatest/assets/textures/";
	private static final String FILE_TYPE = ".png";
	
	public static BufferedImage getTexture(ResourceType location, String textureName) {
		InputStream f = null;;
		if (Resource.class.getResourceAsStream(BASE_LOCATION + location.toString().toLowerCase() + "/" + textureName + FILE_TYPE) == null) {
			Console.print(Console.WarningType.Error, "Cannot find texture : " + BASE_LOCATION + location.toString().toLowerCase() + "/" + textureName + FILE_TYPE);
			f = Resource.class.getResourceAsStream(BASE_LOCATION + location.toString().toLowerCase() + "/missing" + FILE_TYPE);
		} else {
			f = Resource.class.getResourceAsStream(BASE_LOCATION + location.toString().toLowerCase() + "/" + textureName + FILE_TYPE);
		}
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
