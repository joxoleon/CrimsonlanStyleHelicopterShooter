package scripts;

import component.ActorComponent;

import engine.GameTime;

public abstract class ScriptComponent
extends ActorComponent
{
	public abstract void onAttach();
	
	public abstract void update(GameTime gameTime);

	public abstract void onDestroy();
}
