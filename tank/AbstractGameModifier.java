package tank;

import java.util.Observable;

/*Abstract Game Modifiers are things that make changes to the game world:
 * weapons
 * ship movements
 * player input
 * level events
 * */
public abstract class AbstractGameModifier extends Observable{
	
	public AbstractGameModifier(){}
	
	/* read is used to send messages from game observables to game observers */
	public abstract void read(Object theObject);
}
