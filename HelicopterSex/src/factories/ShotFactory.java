package factories;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import content.Content;
import engine.Game;
import shooting.ShotSprite;

public class ShotFactory
{
	// Shot construction fields.
	public static Map<String, ShotSprite> shotSprites = new HashMap<String, ShotSprite>(); 
	
	@SuppressWarnings("resource")
	public static void loadShotSprites(String path)
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(path));

			String line = br.readLine();

			while (line != null)
			{
				String[] tokens = line.split(" ");
				if(tokens.length > 4)
				{
					System.err.println("Error: invalid input in shots file");
					throw new Exception();
				}
				
				BufferedImage shotImage = Content.images.get(tokens[0]);
				ShotSprite newShotSprite = new ShotSprite();
				
				// Initialize new Shot Sprite.
				newShotSprite.shotImage = shotImage;
				newShotSprite.name = tokens[1];
				newShotSprite.scale.x = Float.parseFloat(tokens[2]);
				newShotSprite.scale.y = Float.parseFloat(tokens[3]);
								
				shotSprites.put(newShotSprite.name, newShotSprite);
				
				line = br.readLine();
			}
			
			br.close();


		} catch (Exception e)
		{
			System.err.println("Could not read from file shots.txt");
			Game.game.exitGame();
		}
	}
	
	
}
