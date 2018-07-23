package httf.blockbounce;

import java.io.IOException;
import java.io.UncheckedIOException;

import httf.blockbounce.resources.ResourceLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

public abstract class GameState implements Runnable{
	
	protected final Main main;
	
	public GameState(Main main) {
		this.main = main;
	}

	//Utility methods
		
	protected Scale addScale(Pane root, double width, double height) {
		Scale scale = new Scale();
		root.getTransforms().add(scale);
 		main.getStage().widthProperty().addListener((observable, oldValue, newValue) -> {
 			scale.setX(newValue.doubleValue() / width);
 		});
 		main.getStage().heightProperty().addListener((observable, oldValue, newValue) -> {
 			scale.setY(newValue.doubleValue() / height);
 		});
 		return scale;
	}
	
	protected Scene createScene(Parent root) {
		Scene actual = main.getStage().getScene();
		return new Scene(root, actual.getWidth(), actual.getHeight());
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
		main.getStage().setScene(createScene(root));
	}
	
}
