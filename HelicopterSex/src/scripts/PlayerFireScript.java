package scripts;

import input.Input;
import input.Keys;
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

}
