package httf.blockbounce.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import httf.blockbounce.GameState;
import httf.blockbounce.Main;
import httf.blockbounce.resources.ResourceLoader;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Game extends GameState{
	
	private static final Image BACKGROUND_IMAGE = ResourceLoader.loadAsImage("background.png");
	private static final Image PLAYER_IMAGE = ResourceLoader.loadAsImage("run.gif");
	private static final double width = BACKGROUND_IMAGE.getWidth();  //750
	private static final double height = BACKGROUND_IMAGE.getHeight(); //422
	
	private ImageView backgroundView = new ImageView(BACKGROUND_IMAGE);
	private ImageView playerView = new ImageView(PLAYER_IMAGE);
	
	
	private static final Random random = new Random();
	private static final double randDouble(double min, double max) {
		return random.nextDouble() * (max - min) + min;
	}
	
	private List<TileView> tiles = new ArrayList<>();
	
	private static final double MIN_TILE_DISTANCE = 50;
	private static final double MAX_TILE_DISTANCE = 150;	
	private static double generateDistance() {
		return randDouble(MIN_TILE_DISTANCE, MAX_TILE_DISTANCE);
	}
	
	private static final double MIN_TILE_HEIGHT = 300;
	private static final double MAX_TILE_HEIGHT = 400;
	private static double generateTileHeight() {
		return randDouble(MIN_TILE_HEIGHT, MAX_TILE_HEIGHT);
	}
	
	private static final int TILE_SPEED = 20;
	
	private double playerY = 50;
	
	
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

		root.getChildren().add(playerView);
		playerView.setLayoutX(200);
	}
	
	
	@Override
	public Scene getScene() {
		return scene;
	}
	
	private void addTile(Tile tile) {
		TileView view = tile.createView();
		view.setLayoutY(generateTileHeight());
		view.setLayoutX(width);
		tiles.add(view);
		root.getChildren().add(view);
	}
	
	private void addTile() {
		Tile randomTile = Tile.TILES.get(random.nextInt(Tile.TILES.size()));
		addTile(randomTile);
	}
	
	private void addStartTile(){
		TileView view = Tile.START_TILE.createView();
		view.setY(200);
		tiles.add(view);
		root.getChildren().add(view);
	}
	
	private void removeTile(TileView view) {
		tiles.remove(view);
		root.getChildren().remove(view);
	}
	
	private double nextDistance = generateDistance();
	private void updateTiles(double dt) {
		
		if(tiles.isEmpty())
			addStartTile();
		else {
			TileView lastTile = tiles.get(tiles.size() - 1);
			if(lastTile.rightX() < main.getStage().getWidth() - nextDistance) {
				addTile();
				nextDistance = generateDistance();
			}
		
			TileView firstTile = tiles.get(0);
			if(firstTile.rightX() < 0)
				removeTile(firstTile);
		}
		
	}
	
	private void renderTiles(double dt) {
		tiles.forEach(e -> e.moveLeft(TILE_SPEED * dt));
	}
	
	/**
	 * 
	 * Returns the height of the tile at the given screen position.
	 * If there is no tile, a negative number will be returned.
	 * 
	 * @param screenX
	 * @return
	 */
	public double getHeight(double screenX) {
		return -1;
	}
	
	private void update(double dt) {
		updateTiles(dt);
		updatePlayer(dt);
	}
	
	private void updatePlayer(double dt) {
		
			
		playerY += 12 * dt;
		
		if(playerY >= 167) 
		{
			playerY = 167;
		}
	}
	
	private void render(double dt) {
		renderTiles(dt);
		renderPlayer(dt);
	}
	
	private void renderPlayer(double dt) {
		playerView.setLayoutY(playerY);
		
	}
	
	@Override
	public void run() {
		timer.start();
	}
}
