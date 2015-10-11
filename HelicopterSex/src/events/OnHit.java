package events;

import engine.effects.EffectManager;
import engine.events.EventArgumentContainer;
import engine.events.EventHandler;

public class OnHit
extends EventHandler
{
	@Override
	public String getName()
	{
		return "onHit";
	}

	@Override
	public void handleEvent(EventArgumentContainer argumentContainer)
	{
		OnHitArgumentContaner container = (OnHitArgumentContaner) argumentContainer;
		EffectManager.playEffect("bulletHit01", container.position);
		
	}

	@Override
	public EventHandler clone()
	{
		return new OnHit();
	}

}
