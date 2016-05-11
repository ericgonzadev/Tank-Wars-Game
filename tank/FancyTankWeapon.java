package tank;

import java.awt.Point;

import wingman.game.Bullet;
import wingman.game.Ship;
import wingman.modifiers.weapons.AbstractWeapon;

public class FancyTankWeapon extends AbstractWeapon {
	public FancyTankWeapon(){
		super(TankWorld.getInstance());
	}
	
	public void fireWeapon(Ship theTank) {
		super.fireWeapon(theTank);
		Point location = theTank.getLocationPoint();
		Point offset = theTank.getGunLocation();
		location.x+=offset.x;
		location.y+=offset.y;
		Point speed = new Point(0,-15*direction);
		int strength = 7;
		reload = 15;
		
		bullets = new Bullet[2];
		bullets[0] = new TankBullet(location, speed, strength, -5, (Tank) theTank);
		bullets[1] = new TankBullet(location, speed, strength, 5, (Tank) theTank);
		
				
		this.setChanged();
		
		this.notifyObservers();
	}
}
