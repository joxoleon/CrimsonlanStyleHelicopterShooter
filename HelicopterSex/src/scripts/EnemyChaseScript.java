package scripts;

import managers.PlayerActorManager;
import engine.GameTime;
import engine.component.ActorComponent;
import engine.component.GunComponent;
import engine.component.PhysicsComponent;
import engine.component.PhysicsComponent.Direction;
import engine.utility.Vector2;

public class EnemyChaseScript
extends ScriptComponent
{
	PhysicsComponent parentPhysicsComponent;
	GunComponent parentGunComponent;
	
	private float magnitudeSquaredThreshold = 1000;

	@Override
	public void onAttach()
	{
		parentPhysicsComponent = (PhysicsComponent) parent.getBasicComponent("physicsComponent");
		parentGunComponent = (GunComponent) parent.getBasicComponent("gunComponent");
	}

	@Override
	public void update(GameTime gameTime)
	{
		// Get actor to player vector.
		Vector2 playerPosition = PlayerActorManager.getPlayerPosition();		
		Vector2 actorToPlayerVector = Vector2.sub(playerPosition, parent.position);
				
		if(actorToPlayerVector.magnitudeSquared() > magnitudeSquaredThreshold)
		{
			parentPhysicsComponent.accelerate(Direction.FORWARD);
		}
		
		actorToPlayerVector.normalize();
		
		
		// Get actor forward vector.
		Vector2 forwardVector = parentPhysicsComponent.getForwardVector();
		
		// Get rotation angle
		float rotationAngle = Vector2.fromV1toV2AngleRequiresNormalizedArguments(forwardVector, actorToPlayerVector);
		
		parentPhysicsComponent.rotate(rotationAngle);
		
	}

	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName()
	{
		return "enemyChaseScript";
	}

	@Override
	public ActorComponent clone()
	{
		return new EnemyChaseScript();
	}

}
