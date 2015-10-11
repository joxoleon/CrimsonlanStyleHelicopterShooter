package events;

import engine.effects.EffectManager;
import engine.events.EventArgumentContainer;
import engine.events.EventHandler;
import engine.utility.Vector2;

public class OnDestroy
extends EventHandler
{
	private String effectName = "explosion01";
	private float maxOffset = 20;
	private float minDelay = 0.1f;
	private float delayRange = 0.4f;
	private float probabilityOfSecondExplosion = 0.5f;

	@Override
	public void onAttach()
	{		
	}

	@Override
	public String getName()
	{
		return "onDestroy";
	}

	@Override
	public void handleEvent(EventArgumentContainer container)
	{
		EffectManager.playEffect(effectName, parentActor.position);
		if(Math.random() > probabilityOfSecondExplosion)
		{
			Vector2 offset = new Vector2((float)Math.random() * maxOffset - maxOffset, (float)Math.random() * maxOffset - maxOffset);
			Vector2 position = offset.add(parentActor.position);
			
			float delay = (float) (minDelay + Math.random() * delayRange);
			
			
			EffectManager.queueEffect(effectName, position, delay);
		}
		parentActor.destroy();
		
	}

	@Override
	public EventHandler clone()
	{
		OnDestroy clone = new OnDestroy();
		clone.effectName = effectName;
		return clone;
	}

}
