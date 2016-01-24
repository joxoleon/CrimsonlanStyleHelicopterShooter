package content;

import engine.Game;
import engine.graphics.SpriteSheet;
import engine.utility.MyFileReader;
import engine.utility.StringWriter;
import factories.ModelFactory;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import terrain.TerrainScrollDown;
import terrain.TerrainAsset;

public class Content
{
	public static Map<String, BackgroundPanel> backgroundPanels = new HashMap<String, BackgroundPanel>();
	public static Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	public static Map<String, Font> fonts = new HashMap<String, Font>();
	
	public static Map<String, SpriteSheet> spriteSheets = new HashMap<String, SpriteSheet>();
	
	// Models.
	public static ModelFactory modelFactory = ModelFactory.getInstance();
	
	
	
	
	public static void initializeContent() throws IOException
	{
		loadImages();
		loadSpriteSheets();
		loadFonts();
		initializeBackgroundPanels();
	}

	private static void initializeBackgroundPanels()
	{
		BufferedImage newPanelImage;
		Graphics2D g2d;
		BufferedImage image;
		BackgroundPanel bp;

		// MainMenu background panel
		newPanelImage = new BufferedImage(Game.game.worldDimension.width,
				Game.game.worldDimension.height, BufferedImage.TYPE_INT_ARGB);

		g2d = (Graphics2D) newPanelImage.getGraphics();
		image = images.get("space01");
		if (image == null)
		{
			System.err.println("Error - there is no such image");
		}

		g2d.drawImage(image, 0, 0, newPanelImage.getWidth(),
				newPanelImage.getHeight(), 0, 0, image.getWidth(),
				image.getHeight(), null);

		bp = new BackgroundPanel();
		bp.backgroundImage = newPanelImage;

		backgroundPanels.put("mainMenu", bp);

		// Single player background panel
		newPanelImage = new BufferedImage(Game.game.worldDimension.width,
				Game.game.worldDimension.height, BufferedImage.TYPE_INT_ARGB);

		g2d = (Graphics2D) newPanelImage.getGraphics();
		image = images.get("space02");
		if (image == null)
		{
			System.err.println("Error - there is no such image");
		}

		g2d.drawImage(image, 0, 0, newPanelImage.getWidth(),
				newPanelImage.getHeight(), 0, 0, image.getWidth(),
				image.getHeight(), null);

		bp = new BackgroundPanel();
		bp.backgroundImage = newPanelImage;

		backgroundPanels.put("playSingleGame", bp);

		// Game Over panel
		newPanelImage = new BufferedImage(Game.game.worldDimension.width,
				Game.game.worldDimension.height, BufferedImage.TYPE_INT_ARGB);

		g2d = (Graphics2D) newPanelImage.getGraphics();
		image = images.get("gameOver01");
		if (image == null)
		{
			System.err.println("Error - there is no such image");
		}

		g2d.drawImage(image, 0, 0, newPanelImage.getWidth(),
				newPanelImage.getHeight(), 0, 0, image.getWidth(),
				image.getHeight(), null);

		bp = new BackgroundPanel();
		bp.backgroundImage = newPanelImage;

		Point origin = new Point(140, 500);
		StringWriter.writeString(g2d, "game over", origin, false, Game.game.mainFontName,
				90, Color.yellow);

		backgroundPanels.put("gameOver", bp);

		// Game Over panel
		newPanelImage = new BufferedImage(Game.game.worldDimension.width,
				Game.game.worldDimension.height, BufferedImage.TYPE_INT_ARGB);

		g2d = (Graphics2D) newPanelImage.getGraphics();
		image = images.get("space04");
		if (image == null)
		{
			System.err.println("Error - there is no such image");
		}

		g2d.drawImage(image, 0, 0, newPanelImage.getWidth(),
				newPanelImage.getHeight(), 0, 0, image.getWidth(),
				image.getHeight(), null);

		bp = new BackgroundPanel();
		bp.backgroundImage = newPanelImage;

		origin = new Point(1920 / 2, 130);
		StringWriter.writeString(g2d, "high scores", origin, true, Game.game.mainFontName,
				85, Color.yellow);

		backgroundPanels.put("highScores", bp);

		// Options Panel
		newPanelImage = new BufferedImage(Game.game.worldDimension.width,
				Game.game.worldDimension.height, BufferedImage.TYPE_INT_ARGB);

		g2d = (Graphics2D) newPanelImage.getGraphics();
		image = images.get("space01");
		if (image == null)
		{
			System.err.println("Error - there is no such image");
		}

		g2d.drawImage(image, 0, 0, newPanelImage.getWidth(),
				newPanelImage.getHeight(), 0, 0, image.getWidth(),
				image.getHeight(), null);

		bp = new BackgroundPanel();
		bp.backgroundImage = newPanelImage;

		origin = new Point(1920 / 2, 130);
		StringWriter.writeString(g2d, "options", origin, true, Game.game.mainFontName, 85,
				Color.yellow);

		backgroundPanels.put("options", bp);

	}

	private static void loadImages() throws IOException
	{
//		BufferedReader br;
//		try
//		{
//			br = new BufferedReader(new FileReader("content/images/images.txt"));
//
//			String line = br.readLine();
//
//			while (line != null)
//			{
//				BufferedImage newImage;
//				newImage = ImageIO.read(new File("content/images/" + line
//						+ ".png"));
//				images.put(line, newImage);
//
//				line = br.readLine();
//			}
//
//		} catch (Exception e)
//		{
//			System.err.println("Could not read from file images.txt");
//			Game.game.exitGame();
//		}
		
		
		MyFileReader reader = new MyFileReader("content/images/images.txt");
		
		while(reader.hasMore == true)
		{
			String[] tokens = reader.getNextLineTokens(1);
			
			BufferedImage newImage;
			newImage = ImageIO.read(new File("content/images/" + tokens[0]
					+ ".png"));
			images.put(tokens[0], newImage);
			
		}
		
	}

	private static void loadFonts()
	{

		try
		{
			Font f = Font.createFont(Font.TRUETYPE_FONT, new File(
					"content/QuartzMS.ttf"));
			if (f != null)
			{
				GraphicsEnvironment ge = GraphicsEnvironment
						.getLocalGraphicsEnvironment();
				ge.registerFont(f);
				fonts.put("QuartzMS", f);
			}
		} catch (IOException | FontFormatException e)
		{
		}
	}

	private static void loadSpriteSheets() throws IOException
	{
		String path = "content/spriteSheets/spriteSheets.txt";
		MyFileReader reader = new MyFileReader(path);
		
		while(reader.hasMore == true)
		{
			String[] tokens = reader.getNextLineTokens(4);
			
			BufferedImage image;
			image = ImageIO.read(new File("content/spriteSheets/" + tokens[0] + ".png"));
			if(image == null)
			{
				System.err.println("Error: there is no spritesheet: " + tokens[0]);
				System.exit(1);
			}
			int horizontal = Integer.parseInt(tokens[1]);
			int vertical = Integer.parseInt(tokens[2]);
			int numOfImages = Integer.parseInt(tokens[3]);
			
			SpriteSheet shit = new SpriteSheet(tokens[0], image, horizontal, vertical, numOfImages);
			
			
			spriteSheets.put(tokens[0], shit);
			
		}
	}
}
