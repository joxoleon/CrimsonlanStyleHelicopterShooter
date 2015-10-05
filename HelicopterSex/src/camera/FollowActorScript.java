package camera;

import input.Input;
import input.Keys;

import java.awt.Graphics2D;

import utility.Vector2;
import engine.Actor;
import engine.GameTime;

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
		// TODO : add camera follow delay.
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

}
