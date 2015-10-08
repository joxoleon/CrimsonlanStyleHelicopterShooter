package scripts;

import engine.GameTime;
import engine.component.ActorComponent;


public abstract class ScriptComponent
extends ActorComponent
{
	public boolean isSecondPass = false;
	
	
	public abstract void onAttach();
	
	public abstract void update(GameTime gameTime);

	public abstract void onDestroy();
}
