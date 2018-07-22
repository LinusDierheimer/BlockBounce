 package httf.blockbounce.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import httf.blockbounce.GameState;
import httf.blockbounce.Main;
import httf.blockbounce.endscreen.EndScreen;
import httf.blockbounce.resources.ResourceLoader;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Game extends GameState{
	
	private static final Image BACKGROUND_IMAGE = ResourceLoader.loadAsImage("background.png");
	
	private static final Image PLAYER_IMAGE = ResourceLoader.loadAsImage("run.gif");
	private static final Image PLAYERJUMP_IMAGE = ResourceLoader.loadAsImage("jump.png");
	private static final Image PLAYERLANDING_IMAGE = ResourceLoader.loadAsImage("landing.png");
	
	private static final double WIDTH = BACKGROUND_IMAGE.getWidth();  //750
	private static final double HEIGHT = BACKGROUND_IMAGE.getHeight(); //422

	private static final Random RANDOM = new Random();
	private static double randDouble(double min, double max) {
		return RANDOM.nextDouble() * (max - min) + min;
	}
		
	private static final double MIN_TILE_DISTANCE = 50;
	private static final double MAX_TILE_DISTANCE = 150;	
	private static double generateDistance() {
		return randDouble(MIN_TILE_DISTANCE, MAX_TILE_DISTANCE);
	}
	
	private static final double COLLISION_ACCEPTANCE = 5;
	
	private static final double MIN_TILE_HEIGHT = 100;
	private static final double MAX_TILE_HEIGHT = 200;
	private static double generateTileHeight() {
		return randDouble(MIN_TILE_HEIGHT, MAX_TILE_HEIGHT);
	}
	
	private static final int TILE_SPEED = 20;
	private static final double START_TILE_Y = 200;
	private static final double START_TILE_X = 0;
	private static final double PLAYER_X = 50;

	private static final double GRAVITY_FORCE = 20;
	
	private static final double SCORE_INCREASOR = 0.3;
	
	private ImageView backgroundView = new ImageView(BACKGROUND_IMAGE);
	private ImageView playerView = new ImageView(PLAYERLANDING_IMAGE);
	{
		playerView.setLayoutX(PLAYER_X); 
	}
				
	private double playerY = 50;
	private double jumpTime = 0;
	private double score = 0;
	
	private AnchorPane root = new AnchorPane(backgroundView, playerView);
	private Scene scene = new Scene(root);
	
	private List<TileView> tiles = new ArrayList<>();
	{
		TileView view = Tile.START_TILE.createView();
		view.setLayoutY(START_TILE_Y);
		view.setLayoutX(START_TILE_X);
		tiles.add(view);
		root.getChildren().add(view);
	}
	
/*	
 *	private Scale scale = new Scale();
 *	{
 *		root.getTransforms().add(scale);
 *		main.getStage().widthProperty().addListener((observable, oldValue, newValue) -> {
 *			scale.setX(newValue.doubleValue() / WIDTH);
 *		});
 *		main.getStage().heightProperty().addListener((observable, oldValue, newValue) -> {
 *			scale.setY(newValue.doubleValue() / HEIGHT);
 *		});
 *	}
*/
	
	private Label scoreLabel = new Label("Score: 0");
	{
		scoreLabel.setLayoutX(30);
		scoreLabel.setLayoutY(20);
		//scoreLabel.setMinWidth(root.getWidth()- 30);
		//scoreLabel.setTextAlignment(TextAlignment.RIGHT);
		scoreLabel.setTextFill(Color.ANTIQUEWHITE);
		scoreLabel.setFont(new Font("Impact", 25));
		root.getChildren().add(scoreLabel);
	}
	
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
	
	private boolean upPressed = false;
	{
		scene.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.SPACE)
				upPressed = true;
		});
	}
	
	public Game(Main main) {
		super(main);
		//main.getStage().setMaximized(true);
		main.getStage().setMaxWidth(WIDTH);
		main.getStage().setMaxHeight(HEIGHT);
		main.getStage().setFullScreenExitHint("");
		main.getStage().setResizable(false);
		main.getStage().setMaximized(false);
	}
		
	@Override
	public Scene getScene() {
		return scene;
	}
	
	public double getHeight(double screenX) {
		
		for(TileView tile : tiles) {
			
			if(screenX < tile.getLayoutX())
				return -1;
			
			if(screenX > tile.getLayoutX() + tile.getTile().getWidth())
				continue;
			else {
				return tile.getTopHeight(screenX - tile.getLayoutX());
			}
			
		}
		
		return -1;
	}
	
	private void addTile(Tile tile) {
		TileView view = tile.createView();
		view.setLayoutY(generateTileHeight() + view.getTile().getHeight());
		view.setLayoutX(WIDTH);
		tiles.add(view);
		root.getChildren().add(view);
	}
	
	private void addTile() {
		Tile randomTile = Tile.TILES.get(RANDOM.nextInt(Tile.TILES.size()));
		addTile(randomTile);
	}
	
	private void removeTile(TileView view) {
		tiles.remove(view);
		root.getChildren().remove(view);
	}
	
	private double nextDistance = generateDistance();
	private void updateTiles(double dt) {
		
		if(tiles.isEmpty())
			addTile(); //should never happen
		else {
			TileView lastTile = tiles.get(tiles.size() - 1);
			if(lastTile.rightX() < main.getStage().getWidth() - (nextDistance)) {
				addTile();
				nextDistance = generateDistance();
			}
		
			TileView firstTile = tiles.get(0);
			if(firstTile.rightX() < 0)
				removeTile(firstTile);
		}
		
	}
	
	private void updatePlayer(double dt) {
		double floorY = getHeight(PLAYER_X);
		System.out.println(floorY);
		
		if(floorY == 0) {
			return;
		}
		if(playerY - floorY <= COLLISION_ACCEPTANCE) {
			playerY = floorY - playerView.getImage().getHeight();
			playerView.setImage(PLAYER_IMAGE);
		}
		if(playerY > main.getStage().getHeight()) {
			stop();
		}
		playerY += GRAVITY_FORCE * dt;
		if(floorY > - 1) {
			if(playerY >= floorY) 
			{
				
				playerView.setImage(PLAYER_IMAGE);
			}
			
			if(upPressed && playerY == floorY) {
				jumpTime = 25;
				upPressed = false;
				playerView.setImage(PLAYERJUMP_IMAGE);
			}
			
			if(jumpTime > 0) {
				playerY -=6.5;
				jumpTime --;
				if(jumpTime == 0) {
					playerView.setImage(PLAYERLANDING_IMAGE);
				}
			}
		}
		
	}
	
	private void updateScore(double dt) {
		score += SCORE_INCREASOR * dt;
	}
	
	private void update(double dt) {
		updateTiles(dt);
		updatePlayer(dt);
		updateScore(dt);
	}
	
	private void renderTiles(double dt) {
		tiles.forEach(e -> e.moveLeft(TILE_SPEED * dt));
	}
	
	private void renderPlayer(double dt) {
		playerView.setLayoutY(playerY  - playerView.getImage().getHeight());
	}
	
	private void renderScore(double dt) {
		
		long roundedScore = Math.round(score);
		scoreLabel.setText("Score: " + roundedScore);
		
	}
	
	private void render(double dt) {
		renderTiles(dt);
		renderPlayer(dt);
		renderScore(dt);
	}
	
	public void stop() {
		timer.stop();
		main.setGameState(new EndScreen(main));
	}
	
	@Override
	public void run() {
		timer.start();
	}

}
