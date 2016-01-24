package scripts;

import java.awt.Color;

import engine.Game;
import engine.GameTime;
import engine.component.ActorComponent;
import engine.component.GunComponent;
import engine.component.PhysicsComponent;
import engine.component.PhysicsComponent.Direction;
import engine.input.Input;
import engine.input.Keys;
import engine.utility.DebugRenderer;
import engine.utility.Vector2;

public class PlayerControlScriptMouse
extends ScriptComponent
{
	PhysicsComponent parentPhysicsComponent;
	GunComponent parentGunComponent;
	
	public boolean isAbsoluteControls = true;


	@Override
	public void onAttach()
	{
		// Get physics component
		parentPhysicsComponent = (PhysicsComponent) parent.getBasicComponent("physicsComponent");
		parentGunComponent = (GunComponent) parent.getBasicComponent("gunComponent");
		if(isAbsoluteControls == true)
		{
			parentPhysicsComponent.isAbsoluteAcceleration = true;
		}
	}
	
	@Override
	public String getName()
	{
		return "playerControlScriptMouse";
	}
	
	@Override
	public void update(GameTime gameTime)
	{
		// Handle rotation.
		Vector2 mouseWorldPosition = (Input.getMouseWorldPosition(Game.game.mainCamera));
		Vector2 actorToMouseVector = Vector2.sub(mouseWorldPosition, parent.position);
		
		System.out.println("Mouse = " + mouseWorldPosition + " Parent = " + parent.position);
		
//		DebugRenderer.addDebugVector(parent.position, Vector2.add(actorToMouseVector, parent.position), Color.green);
		
    	
		if(actorToMouseVector.x != 0 && actorToMouseVector.y != 0)
		{
			actorToMouseVector.normalize();
			Vector2 forwardVector = parentPhysicsComponent.getForwardVector();
			
			float rotationAngle = Vector2.fromV1toV2AngleRequiresNormalizedArguments(forwardVector, actorToMouseVector);
			
			parentPhysicsComponent.rotate(rotationAngle);
		}
		
		
		
		// Handle translation
		if(Input.isKeyDown(Keys.Up) || Input.isKeyDown(Keys.W))
		{
			parentPhysicsComponent.accelerate(PhysicsComponent.Direction.FORWARD);
		}
		
		if(Input.isKeyDown(Keys.Down) || Input.isKeyDown(Keys.S))
		{
			parentPhysicsComponent.accelerate(PhysicsComponent.Direction.BACKWARD);
		}
		
		if(Input.isKeyDown(Keys.A))
		{
			parentPhysicsComponent.accelerate(Direction.LEFT);
		}
		
		if(Input.isKeyDown(Keys.D))
		{
			parentPhysicsComponent.accelerate(Direction.RIGHT);
		}
		
		
	}

	@Override
	public void onDestroy()
	{
		
	}

	@Override
	public ActorComponent clone()
	{
		return new PlayerControlScriptMouse();
	}
}
