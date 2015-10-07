package scripts;

import component.ActorComponent;

import utility.Vector2;
import engine.GameTime;

public class KeepInAreaScript
extends ScriptComponent
{
	public Vector2 maxCoordinates;
	public Vector2 minCoordinates;

	public KeepInAreaScript(Vector2 minCoordinates, Vector2 maxCoordinates)
	{
		this.isSecondPass = true;
		this.setBoundaries(minCoordinates, maxCoordinates);
	}
	
	@Override
	public void onAttach()
	{
		if(parent.position.x > maxCoordinates.x)
		{
			parent.position.x = maxCoordinates.x;
		}
		
		if(parent.position.x < minCoordinates.x)
		{
			parent.position.x = minCoordinates.x;
		}
		
		if(parent.position.y > maxCoordinates.y)
		{
			parent.position.y = maxCoordinates.y;
		}
		
		if(parent.position.y < minCoordinates.y)
		{
			parent.position.y = minCoordinates.y;
		}
	}

	@Override
	public void update(GameTime gameTime)
	{
		if(parent.position.x > maxCoordinates.x)
		{
			parent.position.x = maxCoordinates.x;
		}
		
		if(parent.position.x < minCoordinates.x)
		{
			parent.position.x = minCoordinates.x;
		}
		
		if(parent.position.y > maxCoordinates.y)
		{
			parent.position.y = maxCoordinates.y;
		}
		
		if(parent.position.y < minCoordinates.y)
		{
			parent.position.y = minCoordinates.y;
		}
		
	}

	@Override
	public void onDestroy()
	{
		
	}

	@Override
	public String getName()
	{
		return "keepInAreaScript";
	}
	
	public void setBoundaries(Vector2 minCoordinates, Vector2 maxCoordinates)
	{
		this.minCoordinates = minCoordinates;
		this.maxCoordinates = maxCoordinates;
	}

	@Override
	public ActorComponent clone()
	{
		return new KeepInAreaScript(minCoordinates.clone(), maxCoordinates.clone());
	}


}
