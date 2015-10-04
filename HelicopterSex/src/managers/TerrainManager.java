package managers;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import engine.GameTime;
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
	
	
	public static void initialize()
	{
		loadScrollDownTerrains();
		loadFreeMovementTerrains();
	}
	
	public static void update(GameTime gameTime)
	{
		
	}

	public static void draw(Graphics2D g2d)
	{
		
	}
	
	private static void loadScrollDownTerrains()
	{
		
	}
	
	private static void loadFreeMovementTerrains()
	{
		
	}
}
