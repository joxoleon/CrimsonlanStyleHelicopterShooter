package scripts;

import component.GunComponent;
import component.PhysicsComponent;
import component.PhysicsComponent.Direction;
import input.Input;
import input.Keys;
import engine.GameTime;

public class PlayerControlScript
extends ScriptComponent
{
	PhysicsComponent parentPhysicsComponent;
	GunComponent parentGunComponent;

	public boolean hasRotation = false;

	@Override
	public void onAttach()
	{
		// Get physics component
		parentPhysicsComponent = (PhysicsComponent) parent.getBasicComponent("physicsComponent");
		parentGunComponent = (GunComponent) parent.getBasicComponent("gunComponent");
		// Get chopper acceleration and angular acceleration.
	}
	
	@Override
	public String getName()
	{
		return "playerControllerScript";
	}
	
	@Override
	public void update(GameTime gameTime)
	{
		
		
		if(Input.isKeyDown(Keys.Up))
		{
			parentPhysicsComponent.accelerate(PhysicsComponent.Direction.FORWARD);
		}
		
		if(Input.isKeyDown(Keys.Down))
		{
			parentPhysicsComponent.accelerate(PhysicsComponent.Direction.BACKWARD);

		}
		
		if(Input.isKeyDown(Keys.Left))
		{
			if(hasRotation == true)
			{
				parentPhysicsComponent.rotate(PhysicsComponent.Direction.RIGHT);
			}
			else
			{
				parentPhysicsComponent.accelerate(Direction.LEFT);
			}
		}
		
		if(Input.isKeyDown(Keys.Right))
		{
			if(hasRotation == true)
			{
				parentPhysicsComponent.rotate(PhysicsComponent.Direction.LEFT);
			}
			else
			{
				parentPhysicsComponent.accelerate(Direction.RIGHT);
			}
		}
		
		if(Input.isKeyDown(Keys.Space))
		{
			parentGunComponent.fire();
		}
		
		if(Input.isKeyPressed(Keys.R))
		{
			hasRotation = !hasRotation;
			if(hasRotation == false)
			{
				parent.rotation = 0;
				parentPhysicsComponent.angularAcceleration = 0;
 				parentPhysicsComponent.angularVelocity = 0;
			}
		}
		
	}

	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		
	}
}
