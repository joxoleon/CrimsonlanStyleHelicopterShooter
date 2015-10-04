package component;

import engine.Actor;
import engine.GameTime;

public abstract class ActorComponent
{
	// ******************** Fields ******************** 
	public Actor parent;
	
	
	
	
	// ******************** Constructors ******************** 
	public ActorComponent()
	{
	}
		
		
		
	// ******************** Methods ******************** 
	public abstract String getName();
	public abstract void onAttach();
	
	public void addParent(Actor parent)
	{
		this.parent = parent;
		onAttach();
	}
	
	public abstract void update(GameTime gameTime);
	
	public abstract void onDestroy();
	
	
}
