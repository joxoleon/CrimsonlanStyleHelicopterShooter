package camera;

import java.awt.Graphics2D;
import java.util.LinkedList;

import utility.Vector2;
import engine.Actor;
import engine.GameTime;

public class CrimsonlandCameraScript
implements ICameraScript
{
	private Camera camera;
	private Actor actor;
	public Vector2 maxCoordinates;
	public Vector2 minCoordinates;
	
	private LinkedList<Vector2> actorPositions = new LinkedList<Vector2>();
	public int followActorFramesDelay = 20;
	
	public CrimsonlandCameraScript(Vector2 minCoordinates, Vector2 maxCoordinates)
	{
		this.setBoundaries(minCoordinates, maxCoordinates);
	}
	
	@Override
	public void onAttach(Camera camera)
	{
		this.camera = camera;
		
		if(actor != null)
		{
			camera.position.x = actor.position.x;
			camera.position.y = actor.position.y;
		}
	}
	
	public void addActor(Actor actor)
	{
		this.actor = actor;
		
		if(camera != null)
		{
			camera.position.x = actor.position.x;
			camera.position.y = actor.position.y;
		}
	}
	
	@Override
	public void update(GameTime gameTime)
	{
		actorPositions.addLast(actor.position.clone());
		
		if(actorPositions.size() > followActorFramesDelay)
		{
			Vector2 position = actorPositions.removeFirst();
			camera.position.x = position.x;
			camera.position.y = position.y;
		}
		
		if(camera.position.x > maxCoordinates.x)
		{
			camera.position.x = maxCoordinates.x;
		}
		
		if(camera.position.x < minCoordinates.x)
		{
			camera.position.x = minCoordinates.x;
		}
		
		if(camera.position.y > maxCoordinates.y)
		{
			camera.position.y = maxCoordinates.y;
		}
		
		if(camera.position.y < minCoordinates.y)
		{
			camera.position.y = minCoordinates.y;
		}
		
	}
	@Override
	public void draw(Graphics2D g2d)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void setBoundaries(Vector2 minCoordinates, Vector2 maxCoordinates)
	{
		this.minCoordinates = minCoordinates;
		this.maxCoordinates = maxCoordinates;
	}
	
}
