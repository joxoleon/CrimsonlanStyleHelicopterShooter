package events;

import engine.events.EventArgumentContainer;
import engine.utility.Vector2;

public class OnHitArgumentContaner
implements EventArgumentContainer
{
	public float damage;
	public Vector2 position;
	
	public OnHitArgumentContaner(float damage, Vector2 position)
	{
		this.damage = damage;
		this.position = position;
	}
	
	@Override
	public String getEventName()
	{
		return "onHit";
	}
}
