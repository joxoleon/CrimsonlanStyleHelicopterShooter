package events;

import engine.events.EventArgumentContainer;

public class EmptyEventContainer
implements EventArgumentContainer
{
	public String eventName;

	@Override
	public String getEventName()
	{
		return eventName;
	}

}
