 package httf.blockbounce.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import httf.blockbounce.GameState;
import httf.blockbounce.Main;
import httf.blockbounce.endscreen.EndScreen;
import httf.blockbounce.resources.ResourceLoader;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;

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
		
	private static final double MIN_TILE_DISTANCE = 20;
	private static final double MAX_TILE_DISTANCE = 100;	
	private static double generateDistance() {
		return randDouble(MIN_TILE_DISTANCE, MAX_TILE_DISTANCE);
	}
	
	private static final double COLLISION_ACCEPTANCE = 10;
	
	private static final double MIN_TILE_HEIGHT = 100;
	private static final double MAX_TILE_HEIGHT = 150;
	private static double generateTileHeight() {
		return randDouble(MIN_TILE_HEIGHT, MAX_TILE_HEIGHT);
	}
	
	private static final int TILE_SPEED = 20;
	
	private static final double START_TILE_Y = 200;
	private static final double START_TILE_X = 0;
	
	private static final double LEFT_PLAYER_X = 100;
	private static final double RIGHT_PLAYER_X = LEFT_PLAYER_X + PLAYER_IMAGE.getWidth();
	private static final double START_PLAYER_Y = 50;
	
	private static final double GRAVITY_FORCE = 20;
	private static final double MAX_JUMP_TIME = 40;
	private static final double JUMP_FORCE = 6.5;
	
	private static final double SCORE_INCREASOR = 0.2;
	
	private ImageView backgroundView = new ImageView(BACKGROUND_IMAGE);
	private ImageView playerView = new ImageView(PLAYERLANDING_IMAGE);
	{
		playerView.setLayoutX(LEFT_PLAYER_X); 
	}
				
	private double playerY = START_PLAYER_Y;
	private double jumpTime = 0;
	private double score = 0;
	
	private AnchorPane root = new AnchorPane(backgroundView, playerView);
	
	private List<TileView> tiles = new ArrayList<>();
	{
		TileView view = Tile.START_TILE.createView();
		view.setLayoutY(START_TILE_Y);
		view.setLayoutX(START_TILE_X);
		tiles.add(view);
		root.getChildren().add(view);
	}
	
 	@SuppressWarnings("unused")
	private Scale scale = addScale(root, WIDTH, HEIGHT);
 	
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
	
	private volatile boolean upPressed = false; //volatile, because of use by JavaFX Thread and AnimationTimer Thread
	{
		main.getSceen().setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.SPACE)
				upPressed = true;
			
		});
		main.getSceen().setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.SPACE)
				upPressed = false;
		});
	}
	
	public Game(Main main) {
		super(main);
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
			if(lastTile.rightX() < WIDTH - (nextDistance)) {
				addTile();
				nextDistance = generateDistance();
			}
		
			TileView firstTile = tiles.get(0);
			if(firstTile.rightX() < 0)
				removeTile(firstTile);
		}
		
	}
	
	private void updatePlayer(double dt) {
		double floorY = Math.max(getHeight(LEFT_PLAYER_X), getHeight(RIGHT_PLAYER_X));
		
		playerY += GRAVITY_FORCE * dt;
		
		//Stop game
		if(playerY > HEIGHT) {
			stop();
		}
		
		//Acceptance for player
		final double minPlayerHeight = playerY + COLLISION_ACCEPTANCE;
		final double maxPlayerHeight = playerY - COLLISION_ACCEPTANCE;
		
		final boolean playerIsOnTile = floorY <= minPlayerHeight && floorY >=maxPlayerHeight;
		
		if(jumpTime > 0) {
			playerY -= JUMP_FORCE;
			jumpTime --;
			
			//Stop jumping
			if(jumpTime == 0 || !upPressed) {
				playerView.setImage(PLAYERLANDING_IMAGE);
				jumpTime = 0; // for !upPressed
			}
			
		}else if(playerIsOnTile) {
			
			//walking
			playerY = floorY;			
				
			//start jumping
			if(upPressed) {
				jumpTime = MAX_JUMP_TIME;
				playerView.setImage(PLAYERJUMP_IMAGE);
			}else
				playerView.setImage(PLAYER_IMAGE);
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
		new EndScreen(main, score).run();
	}
	
	@Override
	public void run() {
		main.getSceen().setRoot(root);
		timer.start();
	}

}
