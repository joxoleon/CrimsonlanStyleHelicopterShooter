package terrain;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import camera.Camera;
import utility.Vector2;
import engine.Game;
import engine.GameTime;

public class TerrainScrollDown implements ITerrain
{

	// ******************** Fields ********************
	// Terrain panel fields
	public int numberOfTerrainPanels = 6;
	private int panelWidth;		// Based on Game.game.worldDimension.x
	private int panelHeight; 	// To be calculated.
	public String terrainTileName = "testTile01";
	public String terrainName = "vacant lot";
	private boolean isInitialized = false;

	// TerrainFields
	public float terrainHeight;
	public float terrainWidth;

	// Terrain panel array
	private TerrainPanel[] terrainPanels;
	private int currentPanelIndex = 0;

	// Terrain movement fields
	public Vector2 terrainPosition = new Vector2(0, 0);
	public float terrainSpeed = 140;

	// Terrain Assets.
	private LinkedList<String> assets = new LinkedList<String>();
	private Map<String, Integer> numOfAssets = new HashMap<String, Integer>();
	private Map<String, Float> maxAssetDeviations = new HashMap<String, Float>();
	private Map<String, Vector2> assetScales = new HashMap<String, Vector2>();
	
	
	
	

	// ******************** Constructors ********************
	public TerrainScrollDown(String terrainTileName)
	{
		this.terrainName = terrainTileName;
		panelWidth = Game.game.worldDimension.width;
		panelHeight = Game.game.worldDimension.height;
		terrainHeight = panelHeight;
		terrainWidth = panelWidth;
	}

	
	
	
	
	// ******************** Methods ********************
	// Main methods.
	public void initialize()
	{
		if (isInitialized == false)
		{
			initializeTerrainPanels();
			isInitialized = true;
		}

	}

	public void update(GameTime gameTime)
	{

		// Update terrain position.
		terrainPosition.y += terrainSpeed * gameTime.dt_s();

		if (terrainPosition.y > Game.game.worldDimension.height)
		{
			// Set to draw next panel.
			terrainPosition.y -= Game.game.worldDimension.height;
			currentPanelIndex = (currentPanelIndex + 1) % terrainPanels.length;
		}


	}

	@Override
	public void draw(Graphics2D g2d, Camera camera)
	{
		BufferedImage panel = terrainPanels[currentPanelIndex].panelImage;
		int panelWidth = panel.getWidth();
		int panelHeight = panel.getHeight();

		// Draw first panel
		float sourcex2 = (1920 - terrainPosition.x) / 1920 * panelWidth;
		float sourcey2 = (1080 - terrainPosition.y) / 1080 * panelHeight;
		g2d.drawImage(panel, (int) terrainPosition.x, (int) terrainPosition.y,
				1920, 1080, 0, 0, (int) sourcex2, (int) sourcey2, null);

		panel = terrainPanels[(currentPanelIndex + 1) % terrainPanels.length].panelImage;

		g2d.drawImage(panel, 0, 0, (int) terrainPosition.x + panelWidth,
				(int) terrainPosition.y, (int) sourcex2 - panelWidth,
				(int) sourcey2, (int) sourcex2, panelHeight, null);
		
	}

	
	
	// Public helper methods.
	public void resetTerrain()
	{
		terrainPosition.x = 0;
		terrainPosition.y = 0;
		currentPanelIndex = 0;
	}

	public void addAsset(String asset, int numOfTimesToDraw, float maxDeviation)
	{
		assets.addLast(asset);
		numOfAssets.put(asset, numOfTimesToDraw);
		maxAssetDeviations.put(asset, maxDeviation);
	}

	public void addAsset(String asset, int numOfTimesToDraw, float maxDeviation, float scaleX, float scaleY)
	{
		addAsset(asset, numOfTimesToDraw, maxDeviation);
		Vector2 assetScale = new Vector2(scaleX, scaleY);
		assetScales.put(asset, assetScale);

	}

	public void addAssetPosition(TerrainPanel panel, int x, int y)
	{
		panel.assetPositions.add(new Point(x, y));
	}

	public Vector2 getDimensions()
	{
		return new Vector2(terrainHeight, terrainWidth);
	}

	public TerrainType getType()
	{
		return TerrainType.SCROLL_TERRAIN;
	}
	
	
	
	
	
	
	// Private helper methods.
	private void initializeTerrainPanels()
	{
		// Get tile image from Content and calculate panel height and matrix data.
		BufferedImage panelImage = TerrainManager.terrainTiles.get(terrainTileName);
		panelHeight = 1080 - 1080 % panelImage.getHeight()
				+ panelImage.getHeight();
		int numOfTilesHorizontal = panelWidth / panelImage.getWidth() + 1;
		int numOfTilesVertical = panelHeight / panelImage.getHeight() + 1;

		// Create and fill terrain panels.
		terrainPanels = new TerrainPanel[numberOfTerrainPanels];
		for (int i = 0; i < terrainPanels.length; i++)
		{
			// Create TerrainPanel.
			BufferedImage panelBufferedImage = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_ARGB);
			terrainPanels[i] = new TerrainPanel();
			terrainPanels[i].height = panelHeight;
			terrainPanels[i].width = panelWidth;
			terrainPanels[i].panelImage = panelBufferedImage;

			// Get TerrainPanel graphics.
			Graphics2D g2d = (Graphics2D) terrainPanels[i].panelImage
					.getGraphics();

			// Fill panel with tiles.
			for (int j = 0; j < numOfTilesVertical; j++)
			{
				for (int j2 = 0; j2 < numOfTilesHorizontal; j2++)
				{
					g2d.drawImage(
							panelImage, 
							j2 * panelImage.getWidth(), j * panelImage.getHeight(), (j2 + 1) * panelImage.getWidth(), (j + 1) * panelImage.getHeight(), 
							0, 0, panelImage.getWidth(), panelImage.getHeight(), null
							);

				}
			}

			// Fill with assets
			for (String assetName : assets)
			{
				int numOfTimesToDraw = numOfAssets.get(assetName);
				float deviation = maxAssetDeviations.get(assetName) * 100;
				// Calculate new numOfTimesToDraw based on the deviation.
				numOfTimesToDraw += (numOfTimesToDraw * Math.random() * deviation) - deviation;
				if(numOfTimesToDraw < 1)
				{
					continue;
				}
				
				TerrainAsset asset = TerrainManager.terrainAssets.get(assetName);
				Rectangle2D dstRectangle = new Rectangle2D.Float(0, 0,
						panelWidth, panelHeight);
				Vector2 assetScale = assetScales.get(assetName);

				asset.drawAssets(terrainPanels[i], g2d, dstRectangle, numOfTimesToDraw, assetScale);
			}

		}

	}







}
