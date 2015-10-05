package terrain;

import input.Input;
import input.Keys;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import terrain.ITerrain.TerrainType;
import utility.MyFileReader;
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
	
	
	public static void initialize() throws IOException, MyError
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
	
	private static void loadScrollDownTerrains() throws MyError
	{		
		int currentState = 0;
		int counter = 0;

		TerrainScrollDown terrain = null;
		MyFileReader reader = new MyFileReader("content/terrain/scrollDownTerrains.txt");
		while(reader.hasMore == true)
		{
			// Simple state machine
			switch(currentState)
			{
			// State 0 :Read terrainName, numberOfPanels and terrainSpeed and add the terrain into the hashMap.
			case 0:
			{
				String[] tokens = reader.getNextLineTokens(3);
				
				 terrain = new TerrainScrollDown(tokens[0]);
				 scrollDownTerrains.put(tokens[0], terrain);
				 scrollDownTerrainNames.add(tokens[0]);
				 terrain.terrainSpeed = Integer.parseInt(tokens[2]);
				 terrain.numberOfTerrainPanels =
				 Integer.parseInt(tokens[1]);
				 currentState = 1;
			}break;
			
			// State 1: Read tile name.
			case 1:
			{
				String[] tokens = reader.getNextLineTokens(1);
				terrain.terrainTileName = tokens[0];
				currentState = 2;

			}break;
			
			// State 2: Read number of assets.
			case 2:
			{
				String[] tokens = reader.getNextLineTokens(1);
				counter = Integer.parseInt(tokens[0]);
				currentState = 3;
				
			}break;
			
			// then switch back to state 0.
			case 3:
			{
				String[] tokens = reader.getNextLineTokens();
				
				String assetName = tokens[0];
				int numOnPanel = Integer.parseInt(tokens[1]);
				float maxDeviation = Float.parseFloat(tokens[2]);

				// If the width, height are not specfied
				if (tokens.length == 3)
				{
					terrain.addAsset(assetName, numOnPanel,
							maxDeviation);
				}
				// If the width and height are specified
				else if (tokens.length == 5)
				{
					float scaleX = Float.parseFloat(tokens[3]);
					float scaleY = Float.parseFloat(tokens[4]);

					terrain.addAsset(assetName, numOnPanel,
							maxDeviation, scaleX, scaleY);

				} else
				{
					throw new MyError("Terrain read error: invalid asset line.");
				}

				counter--;
				if (counter == 0)
				{
					currentState = 0;
				}
			}break;
			}
		}
	}
	
	private static void loadFreeMovementTerrains() throws MyError
	{
		int currentState = 0;
		int counter = 0;

		TerrainFreeMovement terrain = null;
		MyFileReader reader = new MyFileReader("content/terrain/freeMovementTerrains.txt");
		while(reader.hasMore == true)
		{
			// Simple state machine
			switch(currentState)
			{
			// State 0 :Read terrainName, terrainWidth and terrainHeight and add the terrain into the hashMap.
			case 0:
			{
				String[] tokens = reader.getNextLineTokens(3);
				
				 terrain = new TerrainFreeMovement(tokens[0]);
				 terrain.terrainWidth = Integer.parseInt(tokens[1]);
				 terrain.terrainHeight = Integer.parseInt(tokens[2]);
				 
				 freeMovementTerrains.put(tokens[0], terrain);
				 freeMovementTerrainNames.add(tokens[0]);
				 
				 currentState = 1;
			}break;
			
			// State 1: Read tile name.
			case 1:
			{
				String[] tokens = reader.getNextLineTokens(1);
				terrain.terrainTileName = tokens[0];
				currentState = 2;

			}break;
			
			// State 2: Read number of assets.
			case 2:
			{
				String[] tokens = reader.getNextLineTokens(1);
				counter = Integer.parseInt(tokens[0]);
				currentState = 3;
				
			}break;
			
			// State 3: Read an asset name, number of asets on panel, maximum deviation of number of assets, from each line.
			case 3:
			{
				String[] tokens = reader.getNextLineTokens();
				
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
					float scaleX = Float.parseFloat(tokens[3]);
					float scaleY = Float.parseFloat(tokens[4]);

					terrain.addAsset(assetName, numOnPanel, maxDeviation, scaleX, scaleY);

				} else
				{
					throw new MyError("Terrain read error: invalid asset line.");
				}

				counter--;
				if (counter == 0)
				{
					currentState = 0;
				}
			}break;
			}
		}


	}
	

	public static void loadTerrainAssets() throws IOException
	{
//		BufferedReader br;
//		try
//		{
//			br = new BufferedReader(
//					new FileReader("content/terrain/assets.txt"));
//
//			String line = br.readLine();
//
//			while (line != null)
//			{
//				if (line.charAt(0) == '#')
//				{
//					continue;
//				}
//				String[] tokens = line.split(" ");
//
//				BufferedImage newImage;
//				newImage = ImageIO.read(new File("content/terrain/" + tokens[0]
//						+ ".png"));
//
//				TerrainAsset terrainAsset = new TerrainAsset(tokens[0],
//						newImage, newImage.getWidth(), newImage.getHeight());
//				terrainAssets.put(tokens[0], terrainAsset);
//
//				line = br.readLine();
//			}
//
//		} catch (Exception e)
//		{
//			System.err.println("Could not read from file assets.txt");
//			Game.game.exitGame();
//		}
		
		
		MyFileReader reader = new MyFileReader("content/terrain/assets.txt");
		while(reader.hasMore == true)
		{
			String[] tokens = reader.getNextLineTokens(1);
			
			BufferedImage newImage = ImageIO.read(new File("content/terrain/" + tokens[0] + ".png"));
			TerrainAsset terrainAsset = new TerrainAsset(tokens[0], newImage, newImage.getWidth(), newImage.getHeight());
			terrainAssets.put(tokens[0], terrainAsset);
			
		}
		
		
	}

	public static void loadTerrainTiles() throws IOException
	{
//		BufferedReader br;
//		try
//		{
//			br = new BufferedReader(new FileReader("content/terrain/terrainTiles.txt"));
//
//			String line = br.readLine();
//
//			while (line != null)
//			{
//				BufferedImage newImage;
//				newImage = ImageIO.read(new File("content/terrain/" + line
//						+ ".png"));
//				terrainTiles.put(line, newImage);
//
//				line = br.readLine();
//			}
//
//		} catch (Exception e)
//		{
//			System.err.println("Could not read from file terrainTiles.txt");
//			Game.game.exitGame();
//		}
//	
//	
//		
		MyFileReader reader = new MyFileReader("content/terrain/terrainTiles.txt");
		while(reader.hasMore == true)
		{
			String[] tokens = reader.getNextLineTokens(1);
			BufferedImage newImage;
			newImage = ImageIO.read(new File("content/terrain/" + tokens[0]
					+ ".png"));
			terrainTiles.put(tokens[0], newImage);
		}
	}
}
