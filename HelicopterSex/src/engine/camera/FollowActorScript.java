package engine.camera;

import java.awt.Graphics2D;

import engine.Actor;
import engine.GameTime;
import engine.input.Input;
import engine.input.Keys;
import engine.utility.Vector2;

public class FollowActorScript
implements ICameraScript
{
	private Camera camera;
	private Actor actor;
	public boolean isFollowRotation = false;

	@Override
	public void onAttach(Camera camera)
	{
		this.camera = camera;
	}
	
	public void addActor(Actor actor)
	{
		this.actor = actor;
	}

	@Override
	public void update(GameTime gameTime)
	{
		if(Input.isKeyPressed(Keys.V))
		{
			isFollowRotation = !isFollowRotation;
		}
		
		if(actor != null)
		{
			camera.position = new Vector2();
			camera.position.x = actor.position.x;
			camera.position.y = actor.position.y;
			
			if(isFollowRotation == true)
			{
				camera.rotation = actor.rotation;
			}
			camera.scale = actor.scale.clone();
		}
	}

	@Override
	public void draw(Graphics2D g2d)
	{
	}

	@Override
	public String getName()
	{
		return "followActorScript";
	}

}
