package httf.blockbounce.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import httf.blockbounce.GameState;
import httf.blockbounce.Main;
import httf.blockbounce.resources.ResourceLoader;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.stage.Screen;

public class Game extends GameState{
	
	private static final Image BACKGROUND_IMAGE = ResourceLoader.loadAsImage("background.png");
	
	private static final double width = BACKGROUND_IMAGE.getWidth();  //750
	private static final double height = BACKGROUND_IMAGE.getHeight(); //422
	
	private ImageView backgroundView = new ImageView(BACKGROUND_IMAGE);
	
	private static final Random random = new Random();

	private List<TileView> tiles = new ArrayList<>();
	private static final double MIN_DISTANCE = 50;
		
	private static final int TILE_SPEED = 15;
	
	private AnimationTimer timer = new AnimationTimer() {
		
		long old = System.currentTimeMillis();
		
		@Override
		public void handle(long now) {
			
			double dt = (now - old) / 100000000.0;
			old = now;
			
			update(dt);
			render(dt);
			System.out.println(dt);
		}
	};

	private AnchorPane root = new AnchorPane(backgroundView);
	private Scene scene = new Scene(root);
	
	public Game(Main main) {
		super(main);
		//main.getStage().setMaximized(true);
		main.getStage().setMinWidth(width);
		main.getStage().setMinHeight(height);
		main.getStage().setFullScreenExitHint("");
		main.getStage().setResizable(false);
		
//		Scale scale = new Scale();
//		root.getTransforms().add(scale);
//		main.getStage().widthProperty().addListener((observable, oldValue, newValue) -> {
//			scale.setX(newValue.doubleValue() / width);
//		});
//		main.getStage().heightProperty().addListener((observable, oldValue, newValue) -> {
//			scale.setY(newValue.doubleValue() / height);
//		});
	}
	
	@Override
	public Scene getScene() {
		return scene;
	}
	
	private void addTile() {
		Tile randomTile = Tile.TILES.get(random.nextInt(Tile.TILES.size()));
		TileView view = randomTile.createView();
		view.setLayoutY(200);
		view.setLayoutX(width);
		tiles.add(view);
		root.getChildren().add(view);
	}
	
	private void removeTile(TileView view) {
		tiles.remove(view);
		root.getChildren().remove(view);
	}
	
	private void updateTiles(double dt) {
		
		if(tiles.isEmpty())
			addTile();
		else {
			TileView lastTile = tiles.get(tiles.size() - 1);
			if(lastTile.rightX() < main.getStage().getWidth() - MIN_DISTANCE)
				addTile();
		
			TileView firstTile = tiles.get(0);
			if(firstTile.rightX() < 0)
				removeTile(firstTile);
		}
		
	}
	
	private void renderTiles(double dt) {
		tiles.forEach(e -> e.moveLeft(TILE_SPEED * dt));
	}
	
	private void update(double dt) {
		updateTiles(dt);
	}
	
	private void render(double dt) {
		renderTiles(dt);
	}
	
	@Override
	public void run() {
		timer.start();
	}
}
