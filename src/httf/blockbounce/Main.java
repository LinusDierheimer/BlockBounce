package httf.blockbounce;

import httf.blockbounce.mainmenu.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static final String WINDOW_TITLE = "Block Bouncer";
	
	private Stage stage;
	
	public Stage getStage() {
		return stage;
	}
	
	@Override
	public void start(Stage stage) throws Exception{
		this.stage = stage;
		this.stage.setTitle(WINDOW_TITLE);
		this.stage.setMaximized(true);
		new MainMenu(this).run();
		this.stage.show();
	}
	
	public static void main(String... args) {
		launch(args);
	}
}
