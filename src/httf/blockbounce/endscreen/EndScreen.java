package httf.blockbounce.endscreen;

import java.io.IOException;
import java.io.UncheckedIOException;

import httf.blockbounce.GameState;
import httf.blockbounce.Main;
import httf.blockbounce.resources.ResourceLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.transform.Scale;

public class EndScreen extends GameState{

	public EndScreen(Main main) {
		super(main);
	}

	@Override
	public Scene getScene() {
		FXMLLoader loader = new FXMLLoader(ResourceLoader.loadAsURL("endscreen.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		EndScreenController controller = loader.getController();
		controller.main = main;
		main.getStage().setTitle("Block Bouncer");
		main.getStage().setMaximized(true);
		
		BorderPane root = new BorderPane(loader.getRoot());
		Scale scale = new Scale();
		root.getTransforms().add(scale);
		main.getStage().widthProperty().addListener((observable, oldValue, newValue) -> {
			scale.setX(newValue.doubleValue() / root.getWidth());
		});
		main.getStage().heightProperty().addListener((observable, oldValue, newValue) -> {
			scale.setY(newValue.doubleValue() / root.getHeight());
		});
		
		return new Scene(root);
	}
	
	@Override
	public void run() {}

}
