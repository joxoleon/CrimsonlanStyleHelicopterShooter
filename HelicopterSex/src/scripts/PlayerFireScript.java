package scripts;

import engine.GameTime;
import engine.component.ActorComponent;
import engine.component.GunComponent;
import engine.input.Input;
import engine.input.Keys;

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
