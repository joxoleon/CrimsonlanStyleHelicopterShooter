package shooting;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.ListIterator;

import engine.GameTime;
import engine.camera.Camera;

public class ShotManager
{
	// ******************** Fields ******************** 
	// Shot lists.
	public static LinkedList<SimpleShot> playerShots = new LinkedList<SimpleShot>(); 
	
	
	
	
	

	// ******************** Methods ******************** 
	public static void update(GameTime gameTime)
	{
//		System.out.println(playerShots.size());
		for (SimpleShot playerShot : playerShots)
		{
			playerShot.update(gameTime);
		}
	}
	
	public static void draw(Graphics2D g2d, Camera camera)
	{
		for (SimpleShot playerShot : playerShots)
		{
			playerShot.draw(g2d, camera);
		}
	}
	
	public static void filterShots()
	{
		ListIterator<SimpleShot> listIterator = playerShots.listIterator();
		while(listIterator.hasNext())
		{
			SimpleShot shot = listIterator.next();
			if(shot.isActive == false)
			{
				listIterator.remove();
			}
		}
		
	}	
	
}
