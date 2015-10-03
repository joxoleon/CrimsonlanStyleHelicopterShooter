package terrain;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import content.Content;
import engine.GameTime;

public class TerrainScrollDown
{
	
	// ******************** Fields ******************** 
	// Terrain panel fields
	public int numberOfTerrainPanels = 5;
	private int panelWidth = 1920;
	private int panelHeight = 0; // To be calculated
	public String terrainTileName = "testTile01";
	public String terrainName = "vacant lot";
	private boolean isInitialized = false;
	
	// Terrain panel array
	private TerrainPanel[] terrainPanels;
	private int currentPanelIndex = 0;
	
	// Terrain movement fields
	float terrainPositionX = 0;
	float terrainPositionY = 0;
	public double terrainSpeed = 140;
	
	// Terrain Assets.
	private LinkedList<String> assets = new LinkedList<String>();
	private Map<String, Integer> numOfAssets = new HashMap<String, Integer>();
	private Map<String, Point> assetDimensions = new HashMap<String, Point>();
	
	
	// ******************** Constructors ******************** 
	public TerrainScrollDown(String terrainTileName)
	{
		this.terrainName = terrainTileName;
	}
	
	
	
	
	// ******************** Methods ******************** 
	// Main methods.
	public void initialize()
	{
		if(isInitialized == false)
		{
			initializeTerrainPanels();
			isInitialized = true;
		}
		

	}
	public void update(GameTime gameTime)
	{
		
		
		// Update terrain position.
		terrainPositionY += terrainSpeed * gameTime.dt_s();
				
		if(terrainPositionY > 1080)
		{
			// Set to draw next panel.
			terrainPositionY -= 1080;
			currentPanelIndex = (currentPanelIndex + 1) % terrainPanels.length;
		}
		
	}
	public void draw(Graphics2D g2d)
	{
		BufferedImage panel = terrainPanels[currentPanelIndex].panelImage;
		int panelWidth = panel.getWidth();
		int panelHeight = panel.getHeight();
		
		// Draw first panel
		float sourcex2 = (1920 - terrainPositionX) / 1920 * panelWidth;
		float sourcey2 = (1080 - terrainPositionY) / 1080 * panelHeight;
		g2d.drawImage(
				panel, 
				(int)terrainPositionX, (int)terrainPositionY, 1920, 1080,
				0, 0, (int)sourcex2, (int)sourcey2, 
				null
				);
		
		
		panel = terrainPanels[(currentPanelIndex + 1 ) % terrainPanels.length].panelImage;
		

		g2d.drawImage(
				panel, 
				0, 0, (int)terrainPositionX + panelWidth, (int)terrainPositionY,
				(int)sourcex2 - panelWidth, (int)sourcey2, (int)sourcex2, panelHeight,
				null
				);
	}
	
	// Public helper methods.
	public void resetTerrain()
	{
		terrainPositionX = 0;
		terrainPositionY = 0;
		currentPanelIndex = 0;
	}
	public void addAsset(String asset, int numOfTimesToDraw)
	{
		assets.addLast(asset);
		numOfAssets.put(asset, numOfTimesToDraw);
	}
	public void addAsset(String asset, int numOfTimesToDraw, int width, int height)
	{
		assets.addLast(asset);
		numOfAssets.put(asset, numOfTimesToDraw);
		Point assetDimension = new Point(width, height);
		assetDimensions.put(asset, assetDimension);
		
	}
	public void addAssetPosition(TerrainPanel panel, int x, int y)
	{
		panel.assetPositions.add(new Point(x,y));
	}
	
	
	// Private helper methods (Decomposition methods).
	private void initializeTerrainPanels()
	{				
		// Get tile image from Content and calculate panel height and matrix data.
		BufferedImage panelImage = Content.terrainTiles.get(terrainTileName);
		panelHeight = 1080 - 1080 % panelImage.getHeight() + panelImage.getHeight();
		int numOfTilesHorizontal = panelWidth / panelImage.getWidth() + 1;
		int numOfTilesVertical = panelHeight / panelImage.getHeight() + 1;

				
		
		terrainPanels = new TerrainPanel[numberOfTerrainPanels];
		for(int i = 0; i < terrainPanels.length; i++)
		{
			BufferedImage panelBufferedImage = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_ARGB);
			terrainPanels[i] = new TerrainPanel();
			terrainPanels[i].height = panelHeight;
			terrainPanels[i].width = panelWidth;
			terrainPanels[i].panelImage = panelBufferedImage;
			
			Graphics2D g2d = (Graphics2D) terrainPanels[i].panelImage.getGraphics();
			
			
			
			
			// Fill panel with tiles.
			for (int j = 0; j < numOfTilesVertical; j++)
			{
				for (int j2 = 0; j2 < numOfTilesHorizontal; j2++)
				{
					g2d.drawImage(
							panelImage, 
							j2 * panelImage.getWidth(), j * panelImage.getHeight(), (j2 + 1) * panelImage.getWidth(), (j + 1) * panelImage.getHeight(), 
							0, 0, panelImage.getWidth(), panelImage.getHeight(), 
							null
							);
					
//					Point origin = new Point(1920 / 2, 1080 / 2);
//					StringWriter.writeString(g2d, "Jebeni panel: " + i, origin, true, "Quartz MS", 90, Color.black);
				}
			}
			
			// Fill with assets
			for (String assetName : assets)
			{
				int numOfTimesToDraw = numOfAssets.get(assetName);
				TerrainAsset asset = Content.terrainAssets.get(assetName);
				Rectangle2D dstRectangle = new Rectangle2D.Float(0, 0, panelWidth, panelHeight);
				
				asset.drawAssets(terrainPanels[i], g2d, dstRectangle, numOfTimesToDraw);
			}
			
		}

	}
	
}
