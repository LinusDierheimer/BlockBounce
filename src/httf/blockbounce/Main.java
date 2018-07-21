package httf.blockbounce;
	
import httf.blockbounce.mainmenu.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	private Stage stage;
	
	public void setGameState(GameState state) {
		this.stage.hide();
		this.stage.setScene(state.getScene());
		this.stage.show();
		state.run();
	}
	
	public Stage getStage() {
		return stage;
	}
	
	@Override
	public void start(Stage stage) throws Exception{
		this.stage = stage;
		setGameState(new MainMenu(this));
		this.stage.show();
	}
	
	public static void main(String... args) {
		launch(args);
	}
}
