package httf.blockbounce.endscreen;

import java.io.IOException;
import java.io.UncheckedIOException;

import httf.blockbounce.GameState;
import httf.blockbounce.Main;
import httf.blockbounce.resources.ResourceLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

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
		return new Scene(loader.getRoot());
	}
	
	@Override
	public void run() {}

}
