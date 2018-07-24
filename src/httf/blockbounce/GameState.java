package httf.blockbounce;

import java.io.IOException;
import java.io.UncheckedIOException;

import httf.blockbounce.resources.ResourceLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

public abstract class GameState implements Runnable{
	
	protected final Main main;
	
	public GameState(Main main) {
		this.main = main;
	}

	//Utility methods
		
	protected Scale addScale(Pane root, double width, double height) {
		Scale scale = new Scale(
				main.getScene().getWidth() / width,
				main.getScene().getHeight() / height
		);
		root.getTransforms().add(scale);
 		main.getScene().widthProperty().addListener((observable, oldValue, newValue) -> {
 			scale.setX(newValue.doubleValue() / width);
 		});
 		main.getScene().heightProperty().addListener((observable, oldValue, newValue) -> {
 			scale.setY(newValue.doubleValue() / height);
 		});
 		return scale;
	}
	
	protected FXMLLoader getLoader(String resource) {
		FXMLLoader loader = new FXMLLoader(ResourceLoader.loadAsURL(resource));
		try {
			loader.load();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		return loader;
	}
	
	protected void setScene(Parent root) {
		main.getStage().getScene().setRoot(root);
	}
	
}
