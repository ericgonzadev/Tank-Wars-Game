package tank;

import java.awt.Point;

import wingman.game.BackgroundObject;

public class Wall extends BackgroundObject {
	public Wall(int x, int y){
		super(new Point(x*32, y*32), new Point(0,0), TankWorld.sprites.get("wall"));
	}
	
	public void damage(int damage){
		return;
	}
}
