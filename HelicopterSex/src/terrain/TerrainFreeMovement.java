package terrain;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import utility.MathHelper;
import utility.Vector2;
import managers.GraphicsManager;
import camera.Camera;
import content.Content;
import engine.GameTime;

public class TerrainFreeMovement
implements ITerrain
{
	// test position fields.
	Vector2 position;
	float rotation = 0;
	Vector2 scale;
	
	// ******************** Fields ******************** 
	BufferedImage terrain;
	private int terrainWidth = 5000;
	private int terrainHeight = 5000;
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
//		
//		int srcX1 = (int) (camera.position.x);
//		int srcX2 = (int) (camera.position.x + terrainWidth);
//		int srcY1 = (int) (camera.position.y);
//		int srcY2 = (int) (camera.position.y + terrainHeight);
//		
//		System.out.println(srcX1 + " , " + srcY1 + ", " + srcX2 + ", " + srcY2);
//		initContext(g2d, camera);
//		
//		g2d.drawImage(
//				terrain,
//				(int)-camera.position.x, (int)- camera.position.y,(int)(terrainWidth - camera.position.x),(int)(terrainHeight - camera.position.y),
//				srcX1, srcY1, srcX2, srcY2,
//				null
//				);
//		
//		restoreContext(g2d);
		
		
		
//		
//		
//		GraphicsManager.saveGraphicsContext(g2d);
//		
//		// Calculate render position, and render Rotation.
//		Vector2 cameraCornerOffset = new Vector2(
//						position.x - (camera.position.x - camera.rotatedCornerVector.x),
//						position.y - (camera.position.y - camera.rotatedCornerVector.y)
//						);
//		float renderRotation = rotation - camera.rotation;
//		
//		// Jebem ti scale, sta da radim!
//		
//		g2d.translate(cameraCornerOffset.x, cameraCornerOffset.y);
//		g2d.rotate(rotation);
//		
//		int dstX1 = (int) cameraCornerOffset.x;
//		int dstY1 = (int) cameraCornerOffset.y;
//		int dstX2 = (int) cameraCornerOffset.x ;
//		int dstY2 = (int) cameraCornerOffset.y;
//		
//		// Calculate source for the terrain!
//		int srcX1 = (int) (camera.position.x - camera.cameraWidth / 2);
//		int srcX2 = (int) (srcX1 + terrainWidth);
//		int srcY1 = (int) (camera.position.y - camera.cameraHeight / 2);
//		int srcY2 = (int) (srcY1 + terrainHeight);
//		
//	
//		
//		System.out.println("camera position =" + camera.position);
//		System.out.println("terrainPosition = " + position);
//		System.out.println("corner vector = " + camera.cornerVector);
//		System.out.println("rotated corner vector = " + camera.rotatedCornerVector);
//		System.out.println("camera corner offset = " + cameraCornerOffset);
//		
////		System.out.println("destination X (" + dstX1 + ", " + dstX2 + ")");
////		System.out.println("destination Y (" + dstY1 + ", " + dstY2 + ")");
////		System.out.println("source X (" + srcX1 + ", " + srcX2 + ")");
////		System.out.println("source Y (" + srcY1 + ", " + srcY2 + ")");
//
//		g2d.drawImage(
//				terrain,
//				dstX1, dstY1, dstX2, dstY2,
//				srcX1, srcY1, srcX2, srcY2,
//				null
//				);
//		
////		g2d.drawImage( 
////				terrain,
////				- terrainWidth / 2, - terrainHeight / 2, terrainWidth / 2, terrainHeight / 2,
////				0, 0, terrain.getWidth(), terrain.getHeight(),
////				null
////				);
//		
//		GraphicsManager.restoreGraphicsContext(g2d);
		
		
		GraphicsManager.saveGraphicsContext(g2d);
		
		Vector2 renderPosition = new Vector2(
				position.x - (camera.position.x - camera.rotatedCornerVector.x),
				position.y - (camera.position.y - camera.rotatedCornerVector.y)						
						);
		
		g2d.translate(renderPosition.x, renderPosition.y);
		g2d.rotate(rotation);
		
		g2d.drawImage( 
				terrain,
				-terrainWidth / 2, - terrainHeight / 2, terrainWidth / 2, terrainHeight / 2,
				0, 0, terrain.getWidth(), terrain.getHeight(),
				null
				);
		
		GraphicsManager.restoreGraphicsContext(g2d);		
	}

	public void initialize()
	{
		if(isInitialized == false)
		{
			position = new Vector2(terrainWidth / 2, terrainHeight / 2);
			rotation = 0;
			scale = new Vector2(1, 1);
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
	
//	private void initContext(Graphics2D g2d, Camera camera)
//	{
//		backupTransform = g2d.getTransform();
//		g2d.translate(camera.cameraWidth / 2, camera.cameraHeight / 2);
//		g2d.rotate(camera.rotation);
//		g2d.scale(camera.scale.x, camera.scale.y);
//		g2d.translate(-camera.cameraWidth / 2, -camera.cameraHeight / 2);
//	}
//	private void restoreContext(Graphics2D g2d)
//	{
//		g2d.setTransform(backupTransform);
//	}
}
