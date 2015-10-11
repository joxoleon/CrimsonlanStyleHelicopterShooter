package factories;

import java.util.HashMap;
import java.util.Map;

import engine.Game;
import engine.events.EventHandler;
import events.OnHit;

public class EventHandlerFactory
{
	private static Map<String, EventHandler> eventHandlers = new HashMap<String, EventHandler>();
	
	
	public static void initialize()
	{
		// On hit event.
		OnHit onHit = new OnHit();
		eventHandlers.put("onHit", onHit);
	}
	
	public static EventHandler getEventHandler(String eventHandlerName)
	{
		EventHandler handler = eventHandlers.get(eventHandlerName);
		if(handler == null)
		{
			System.err.println("There is no event handler with such name: " + eventHandlerName);
			Game.game.exitGame();
		}
		
		return handler.clone();
	}
}
