package engine;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import managers.GraphicsManager;
import camera.Camera;
import component.ActorComponent;
import component.GraphicsComponent;
import scripts.ScriptComponent;
import utility.Vector2;

public class Actor
{
	// ******************** Fields ******************** 
	
	// Actor information fields.
	public String name;
	
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
	
	// Graphics Component.
	public GraphicsComponent graphicsComponent;
	
	
	// ******************** Constructors ******************** 
		
		
		
		
	// ******************** Methods ******************** 
	public void update(GameTime gameTime)
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
		
		return clone;
	}

}
