package shooting;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;







import content.Content;
import engine.Game;
import engine.GameTime;

public class ShotManager
{
	// ******************** Fields ******************** 
	// Shot lists.
	public static LinkedList<SimpleShot> playerShots = new LinkedList<SimpleShot>(); 
	
	
	
	
	

	
	
	
	// ******************** Methods ******************** 
	public static void update(GameTime gameTime)
	{
		for (SimpleShot playerShot : playerShots)
		{
			playerShot.update(gameTime);
		}
	}
	
	public static void draw(Graphics2D g2d)
	{
		for (SimpleShot playerShot : playerShots)
		{
			playerShot.draw(g2d);
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
