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

	private final double score;
	
	public EndScreen(Main main, double score) {
		super(main);
		this.score = score;
	}
	
	@Override
	public void run() {
		FXMLLoader loader = new FXMLLoader(ResourceLoader.loadAsURL("endscreen.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		EndScreenController controller = loader.getController();
		controller.main = main;
		controller.scoreLabel.setText("" +Math.round(score));
		
		Pane root = loader.getRoot();
		addScale(root, root.getPrefWidth(), root.getPrefHeight());
		
		main.getStage().setScene(new Scene(root));
	}

}
