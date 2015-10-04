package engine;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import camera.Camera;
import component.ActorComponent;
import component.GraphicsComponent;
import scripts.ScriptComponent;
import utility.Vector2;

public class Actor
{
	// ******************** Fields ******************** 
	
	// Transform fields.
	public Vector2 position = new Vector2();
	public float rotation = 0;
	public Vector2 scale = new Vector2(1, 1);
	
	// Components.
	protected LinkedList<ActorComponent> basicComponents = new LinkedList<ActorComponent>();
	protected Map<String, ActorComponent> basicComponentsMap = new HashMap<String, ActorComponent>();
	
	// Script Components.
	private LinkedList<ScriptComponent> scriptComponents = new LinkedList<ScriptComponent>();
	private Map<String, ScriptComponent> scriptComponentsMap = new HashMap<String, ScriptComponent>();
	
	// Graphics Component.
	public GraphicsComponent graphicsComponent;
	
	// Backup transform.
	AffineTransform backupTransform;
	
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
	}
	
	public void draw(Graphics2D g2d, Camera camera)
	{
		saveGraphicsContext(g2d);
		
		Vector2 renderPosition = new Vector2(
				position.x - (camera.position.x - camera.rotatedCornerVector.x),
				position.y - (camera.position.y - camera.rotatedCornerVector.y)						
						);
		
		g2d.translate(renderPosition.x, renderPosition.y);
		g2d.rotate(rotation);
		g2d.scale(scale.x, scale.y);
		
		if(graphicsComponent != null)
			graphicsComponent.draw(g2d);
		
		restoreGraphicsContext(g2d);		
	}
	
	public void addScriptComponent(ScriptComponent scriptComponent)
	{
		scriptComponent.addParent(this);
		scriptComponents.add(scriptComponent);
		scriptComponentsMap.put(scriptComponent.getName(), scriptComponent);
		scriptComponent.onAttach();
	}
	public void addScriptComponentFirst(ScriptComponent scriptComponent)
	{
		scriptComponent.addParent(this);
		scriptComponents.add(0, scriptComponent);
		scriptComponentsMap.put(scriptComponent.getName(), scriptComponent);
		scriptComponent.onAttach();
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
	private void saveGraphicsContext(Graphics2D g2d)
	{
		backupTransform = g2d.getTransform();
	}
	private void restoreGraphicsContext(Graphics2D g2d)
	{
		g2d.setTransform(backupTransform);
	}

}
