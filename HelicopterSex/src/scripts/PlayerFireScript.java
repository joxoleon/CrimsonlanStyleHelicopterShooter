package scripts;

import input.Input;
import input.Keys;
import component.ActorComponent;
import component.GunComponent;
import engine.GameTime;

public class PlayerFireScript
extends ScriptComponent
{
	GunComponent parentGunComponent;

	public PlayerFireScript()
	{
		isSecondPass = true;
	}
	
	@Override
	public void onAttach()
	{
		parentGunComponent = (GunComponent) parent.getBasicComponent("gunComponent");
		if(parentGunComponent == null)
		{
			System.err.println("shit");
		}
	}

	@Override
	public void update(GameTime gameTime)
	{
		if(Input.isKeyDown(Keys.Space))
		{
			parentGunComponent.fire();
		}
		
	}

	@Override
	public void onDestroy()
	{		
	}

	@Override
	public String getName()
	{
		return "playerFireScript";
	}

	@Override
	public ActorComponent clone()
	{
		return new PlayerFireScript();
	}

}
