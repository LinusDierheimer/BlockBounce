package httf.blockbounce.resources;

import java.io.InputStream;
import java.net.URL;

import javafx.scene.image.Image;

public class ResourceLoader {
	private ResourceLoader() {}
	
	public static InputStream loadAsStream(String name) {
		return ResourceLoader.class.getResourceAsStream(name);
	}
	
	public static Image loadAsImage(String name) {
		return new Image(loadAsStream(name));
	}
	
	public static URL loadAsURL(String name) {
		return ResourceLoader.class.getResource(name);
	}
}
