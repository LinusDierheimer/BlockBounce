package httf.blockbounce.endscreen;

import java.net.URL;
import java.util.ResourceBundle;

import httf.blockbounce.Main;
import httf.blockbounce.game.Game;
import httf.blockbounce.mainmenu.MainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class EndScreenController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="scoreLabel"
    private Text scoreLabel; // Value injected by FXMLLoader

    @FXML // fx:id="mainMenuButton"
    private Button mainMenuButton; // Value injected by FXMLLoader

    @FXML // fx:id="retryButton"
    private Button retryButton; // Value injected by FXMLLoader

    Main main;
    
    @FXML
    void retryButtonPressed(ActionEvent event) {
    	main.setGameState(new Game(main));
    }

    @FXML
    void mainMenuButtonPressed(ActionEvent event) {
    	main.setGameState(new MainMenu(main));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert scoreLabel != null : "fx:id=\"scoreLabel\" was not injected: check your FXML file 'endscreen.fxml'.";
        assert mainMenuButton != null : "fx:id=\"mainMenuButton\" was not injected: check your FXML file 'endscreen.fxml'.";
        assert retryButton != null : "fx:id=\"retryButton\" was not injected: check your FXML file 'endscreen.fxml'.";

    }
}
