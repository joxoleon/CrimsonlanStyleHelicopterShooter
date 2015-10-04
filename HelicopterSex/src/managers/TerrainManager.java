package managers;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import content.MyError;
import camera.Camera;
import engine.Game;
import engine.GameTime;
import terrain.ITerrain;
import terrain.TerrainFreeMovement;
import terrain.TerrainScrollDown;

public class TerrainManager
{
	// Scroll down terrains.
	private static Map<String, TerrainScrollDown> scrollDownTerrains = new HashMap<String, TerrainScrollDown>();
	private static ArrayList<String> scrollDownTerrainNames = new ArrayList<String>();
	
	// Free movement terrains.
	private static Map<String, TerrainFreeMovement>freeMovementTerrains = new HashMap<String, TerrainFreeMovement>();
	private static ArrayList<String> freeMovementTerrainNames = new ArrayList<String>();
	
	private static ITerrain currentTerrain = null;
	
	
	public static void initialize()
	{
		loadScrollDownTerrains();
		loadFreeMovementTerrains();
	}
	
	public static void setFreeMovementTerrain()
	{
		int index = (int) (Math.random() * freeMovementTerrainNames.size());
		currentTerrain = freeMovementTerrains.get(freeMovementTerrainNames.get(index));
	}
	
	public static void setFreeMovementTerrain(String terrainName)
	{
		currentTerrain = freeMovementTerrains.get(terrainName);
		if(currentTerrain == null)
		{
			System.err.println("Unable to find terrain: " + terrainName);
			Game.game.exitGame();
		}
	}
	
	public static void setScrollDownTerrain(String terrainName)
	{
		currentTerrain = scrollDownTerrains.get(terrainName);
		if(currentTerrain == null)
		{
			System.err.println("Unable to find terrain: " + terrainName);
			Game.game.exitGame();
		}
	}
	
	public static void setScrollDownTerrain()
	{
		int index = (int) (Math.random() * scrollDownTerrainNames.size());
		currentTerrain = scrollDownTerrains.get(scrollDownTerrainNames.get(index));
	}
	
	public static void update(GameTime gameTime)
	{
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
	
	private static void loadFreeMovementTerrains()
	{
		
	}
}
