package httf.blockbounce;

import httf.blockbounce.mainmenu.MainMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static final String WINDOW_TITLE = "Block Bouncer";
	
	private Stage stage;
	
	public Stage getStage() {
		return stage;
	}
	
	public Scene getSceen() {
		return stage.getScene();
	}
	
	@Override
	public void start(Stage stage) throws Exception{
		this.stage = stage;
		this.stage.setTitle(WINDOW_TITLE);
		this.stage.setFullScreen(true);
	
		Scene scene = new Scene(new BorderPane(), Paint.valueOf("black"));
		this.stage.setScene(scene);
		
		new MainMenu(this).run();
		this.stage.show();
	}
	
	public static void main(String... args) {
		launch(args);
	}
}
