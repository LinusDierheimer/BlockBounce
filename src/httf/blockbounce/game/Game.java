package httf.blockbounce.game;

import httf.blockbounce.GameState;
import httf.blockbounce.Main;
import httf.blockbounce.resources.ResourceLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Game extends GameState{
	
	private static final Image BACKGROUND_IMAGE = ResourceLoader.loadAsImage("backgroud.png");
	
	private ImageView backgroundView = new ImageView(BACKGROUND_IMAGE);
	
	private AnchorPane root = new AnchorPane(backgroundView);
	private Scene scene = new Scene(root);
	
	public Game(Main main) {
		super(main);
	}
	
	@Override
	public Scene getScene() {
		return scene;
	}
	
}
