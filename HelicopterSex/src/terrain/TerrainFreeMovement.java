package terrain;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import managers.GraphicsManager;
import content.Content;
import engine.GameTime;
import engine.camera.Camera;
import engine.utility.MathHelper;
import engine.utility.Vector2;

public class TerrainFreeMovement implements ITerrain
{
	// test position fields.
	Vector2 position = null;
	float rotation = 0;
	Vector2 scale = new Vector2(1, 1);

	// ******************** Fields ********************
	BufferedImage terrain;
	public int terrainWidth = 5000;
	public int terrainHeight = 5000;
	public String terrainTileName = "snowTile01";
	public String name = "vacantLot";
	private boolean isInitialized = false;

	// Terrain Assets.
	private LinkedList<String> assets = new LinkedList<String>();
	private Map<String, Integer> numOfAssets = new HashMap<String, Integer>();
	private Map<String, Vector2> assetScales= new HashMap<String, Vector2>();
	private Map<String, Float> maxAssetDeviations = new HashMap<String, Float>();
	private LinkedList<Point> assetPositions = new LinkedList<Point>();

	AffineTransform backupTransform;

	// ******************** Constructors ********************
	public TerrainFreeMovement(String terrainName)
	{
		this.name = terrainName;
	}

	// ******************** Methods ********************
	public void draw(Graphics2D g2d, Camera camera)
	{
		GraphicsManager.saveGraphicsContext(g2d);

		Vector2 renderPosition = new Vector2(
				position.x - (camera.position.x - camera.rotatedCornerVector.x),
				position.y - (camera.position.y - camera.rotatedCornerVector.y));

		g2d.translate(renderPosition.x, renderPosition.y);
		g2d.rotate(rotation);

		g2d.drawImage(terrain, -terrainWidth / 2, -terrainHeight / 2,
				terrainWidth / 2, terrainHeight / 2, 0, 0, terrain.getWidth(),
				terrain.getHeight(), null);

		GraphicsManager.restoreGraphicsContext(g2d);
	}

	public void initialize()
	{
		if (isInitialized == false)
		{
			position = new Vector2(terrainWidth / 2, terrainHeight / 2);
			rotation = 0;
			scale = new Vector2(1, 1);
			initializeTerrain();
			isInitialized = true;
		}
	}

	public void addAsset(String asset, int numOfTimesToDraw,
			float maxDeviationNum)
	{
		assets.addLast(asset);
		numOfAssets.put(asset, numOfTimesToDraw);
		maxAssetDeviations.put(asset, maxDeviationNum);
	}

	public void addAsset(String asset, int numOfTimesToDraw,
			float maxDeviationNum, float scaleX, float scaleY)
	{
		addAsset(asset, numOfTimesToDraw, maxDeviationNum);
		Vector2 assetScale = new Vector2(scaleX, scaleY);
		assetScales.put(asset, assetScale);
	}

	private void initializeTerrain()
	{
		BufferedImage panelImage = TerrainManager.terrainTiles
				.get(terrainTileName);

		int numOfTilesHorizontal = terrainWidth / panelImage.getWidth() + 1;
		int numOfTilesVertical = terrainHeight / panelImage.getHeight() + 1;

		terrain = new BufferedImage(terrainWidth, terrainHeight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) terrain.getGraphics();

		// Fill terrain with tiles.
		for (int j = 0; j < numOfTilesVertical; j++)
		{
			for (int j2 = 0; j2 < numOfTilesHorizontal; j2++)
			{
				g2d.drawImage(panelImage, j2 * panelImage.getWidth(), j
						* panelImage.getHeight(),
						(j2 + 1) * panelImage.getWidth(),
						(j + 1) * panelImage.getHeight(), 0, 0,
						panelImage.getWidth(), panelImage.getHeight(), null);

			}
		}

		// Fill terrain with assets.
		for (String assetName : assets)
		{
			int numOfTimesToDraw = numOfAssets.get(assetName);
			float deviation = maxAssetDeviations.get(assetName);
			
//			String testPrint = assetName + " initial num of times to draw = " + numOfTimesToDraw + ", after deviation = ";

			// Calculate new numOfTimesToDraw based on the deviation.
			numOfTimesToDraw += (numOfTimesToDraw * Math.random() * deviation * 2) - (deviation * numOfTimesToDraw);
			if(numOfTimesToDraw < 1)
			{
				continue;
			}
			
//			testPrint += numOfTimesToDraw;
//			System.out.println(testPrint);
			
			TerrainAsset asset = TerrainManager.terrainAssets.get(assetName);
			Rectangle2D dstRectangle = new Rectangle2D.Float(0, 0,
					terrainWidth, terrainHeight);
			Vector2 assetScale = assetScales.get(assetName);

			asset.drawAssets(this, g2d, dstRectangle, numOfTimesToDraw, assetScale);
		}

	}

	public boolean isValidAssetPosition(int x, int y, int minDistanceX,
			int minDistanceY)
	{
		for (Point point : assetPositions)
		{
			int distanceX = (int) Math.abs(point.getX() - x);
			if (distanceX <= minDistanceX)
			{
				int distanceY = (int) Math.abs(point.getY() - y);
				if (distanceY < minDistanceY)
				{
					return false;
				}
			}
		}

		return true;
	}

	public void addAssetPosition(int x, int y)
	{
		assetPositions.add(new Point(x, y));
	}

	@Override
	public void update(GameTime gameTime)
	{

	}

	@Override
	public Vector2 getDimensions()
	{
		return new Vector2(terrainWidth, terrainHeight);
	}

	@Override
	public TerrainType getType()
	{
		return TerrainType.REGULAR_TERRAIN;
	}
}
