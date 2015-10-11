package events;

import scripts.HealthScript;
import engine.component.PhysicsComponent;
import engine.effects.EffectManager;
import engine.events.EventArgumentContainer;
import engine.events.EventHandler;
import engine.utility.Vector2;

public class OnHit
extends EventHandler
{
	private PhysicsComponent parentPhysicsComponent;
	
	@Override
	public void onAttach()
	{
		parentPhysicsComponent = (PhysicsComponent) parentActor.getBasicComponent("physicsComponent");
	}
	
	@Override
	public String getName()
	{
		return "onHit";
	}

	@Override
	public void handleEvent(EventArgumentContainer argumentContainer)
	{
		OnHitArgumentContaner container = (OnHitArgumentContaner) argumentContainer;
		EffectManager.playEffect(container.shot.effectName, container.position);

		HealthScript health = (HealthScript) parentActor.getScriptComponent("healthScript");
		health.inflictDamage(container.shot.damage);
		
		Vector2 throwbackVector = container.shot.getThrowbackVector();
		parentPhysicsComponent.velocity.add(throwbackVector.div(parentPhysicsComponent.mass));
		
	}

	@Override
	public EventHandler clone()
	{
		return new OnHit();
	}

	

}
