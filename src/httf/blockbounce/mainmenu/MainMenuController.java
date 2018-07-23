package httf.blockbounce.mainmenu;

import java.net.URL;
import java.util.ResourceBundle;

import httf.blockbounce.Main;
import httf.blockbounce.game.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class MainMenuController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="mainPane"
    private BorderPane mainPane; // Value injected by FXMLLoader

    @FXML // fx:id="both"
    private Label both; // Value injected by FXMLLoader

    @FXML // fx:id="mainmenue"
    private Button mainmenue; // Value injected by FXMLLoader

    @FXML // fx:id="gameImage"
    private ImageView gameImage; // Value injected by FXMLLoader

    Main main;
    
    @FXML
    void startgame(ActionEvent event) {
		new Game(main).run();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'Untitled'.";
        assert both != null : "fx:id=\"both\" was not injected: check your FXML file 'Untitled'.";
        assert mainmenue != null : "fx:id=\"mainmenue\" was not injected: check your FXML file 'Untitled'.";
        assert gameImage != null : "fx:id=\"gameImage\" was not injected: check your FXML file 'Untitled'.";

    }
    
    
}
