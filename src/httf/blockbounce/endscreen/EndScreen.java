package httf.blockbounce.endscreen;

import java.io.IOException;
import java.io.UncheckedIOException;

import httf.blockbounce.GameState;
import httf.blockbounce.Main;
import httf.blockbounce.resources.ResourceLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

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
		
		Pane root = loader.getRoot();
		
		main.getStage().setTitle("Block Bouncer");
		main.getStage().setWidth(655);
		main.getStage().setHeight(515);
		main.getStage().setResizable(false);
		
		return new Scene(root);
	}
	
	@Override
	public void run() {}

}
