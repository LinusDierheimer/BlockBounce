package httf.blockbounce.mainmenu;

import httf.blockbounce.GameState;
import httf.blockbounce.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class MainMenu extends GameState{

	public MainMenu(Main main) {
		super(main);
	}
	
	@Override
	public void run() {
		FXMLLoader loader = getLoader("mainmenu.fxml");
		MainMenuController controller = loader.getController();
		controller.main = main;
				
		Pane root = loader.getRoot();
		addScale(root, root.getPrefWidth(), root.getPrefHeight());
		
		setScene(root);
	}

}
