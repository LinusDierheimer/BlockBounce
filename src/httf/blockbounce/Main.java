package httf.blockbounce;
	
import httf.blockbounce.game.Game;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	private Stage stage;
	
	public void setGameState(GameState state) {
		this.stage.setScene(state.getScene());
	}
	
	@Override
	public void start(Stage stage) throws Exception{
		this.stage = stage;
		setGameState(new Game(this));
		this.stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
