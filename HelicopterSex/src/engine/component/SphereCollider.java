package engine.component;


import java.awt.Color;
import java.awt.Graphics2D;

import managers.GraphicsManager;
import engine.GameTime;
import engine.camera.Camera;
import engine.utility.Vector2;

public class SphereCollider
extends ActorComponent
{
	// ******************** Fields ********************
	public float radius;
	public PhysicsComponent parentPhysicsComponent;
	
	public Color colliderColor = Color.green;
	public boolean isDrawCollider = true;
	
	// ******************** Constructors ********************
	
	
	
		
	// ******************** Methods ********************
	@Override
	public String getName()
	{
		return "sphereColliderComponent";
	}
	
	@Override
	public void onAttach()
	{
		parentPhysicsComponent = (PhysicsComponent) parent.getBasicComponent("physicsComponent");
		parent.collider = this;
		Vector2 dimensions = parent.graphicsComponent.getDimensions();
		radius = (dimensions.x + dimensions.y) / 4;
	}
	
	@Override
	public void update(GameTime gameTime)
	{
	}
	
	public void draw(Graphics2D g2d, Camera camera)
	{
		GraphicsManager.saveFullGraphicsContext(g2d);
		Vector2 renderPosition = new Vector2(
				parent.position.x - (camera.position.x - camera.rotatedCornerVector.x),
				parent.position.y - (camera.position.y - camera.rotatedCornerVector.y)						
						);
		
		g2d.translate(renderPosition.x, renderPosition.y);

		
		g2d.setColor(colliderColor);
		g2d.drawOval((int)-radius, (int)-radius, (int)(2 * radius), (int)(2 * radius));
		
		GraphicsManager.restoreFullGraphicsContext(g2d);
	}
	
	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public ActorComponent clone()
	{
		return new SphereCollider();
	}
	

}
