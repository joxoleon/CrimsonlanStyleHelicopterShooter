package terrain;

import input.Input;
import input.Keys;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import terrain.ITerrain.TerrainType;
import content.MyError;
import camera.Camera;
import engine.Game;
import engine.GameTime;

public class TerrainManager
{
	// Scroll down terrains.
	private static Map<String, TerrainScrollDown> scrollDownTerrains = new HashMap<String, TerrainScrollDown>();
	private static ArrayList<String> scrollDownTerrainNames = new ArrayList<String>();
	private static int scrollTerrainIndex;
	
	// Free movement terrains.
	private static Map<String, TerrainFreeMovement>freeMovementTerrains = new HashMap<String, TerrainFreeMovement>();
	private static ArrayList<String> freeMovementTerrainNames = new ArrayList<String>();
	private static int freeTerrainIndex;
	
	// Terrain assets.
	public static Map<String, TerrainAsset> terrainAssets = new HashMap<String, TerrainAsset>();
	public static Map<String, BufferedImage> terrainTiles = new HashMap<String, BufferedImage>();
	
	private static ITerrain currentTerrain = null;
	
	
	public static void initialize()
	{
		loadTerrainTiles();
		loadTerrainAssets();
		loadScrollDownTerrains();
		loadFreeMovementTerrains();
	}
	
	public static void setFreeMovementTerrain()
	{
		freeTerrainIndex = (int) (Math.random() * freeMovementTerrainNames.size());
		currentTerrain = freeMovementTerrains.get(freeMovementTerrainNames.get(freeTerrainIndex));
		currentTerrain.initialize();
	}
	
	public static void setFreeMovementTerrain(String terrainName)
	{
		currentTerrain = freeMovementTerrains.get(terrainName);
		if(currentTerrain == null)
		{
			System.err.println("Unable to find terrain: " + terrainName);
			Game.game.exitGame();
		}
		currentTerrain.initialize();
	}
	
	public static void setScrollDownTerrain(String terrainName)
	{
		currentTerrain = scrollDownTerrains.get(terrainName);
		if(currentTerrain == null)
		{
			System.err.println("Unable to find terrain: " + terrainName);
			Game.game.exitGame();
		}
		currentTerrain.initialize();
	}
	
	public static void setScrollDownTerrain()
	{
		scrollTerrainIndex = (int) (Math.random() * scrollDownTerrainNames.size());
		currentTerrain = scrollDownTerrains.get(scrollDownTerrainNames.get(scrollTerrainIndex));
		currentTerrain.initialize();
	}
	
	public static void update(GameTime gameTime)
	{
		if(Input.isKeyPressed(Keys.Y))
		{
			if(currentTerrain.getType() == TerrainType.SCROLL_TERRAIN)
			{
				scrollTerrainIndex = (scrollTerrainIndex + 1) % scrollDownTerrainNames.size();
				currentTerrain = scrollDownTerrains.get(scrollDownTerrainNames.get(scrollTerrainIndex));
			}
			else if(currentTerrain.getType() == TerrainType.REGULAR_TERRAIN)
			{
				freeTerrainIndex = (freeTerrainIndex + 1) % freeMovementTerrainNames.size();
				currentTerrain = freeMovementTerrains.get(freeMovementTerrainNames.get(freeTerrainIndex));
			}
		}
		
		if(currentTerrain != null)
		{
			currentTerrain.update(gameTime);
		}
	}

	public static void draw(Graphics2D g2d, Camera camera)
	{
		if(currentTerrain != null)
		{
			currentTerrain.draw(g2d, camera);
		}
	}
	
	private static void loadScrollDownTerrains()
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
				if (line.length() == 0 || line.startsWith("#"))
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
						scrollDownTerrains.put(tokens[0], terrain);
						scrollDownTerrainNames.add(tokens[0]);
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
					// State 2: Read number of assets.
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
						float maxDeviation = Float.parseFloat(tokens[2]);

						// If the width, height are not specfied
						if (tokens.length == 3)
						{
							terrain.addAsset(assetName, numOnPanel, maxDeviation);
						}
						// If the width and height are specified
						else if (tokens.length == 5)
						{
							int width = Integer.parseInt(tokens[3]);
							int height = Integer.parseInt(tokens[4]);

							terrain.addAsset(assetName, numOnPanel,maxDeviation, width, height);

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
			e.printStackTrace();
			System.err.println("Could not read from file assets.txt");
			Game.game.exitGame();
		}
	}
	
	private static void loadFreeMovementTerrains()
	{
		// TODO : load free movement terrains.
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
}
