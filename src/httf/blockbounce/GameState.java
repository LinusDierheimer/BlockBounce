package httf.blockbounce;

import javafx.scene.Scene;

public abstract class GameState implements Runnable{
	
	protected final Main main;
	
	public abstract Scene getScene();

	public GameState(Main main) {
		this.main = main;
	}


}
