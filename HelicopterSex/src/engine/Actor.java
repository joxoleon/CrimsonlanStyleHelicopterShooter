package engine;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import managers.GraphicsManager;
import engine.camera.Camera;
import engine.component.ActorComponent;
import engine.component.GraphicsComponent;
import engine.component.SphereCollider;
import engine.events.EventArgumentContainer;
import engine.events.EventHandler;
import engine.utility.Vector2;
import scripts.ScriptComponent;

public class Actor
{
	// ******************** Fields ******************** 
	
	// Actor information fields.
	public String name;
	public boolean isActive = true;
	public float inactiveFrameCounter = 0;
	
	// Transform fields.
	public Vector2 position = new Vector2();
	public float rotation = 0;
	public Vector2 scale = new Vector2(1, 1);
	
	// Components.
	protected LinkedList<ActorComponent> basicComponents = new LinkedList<ActorComponent>();
	protected Map<String, ActorComponent> basicComponentsMap = new HashMap<String, ActorComponent>();
	
	// Script Components.
	private LinkedList<ScriptComponent> scriptComponents = new LinkedList<ScriptComponent>();
	private LinkedList<ScriptComponent> scriptComponentsSecondPass = new LinkedList<ScriptComponent>();
	private Map<String, ScriptComponent> scriptComponentsMap = new HashMap<String, ScriptComponent>();
	
	// Event handlers.
	private Map<String, EventHandler> eventHandlers = new HashMap<String, EventHandler>();
	
	// Graphics Component.
	public GraphicsComponent graphicsComponent;
	public SphereCollider collider;
	
	// ******************** Constructors ******************** 
		
		
		
		
	// ******************** Methods ******************** 
	public void update(GameTime gameTime)
	{
		if(isActive == true)
		{
			for (ScriptComponent scriptComponent : scriptComponents)
			{
				scriptComponent.update(gameTime);
			}
			
			for (ActorComponent basicComponent : basicComponents)
			{
				basicComponent.update(gameTime);
			}
			
			
			for (ActorComponent script : scriptComponentsSecondPass)
			{
				script.update(gameTime);
			}
		}
	}
	
	public void draw(Graphics2D g2d, Camera camera)
	{
		GraphicsManager.saveGraphicsContext(g2d);
		GraphicsManager.lastActorRotation = rotation;
		
		Vector2 renderPosition = new Vector2(
				position.x - (camera.position.x - camera.rotatedCornerVector.x),
				position.y - (camera.position.y - camera.rotatedCornerVector.y)						
						);
		
		g2d.translate(renderPosition.x, renderPosition.y);
		g2d.rotate(rotation);
		g2d.scale(scale.x, scale.y);
		
		if(graphicsComponent != null)
			graphicsComponent.draw(g2d);
		
		GraphicsManager.restoreGraphicsContext(g2d);	
	}
	
	public void destroy()
	{
		for (ScriptComponent scriptComponent : scriptComponents)
		{
			scriptComponent.onDestroy();
		}
		for (ActorComponent actorComponent : basicComponents)
		{
			actorComponent.onDestroy();
		}
		for (ScriptComponent scriptComponent : scriptComponentsSecondPass)
		{
			scriptComponent.onDestroy();
		}
		
	}
	
	public void addScriptComponent(ScriptComponent scriptComponent)
	{
		scriptComponent.addParent(this);
		if(scriptComponent.isSecondPass == false)
		{
			scriptComponents.add(scriptComponent);
		}
		else
		{
			scriptComponentsSecondPass.add(scriptComponent);
		}
		scriptComponentsMap.put(scriptComponent.getName(), scriptComponent);
	}

	public ScriptComponent getScriptComponent(String scriptComponentName)
	{
		return scriptComponentsMap.get(scriptComponentName);
	}
	
	public void addBasicComponent(ActorComponent component)
	{
		component.addParent(this);
		basicComponents.add(component);
		basicComponentsMap.put(component.getName(), component);
	}
	
	public ActorComponent getBasicComponent(String basicComponentName)
	{
		return basicComponentsMap.get(basicComponentName);
	}
	
	public void addEventHandler(EventHandler handler)
	{
		String name = handler.getName();
		eventHandlers.put(name, handler);
		handler.onAttach(this);
	}
	
	public void handleEvent(EventArgumentContainer argumentContainer)
	{
		String name = argumentContainer.getEventName();
		EventHandler handler = eventHandlers.get(name);
		if(handler == null)
		{
			System.err.println("There is no handler with such name: " + name);
			Game.game.exitGame();
		}
		
		handler.handleEvent(argumentContainer);
	}
	
	public Vector2 getForwardVector()
	{
		Vector2 forward = new Vector2(0, -1);
		forward.rotate(rotation);
		return forward;
	}
	
	public Actor clone()
	{
		// Clone attributes.
		Actor clone = new Actor();
		clone.position = position.clone();
		clone.rotation = rotation;
		clone.scale = scale.clone();
		
		// Clone graphics component.
		clone.graphicsComponent = graphicsComponent.clone();
		
		// Clone basic components.
		for (ActorComponent basicComponent : basicComponents)
		{
			clone.addBasicComponent(basicComponent.clone());
		}
		
		for (ScriptComponent script : scriptComponents)
		{
			clone.addScriptComponent((ScriptComponent) script.clone());
		}
		
		for (ScriptComponent script : scriptComponentsSecondPass)
		{
			clone.addScriptComponent((ScriptComponent)(script));
		}
		
		// Clone event handlers.
		for (Map.Entry<String, EventHandler> eventHandler : eventHandlers.entrySet())
		{
			EventHandler handler = eventHandler.getValue();
			clone.addEventHandler(handler.clone());
		}
		
		return clone;
	}

}
