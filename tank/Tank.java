package tank;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Observable;

import wingman.GameWorld;
import wingman.game.BigExplosion;
import wingman.game.PlayerShip;
import wingman.game.SmallExplosion;
import wingman.modifiers.AbstractGameModifier;
import wingman.modifiers.motions.InputController;
import wingman.modifiers.weapons.SimpleWeapon;

public class Tank extends PlayerShip {
	int direction;
	
    public Tank(Point location, Image img, int[] controls, String name) {
        super(location, new Point(0,0), img, controls, name);
        resetPoint = new Point(location);
        this.gunLocation = new Point(32,32);
        
        this.name = name;
        weapon = new TankWeapon();
        motion = new InputController(this, controls, TankWorld.getInstance());
        lives = 2;
        health = 100;
        strength = 100;
        score = 0;
        respawnCounter=0;
        height=64;
        width=64;
        direction=180;
        this.location = new Rectangle(location.x,location.y,width,height);
    }
    //You need to fill in here     
    public void turn(int angle){
    	this.direction += angle;
    	if(this.direction>=360){
    		this.direction=0;
    	}
    	else if(this.direction<0){
    		this.direction=359;
    	}
    }
    //You need to fill in here     
    public void update(int w, int h) {
    	if(isFiring){
    		int frame = TankWorld.getInstance().getFrameNumber();
    		if(frame>=lastFired+weapon.reload){
    			fire();
    			lastFired= frame;
    		}
    	}
    	
    	if(right==1 || left==1){
    		this.turn(3*(left-right));
    	}
    	if(down==1 || up==1){
        	int dy = (int)(5*(double)Math.cos(Math.toRadians(direction+90)));
        	int dx = (int)(5*(double)Math.sin(Math.toRadians(this.direction+90)));
    		location.x+=dx*(up-down);
    		location.y+=dy*(up-down);
        }
    	
    	if(location.y<0) location.y=0;
    	if(location.y>h-this.height) location.y=h-this.height;
    	if(location.x<0) location.x=0;
    	if(location.x>w-this.width) location.x=w-this.width;
    }
    
    public void draw(Graphics g, ImageObserver obs) {
    	if(respawnCounter<=0)
    		g.drawImage(img,  //the image
    				location.x,location.y,  //destination top left
    				location.x+this.getSizeX(), location.y+this.getSizeY(),  //destination lower right
    				(direction/6)*this.getSizeX(), 0,  //source top left
    				((direction/6)*this.getSizeX())+this.getSizeX(),this.getSizeY(),  //source lower right
    				obs);
    	else if(respawnCounter==80){
    		TankWorld.getInstance().addClockObserver(this.motion);
    		respawnCounter -=1;
        	System.out.println(Integer.toString(respawnCounter));
    	}
    	else if(respawnCounter<80){
    		if(respawnCounter%2==0)
        		g.drawImage(img,  //the image
        				location.x,location.y,  //destination top left
        				location.x+this.getSizeX(), location.y+this.getSizeY(),  //destination lower right
        				(direction/6)*this.getSizeX(), 0,  //source top left
        				((direction/6)*this.getSizeX())+this.getSizeX(),this.getSizeY(),  //source lower right
        				obs);
    		respawnCounter -= 1;
    	}
    	else
    		respawnCounter -= 1;
    }
    
    public void die(){
    	this.show=false;
    	GameWorld.setSpeed(new Point(0,0));
    	BigExplosion explosion = new BigExplosion(new Point(location.x,location.y));
    	TankWorld.getInstance().addBackground(explosion);
    	lives-=1;
    	if(lives>=0){
        	TankWorld.getInstance().removeClockObserver(this.motion);
    		reset();
    	}
    	else{
    		this.motion.delete(this);
    	}
    }
    
    public void reset(){
    	this.setLocation(resetPoint);
    	health=strength;
    	respawnCounter=160;
    	this.weapon = new TankWeapon();
    }

}