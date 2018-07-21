package httf.blockbounce.game;

import java.util.ArrayList;
import java.util.List;

import httf.blockbounce.GameState;
import httf.blockbounce.Main;
import httf.blockbounce.resources.ResourceLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Game extends GameState{
	
	private static final Image BACKGROUND_IMAGE = ResourceLoader.loadAsImage("background.jpg");
	private static final List<Image> TILES = new ArrayList<>(1);
	static {
		TILES.add(ResourceLoader.loadAsImage("tile1.jpg"));
	}
	
	private ImageView backgroundView = new ImageView(BACKGROUND_IMAGE);
	
	private AnchorPane root = new AnchorPane(backgroundView);
	private Scene scene = new Scene(root);
	
	public Game(Main main) {
		super(main);
		main.getStage().setMaximized(true);
	}
	
	@Override
	public Scene getScene() {
		return scene;
	}
	
	@Override
	public void run() {
		
	}
}
