package events;

import shooting.SimpleShot;
import engine.events.EventArgumentContainer;
import engine.utility.Vector2;

public class OnHitArgumentContaner
implements EventArgumentContainer
{
	public SimpleShot shot;
	public Vector2 position;
	
	public OnHitArgumentContaner(SimpleShot shot, Vector2 position)
	{
		this.shot = shot;
		this.position = position;
	}
	
	@Override
	public String getEventName()
	{
		return "onHit";
	}
}
