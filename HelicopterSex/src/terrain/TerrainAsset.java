package terrain;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import engine.utility.Vector2;

public class TerrainAsset
{
	// ******************** Fields ******************** 
	// Name.
	public String name;
	
	// Image details.
	BufferedImage assetImage;
	public int dstWidth;
	public int dstHeight;
	public Vector2 userScale = new Vector2(1, 1);
	
	public static int invalidPositionThreshold = 50;
	
	
	// ******************** Constructors ******************** 
	public TerrainAsset(String imageName, BufferedImage assetImage, int dstWidth, int dstHeight)
	{
		this.name = imageName;
		this.assetImage = assetImage;
		this.dstWidth = dstWidth;
		this.dstHeight = dstHeight;
	}

	
	
	// ******************** Methods ******************** 
	private void drawAsset(Graphics2D g2d, Point2D position, boolean isCentered)
	{
//		System.out.println(userScale);
		int x1 = (int) (position.getX());
		int y1 = (int) (position.getY());
		if(isCentered == true)
		{
			x1 -= (int) (dstWidth * userScale.x / 2) ;
			y1 -= (int) (dstHeight * userScale.y / 2);
		}
		int x2 = (int) (x1 + dstWidth * userScale.x);
		int y2 = (int) (y1 + dstHeight * userScale.y);
		
		g2d.drawImage(
				assetImage,
				x1, y1, x2, y2,
				0, 0, assetImage.getWidth(), assetImage.getHeight(),
				null				
				);
	}
	
	private void drawAssets(TerrainPanel panel, Graphics2D g2d, Rectangle2D areaRectangle, int numberOfAssets)
	{
		int minX = (int) (areaRectangle.getX() + dstWidth * userScale.x / 2);
		int maxX = (int) (areaRectangle.getWidth() - dstWidth * userScale.x / 2);
		

		int minY = (int) (areaRectangle.getY() + dstHeight * userScale.x / 2);
		int maxY = (int) (areaRectangle.getHeight()- dstHeight * userScale.y / 3);
		// Ne treba 3 nego 2, mali hack.
		// TODO: edit this hack!
		
		int invalidAssetPositionCounter = 0;
		
		for (int i = 0; i < numberOfAssets; i++)
		{
			int x = (int) (minX + Math.random() * (maxX - minX));
			int y = (int) (minY + Math.random() * (maxY - minY));
						
			if(panel.isValidAssetPosition(x, y, (int)(dstWidth * userScale.x), (int)(dstHeight * userScale.y)) == true)
			{
				
				panel.addAssetPosition(x, y);
				Point2D point = new Point2D.Float(x, y);
				drawAsset(g2d, point, true);
			}
			else
			{
				invalidAssetPositionCounter++;
				if(invalidAssetPositionCounter < invalidPositionThreshold)
				{
					i--;
				}
				
			}

			
		}

	}
	
	private void drawAssets(TerrainFreeMovement terrain, Graphics2D g2d, Rectangle2D areaRectangle, int numberOfAssets)
	{
		int minX = (int) (areaRectangle.getX() + dstWidth / 2);
		int maxX = (int) (minX + areaRectangle.getWidth() - dstWidth);
		
		int minY = (int) (areaRectangle.getY() + dstHeight / 2);
		int maxY = (int) (minY + areaRectangle.getHeight()- dstHeight);
		
		int invalidAssetPositionCounter = 0;

		for (int i = 0; i < numberOfAssets; i++)
		{
			int x = (int) (minX + Math.random() * (maxX - minX));
			int y = (int) (minY + Math.random() * (maxY - minY));
			
			if(terrain.isValidAssetPosition(x, y, (int)(dstWidth * userScale.x), (int)(dstHeight * userScale.y)) == true)
			{
				terrain.addAssetPosition(x, y);
				Point2D point = new Point2D.Float(x, y);
				drawAsset(g2d, point, true);
			}
			else
			{
				invalidAssetPositionCounter++;
				if(invalidAssetPositionCounter < invalidPositionThreshold)
				{
					i--;
				}
			}

			
		}

	}
	
	public void drawAssets(TerrainFreeMovement terrain, Graphics2D g2d, Rectangle2D areaRectangle, int numberOfAssets, Vector2 userScale)
	{
		this.userScale.x = userScale.x;
		this.userScale.y = userScale.y;
		
		drawAssets(terrain, g2d, areaRectangle, numberOfAssets);
	}
	
	public void drawAssets(TerrainPanel panel, Graphics2D g2d, Rectangle2D areaRectangle, int numberOfAssets, Vector2 userScale)
	{
		this.userScale.x = userScale.x;
		this.userScale.y = userScale.y;
		drawAssets(panel, g2d, areaRectangle, numberOfAssets);
	}
	
}
