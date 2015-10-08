package scripts;

import engine.GameTime;
import engine.component.ActorComponent;
import engine.component.GunComponent;
import engine.component.PhysicsComponent;
import engine.component.PhysicsComponent.Direction;
import engine.input.Input;
import engine.input.Keys;
import engine.utility.Vector2;

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
		
//		Vector2 forwardVector = parentPhysicsComponent.getForwardVector();
////		System.out.println(forwardVector);
//		System.out.println(Vector2.fromV1toV2AngleRequiresNormalizedArguments(forwardVector, new Vector2(0, -1)));
//		
	}

	@Override
	public void onDestroy()
	{
		
	}

	@Override
	public ActorComponent clone()
	{
		return new PlayerControlScript();
	}
}
