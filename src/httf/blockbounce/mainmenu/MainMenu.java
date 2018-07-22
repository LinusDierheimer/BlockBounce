package httf.blockbounce.mainmenu;

import java.io.IOException;
import java.io.UncheckedIOException;

import httf.blockbounce.GameState;
import httf.blockbounce.Main;
import httf.blockbounce.resources.ResourceLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class MainMenu extends GameState{

	public MainMenu(Main main) {
		super(main);
	}

	@Override
	public Scene getScene() {
		FXMLLoader loader = new FXMLLoader(ResourceLoader.loadAsURL("mainmenu.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		MainMenuController controller = loader.getController();
		controller.main = main;
		
		BorderPane root = new BorderPane(loader.getRoot());		
		return new Scene(root);
	}

	@Override
	public void run() {}

}
