package httf.blockbounce.game;

import java.util.ArrayList;
import java.util.List;

import httf.blockbounce.GameState;
import httf.blockbounce.Main;
import httf.blockbounce.resources.ResourceLoader;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Game extends GameState{
	
	private static final Image BACKGROUND_IMAGE = ResourceLoader.loadAsImage("background.jpg");
	private static final List<Image> TILES = new ArrayList<>(1);
	static {
		//TILES.add(ResourceLoader.loadAsImage("tile1.jpg"));
	}
	
	private ImageView backgroundView = new ImageView(BACKGROUND_IMAGE);
	
	private AnimationTimer timer = new AnimationTimer() {
		
		long old = System.nanoTime();
		
		@Override
		public void handle(long now) {
			
			double dt = (now - old) / 100000000.0;
			old = now;
			
			update(dt);
			render(dt);
		}
	};
	
	private List<ImageView> shownTiles = new ArrayList<>();
		
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
	
	private void update(double dt) {
		
	}
	
	private void render(double dt) {
		shownTiles.forEach(e -> {
			e.setLayoutX(e.getLayoutX() - 2 * dt);
		});
	}
	
	@Override
	public void run() {
		timer.start();
	}
}
