package managers;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import utility.StringWriter;
import engine.Game;
import engine.GameTime;


public class PauseManager
{
	public static boolean isPaused = false;
	public static BufferedImage pauseImage;
	private static Color imageColor = new Color(0, 0, 0, 0.4f);
	private static String pauseTextFontName = "Quartz MS";
	private static int pauseTextFontSize = 90;
	private static Color pauseTextColor = Color.yellow;

	public static void initialize()
	{
		pauseImage = new BufferedImage(Game.game.worldDimension.width, Game.game.worldDimension.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) pauseImage.getGraphics();
		g2d.setPaint(imageColor);
		g2d.fillRect(0, 0, pauseImage.getWidth(), pauseImage.getHeight());
		
		String text = "pause";
		Point origin = new Point((int) (Game.game.worldDimension.getWidth() / 2), (int) (Game.game.worldDimension.getHeight() / 2));
		
		StringWriter.writeString(g2d, text, origin, true, pauseTextFontName, pauseTextFontSize, pauseTextColor);
	}
	
	public static void update(GameTime gameTime)
	{
		
	}
	
	public static void draw(Graphics2D g2d)
	{
		if(isPaused == true)
		{
			GraphicsManager.saveGraphicsContext(g2d);
			
		g2d.drawImage(pauseImage,
				0, 0, Game.game.worldDimension.width, Game.game.worldDimension.height,
				0, 0, pauseImage.getWidth(), pauseImage.getHeight(),
				null);
		
			GraphicsManager.restoreGraphicsContext(g2d);
		}
	}
	
	public static void togglePause(LinkedList<Long> songIDs)
	{
		if(isPaused == false)
		{
			isPaused = true;
			for (Long songID : songIDs)
			{
				AudioManager.pause(songID);
			}
		}
		else
		{
			isPaused = false;
			for (Long songID : songIDs)
			{
				AudioManager.play(songID);
			}
		}
	}
	
}
