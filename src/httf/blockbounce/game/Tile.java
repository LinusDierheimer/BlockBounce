package httf.blockbounce.game;

import java.util.Arrays;
import java.util.List;

import httf.blockbounce.resources.ResourceLoader;

import javafx.scene.image.Image;

abstract class Tile extends Image{

	public static final List<Tile> TILES = Arrays.asList(
			
			new Tile("tile1.png") {
				@Override
				public int getHeight(int x) {
					return 80;
				}
			},
			
			new Tile("tile2.png") {
				@Override
				public int getHeight(int x){
					return 80;
				}
			}
	);
	
	public Tile(String resource) {
		super(ResourceLoader.loadAsStream(resource));
	}
	
	public TileView createView() {
		return new TileView(this);
	}
	
	public abstract int getHeight(int x);
}
