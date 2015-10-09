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
	public boolean isHandleCollision = true;
	private float collisionCooldownTime = 0.3f;
	private float collisionCounter = 0;
	
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
		if(isHandleCollision == false)
		{
			collisionCounter += gameTime.dt_s();
			if(collisionCounter > collisionCooldownTime)
			{
				collisionCounter = 0;
				isHandleCollision = true;
			}
		}
	}

	@Override
	public void onDestroy()
	{
		
	}
	
	@Override
	public ActorComponent clone()
	{
		return new SphereCollider();
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

	public boolean checkForCollision(SphereCollider collider)
	{
		if(collider.isHandleCollision == false)
		{
			return false;
		}
		
		
		float minDistance = this.radius + collider.radius;
		Vector2 distanceVector = Vector2.sub(this.parent.position, collider.parent.position);
		float distanceVectorMagnitude = distanceVector.magnitude();
		
		if(minDistance > distanceVectorMagnitude)
		{
			handleCollisionPhysics(collider, minDistance, distanceVector, distanceVectorMagnitude);
			
			return true;
		}
		return false;
	}
	
	
	// TODO: this is totaly horrible. Change it.
	private void handleCollisionPhysics(SphereCollider collider, float minDistance, Vector2 distanceVector, float distanceVectorMagnitude)
	{
		System.out.println("collision!!!");
		
		
		
		// Handle velocities.
		
		float thisMagnitudeSquared = this.parentPhysicsComponent.velocity.magnitudeSquared();
		float colliderMagnitudeSquared = collider.parentPhysicsComponent.velocity.magnitudeSquared();	
		
		float thisMassRatio = this.parentPhysicsComponent.mass / (this.parentPhysicsComponent.mass + collider.parentPhysicsComponent.mass);
		float colliderMassRatio = collider.parentPhysicsComponent.mass / (this.parentPhysicsComponent.mass + collider.parentPhysicsComponent.mass);
		
		if(thisMagnitudeSquared > colliderMagnitudeSquared)
		{
			collider.parentPhysicsComponent.velocity.add(Vector2.mul(this.parentPhysicsComponent.velocity, thisMassRatio));
			this.parentPhysicsComponent.velocity.mul(- colliderMassRatio);
		}
		else
		{
			this.parentPhysicsComponent.velocity.add(Vector2.mul(this.parentPhysicsComponent.velocity, colliderMassRatio));
			collider.parentPhysicsComponent.velocity.mul(- thisMassRatio);
		}
		
		
		// Hack position.
		distanceVector.normalize();
		distanceVector.mul(minDistance - distanceVectorMagnitude);
		this.parent.position.add(Vector2.mul(distanceVector, colliderMassRatio));
		collider.parent.position.add(Vector2.mul(distanceVector, thisMassRatio));
		
		collider.isHandleCollision = false;
		
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
}
