package httf.blockbounce.endscreen;

import httf.blockbounce.GameState;
import httf.blockbounce.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class EndScreen extends GameState{

	private final double score;
	
	public EndScreen(Main main, double score) {
		super(main);
		this.score = score;
	}
	
	@Override
	public void run() {
		FXMLLoader loader = getLoader("endscreen.fxml");
		EndScreenController controller = loader.getController();
		controller.main = main;
		controller.scoreLabel.setText("" +Math.round(score));
		
		Pane root = loader.getRoot();
		addScale(root, root.getPrefWidth(), root.getPrefHeight());
		
		main.getSceen().setRoot(root);
	}

}
