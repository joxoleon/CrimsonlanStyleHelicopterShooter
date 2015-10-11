package scripts;

import engine.GameTime;
import engine.component.ActorComponent;
import events.EmptyEventContainer;

public class HealthScript
extends ScriptComponent
{
	private float health = 1000;
	private float maxHealth = 1000;

	public HealthScript(float maxHealth)
	{
		this.maxHealth = maxHealth;
		health = maxHealth;
	}
	
	@Override
	public void onAttach()
	{
		
	}

	@Override
	public void update(GameTime gameTime)
	{
		if(health <= 0)
		{
			EmptyEventContainer container = new EmptyEventContainer();
			container.eventName = "onDestroy";
			parent.handleEvent(container);
		}
	}

	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName()
	{
		return "healthScript";
	}

	@Override
	public ActorComponent clone()
	{
		return new HealthScript(maxHealth);
	}
	
	public void inflictDamage(float damage)
	{
		health -= damage;
		if(health <= 0)
		{
			EmptyEventContainer container = new EmptyEventContainer();
			container.eventName = "onDestroy";
			parent.handleEvent(container);
		}
		System.out.println(health);
	}

}
