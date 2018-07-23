package httf.blockbounce;

import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

public abstract class GameState implements Runnable{
	
	protected final Main main;
	
	public GameState(Main main) {
		this.main = main;
	}

	//Utility methods
		
	protected final Scale addScale(Pane root, double width, double height) {
		Scale scale = new Scale();
		root.getTransforms().add(scale);
 		main.getStage().widthProperty().addListener((observable, oldValue, newValue) -> {
 			scale.setX(newValue.doubleValue() / width);
 		});
 		main.getStage().heightProperty().addListener((observable, oldValue, newValue) -> {
 			scale.setY(newValue.doubleValue() / height);
 		});
 		return scale;
	}
	
}
