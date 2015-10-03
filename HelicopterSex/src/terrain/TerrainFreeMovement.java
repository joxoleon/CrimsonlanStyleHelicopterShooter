package terrain;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.management.ImmutableDescriptor;

import camera.Camera;
import content.Content;

public class TerrainFreeMovement
{
	// ******************** Fields ******************** 
	BufferedImage terrain;
	private int terrainWidth = 6000;
	private int terrainHeight = 6000;
	public String terrainTileName = "snowTile01";
	public String name = "vacantLot";
	private boolean isInitialized = false;
	
	// Terrain Assets.
	private LinkedList<String> assets = new LinkedList<String>();
	private Map<String, Integer> numOfAssets = new HashMap<String, Integer>();
	private Map<String, Point> assetDimensions = new HashMap<String, Point>();
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
//		int dstX1 = (int) (camera.position.x - camera.cameraWidth / 2);
//		int dstX2 = (int) (camera.position.x + camera.cameraWidth / 2);
//		int dstY1 = (int) (camera.position.y - camera.cameraHeight / 2);
//		int dstY2 = (int) camera.position.y + camera.cameraHeight / 2;
		
		int srcX1 = (int) (camera.position.x);
		int srcX2 = (int) (camera.position.x + terrainWidth);
		int srcY1 = (int) (camera.position.y);
		int srcY2 = (int) (camera.position.y + terrainHeight);
		
		System.out.println(srcX1 + " , " + srcY1 + ", " + srcX2 + ", " + srcY2);
		initContext(g2d, camera);
		
		g2d.drawImage(
				terrain,
				(int)-camera.position.x, (int)- camera.position.y,(int)(terrainWidth - camera.position.x),(int)(terrainHeight - camera.position.y),
				srcX1, srcY1, srcX2, srcY2,
				null
				);
		
		restoreContext(g2d);
				
	}

	public void initialize()
	{
		if(isInitialized == false)
		{
			initializeTerrain();
			isInitialized = true;
		}
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
	
	private void initializeTerrain()
	{
		BufferedImage panelImage = Content.terrainTiles.get(terrainTileName);
		
		int numOfTilesHorizontal = terrainWidth / panelImage.getWidth() + 1;
		int numOfTilesVertical = terrainHeight / panelImage.getHeight() + 1;
		
		terrain = new BufferedImage(terrainWidth, terrainHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) terrain.getGraphics();
		
		// Fill terrain with tiles.
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

			}
		}
		
		// Fill terrain with assets.
		for (String assetName : assets)
		{
			int numOfTimesToDraw = numOfAssets.get(assetName);
			TerrainAsset asset = Content.terrainAssets.get(assetName);
			Rectangle2D dstRectangle = new Rectangle2D.Float(0, 0, terrainWidth, terrainHeight);
			
			asset.drawAssets(this, g2d, dstRectangle, numOfTimesToDraw);
		}
		
	}
	public boolean isValidAssetPosition(int x, int y, int minDistanceX, int minDistanceY)
	{
		for (Point point : assetPositions)
		{
			int distanceX = (int) Math.abs(point.getX() - x);
			if(distanceX <= minDistanceX)
			{
				int distanceY = (int) Math.abs(point.getY() - y);
				if(distanceY < minDistanceY)
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

	private void initContext(Graphics2D g2d, Camera camera)
	{
		backupTransform = g2d.getTransform();
		g2d.translate(camera.cameraWidth / 2, camera.cameraHeight / 2);
		g2d.rotate(camera.rotation);
		g2d.scale(camera.scale.x, camera.scale.y);
		g2d.translate(-camera.cameraWidth / 2, -camera.cameraHeight / 2);
	}
	private void restoreContext(Graphics2D g2d)
	{
		g2d.setTransform(backupTransform);
	}
}
