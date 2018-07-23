package httf.blockbounce.mainmenu;

import java.io.IOException;
import java.io.UncheckedIOException;

import httf.blockbounce.GameState;
import httf.blockbounce.Main;
import httf.blockbounce.resources.ResourceLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class MainMenu extends GameState{

	public MainMenu(Main main) {
		super(main);
	}
	
	@Override
	public void run() {
		FXMLLoader loader = new FXMLLoader(ResourceLoader.loadAsURL("mainmenu.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		MainMenuController controller = loader.getController();
		controller.main = main;
				
		Pane root = loader.getRoot();
		addScale(root, root.getPrefWidth(), root.getPrefHeight());
		
		main.getStage().setScene(new Scene(root));
	}

}
