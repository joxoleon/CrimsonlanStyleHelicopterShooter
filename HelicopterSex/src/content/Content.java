package content;

import engine.Game;
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
import utility.StringWriter;

public class Content
{
	public static Map<String, BackgroundPanel> backgroundPanels = new HashMap<String, BackgroundPanel>();
	public static Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	public static Map<String, Font> fonts = new HashMap<String, Font>();
	
	// Terrain fields.
	public static Map<String, TerrainAsset> terrainAssets = new HashMap<String, TerrainAsset>();
	public static Map<String, BufferedImage> terrainTiles = new HashMap<String, BufferedImage>();
	public static Map<String, TerrainScrollDown> terrains = new HashMap<String, TerrainScrollDown>();
	
	// Models.
	public static ModelFactory modelFactory = ModelFactory.getInstance();
	
	
	
	
	public static void initializeContent()
	{
		loadImages();
		loadFonts();
		initializeBackgroundPanels();
		loadTerrainAssets();
		loadTerrainTiles();
		loadTerrains();
;
	}

	public static void initializeBackgroundPanels()
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
		StringWriter.writeString(g2d, "game over", origin, false, "Quartz MS",
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
		StringWriter.writeString(g2d, "high scores", origin, true, "Quartz MS",
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
		StringWriter.writeString(g2d, "options", origin, true, "Quartz MS", 85,
				Color.yellow);

		backgroundPanels.put("options", bp);

	}

	public static void loadImages()
	{
		BufferedReader br;
		try
		{
			br = new BufferedReader(new FileReader("content/images/images.txt"));

			String line = br.readLine();

			while (line != null)
			{
				BufferedImage newImage;
				newImage = ImageIO.read(new File("content/images/" + line
						+ ".png"));
				images.put(line, newImage);

				line = br.readLine();
			}

		} catch (Exception e)
		{
			System.err.println("Could not read from file images.txt");
			Game.game.exitGame();
		}
	}

	public static void loadFonts()
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

	public static void loadTerrainAssets()
	{
		BufferedReader br;
		try
		{
			br = new BufferedReader(
					new FileReader("content/terrain/assets.txt"));

			String line = br.readLine();

			while (line != null)
			{
				if (line.charAt(0) == '#')
				{
					continue;
				}
				String[] tokens = line.split(" ");

				BufferedImage newImage;
				newImage = ImageIO.read(new File("content/terrain/" + tokens[0]
						+ ".png"));

				TerrainAsset terrainAsset = new TerrainAsset(tokens[0],
						newImage, newImage.getWidth(), newImage.getHeight());
				terrainAssets.put(tokens[0], terrainAsset);

				line = br.readLine();
			}

		} catch (Exception e)
		{
			System.err.println("Could not read from file assets.txt");
			Game.game.exitGame();
		}
	}

	public static void loadTerrainTiles()
	{
		BufferedReader br;
		try
		{
			br = new BufferedReader(new FileReader("content/terrain/terrainTiles.txt"));

			String line = br.readLine();

			while (line != null)
			{
				BufferedImage newImage;
				newImage = ImageIO.read(new File("content/terrain/" + line
						+ ".png"));
				terrainTiles.put(line, newImage);

				line = br.readLine();
			}

		} catch (Exception e)
		{
			System.err.println("Could not read from file terrainTiles.txt");
			Game.game.exitGame();
		}
	}
	
	public static void loadTerrains()
	{
		BufferedReader br;
		try
		{
			br = new BufferedReader(
					new FileReader("content/terrain/terrains.txt"));

			String line = br.readLine();

			int currentState = 0;
			int counter = 0;

			TerrainScrollDown terrain = null;

			while (line != null)
			{
				// Skip if it is a comment or an empty line
				if (line.charAt(0) == '#')
				{
					line = br.readLine();
					continue;
				}
				String[] tokens = line.split(" ");
				if (tokens.length == 0)
				{
					line = br.readLine();
					continue;
				}
				
				
				// Simple state machine
				try
				{
					switch (currentState)
					{
					// State 0 :Read terrain name and add terrain into map.
					case 0:
					{
						if (tokens.length != 3)
						{
							throw new MyError(
									"Terrain read error: invalid terrain name");
						}
						terrain = new TerrainScrollDown(tokens[0]);
						terrains.put(tokens[0], terrain);
						terrain.terrainSpeed = Integer.parseInt(tokens[2]);
						terrain.numberOfTerrainPanels = Integer.parseInt(tokens[1]);
						currentState = 1;
					}
						break;
					
					// State 1: Read tile name.
					case 1:
					{
						if (tokens.length > 1)
						{
							throw new MyError(
									"Terrain read error: invalid terrain name");
						}
						terrain.terrainTileName = tokens[0];
						currentState = 2;
						
					}
						break;
					// State 1: Read number of assets.
					case 2:
					{
						if (tokens.length > 1)
						{
							throw new MyError(
									"Terrain read error: invalid number of tokens");
						}
						counter = Integer.parseInt(tokens[0]);
						currentState = 3;

					}
						break;

					// State 2: Read asset per line and when you came to the end then switch back to state 0.
					case 3:
					{
						String assetName = tokens[0];
						int numOnPanel = Integer.parseInt(tokens[1]);

						// If the width, height are not specfied
						if (tokens.length == 2)
						{
							terrain.addAsset(assetName, numOnPanel);
						}
						// If the width and height are specified
						else if (tokens.length == 4)
						{
							int width = Integer.parseInt(tokens[2]);
							int height = Integer.parseInt(tokens[3]);

							terrain.addAsset(assetName, numOnPanel, width,
									height);

						} else
						{
							throw new MyError(
									"Terrain read error: invalid asset line.");
						}

						counter--;
						if (counter == 0)
						{
							currentState = 0;
						}
					}
						break;

					}

				} catch (MyError e)
				{
					System.err.println(e);
				}


				line = br.readLine();
			}

		} catch (Exception e)
		{
			System.err.println("Could not read from file assets.txt");
			Game.game.exitGame();
		}
	}
}
