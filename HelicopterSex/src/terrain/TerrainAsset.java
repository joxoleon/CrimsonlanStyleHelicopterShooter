package terrain;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class TerrainAsset
{
	// ******************** Fields ******************** 
	// Name.
	public String name;
	
	// Image details.
	BufferedImage assetImage;
	public int dstWidth;
	public int dstHeight;
	
	
	// ******************** Constructors ******************** 
	public TerrainAsset(String imageName, BufferedImage assetImage, int dstWidth, int dstHeight)
	{
		this.name = imageName;
		this.assetImage = assetImage;
		this.dstWidth = dstWidth;
		this.dstHeight = dstHeight;
	}

	
	
	// ******************** Methods ******************** 
	private void drawAsset(Graphics2D g2d, Rectangle2D dstRect)
	{
		g2d.drawImage(
				assetImage,
				(int)dstRect.getX(), (int)dstRect.getY(), (int)dstRect.getX() + dstWidth, (int)(dstRect.getY() + dstHeight),
				0, 0, assetImage.getWidth(), assetImage.getHeight(),
				null				
				);
	}
	
	private void drawAsset(Graphics2D g2d, Point2D position, boolean isCentered)
	{
		int x = (int) (position.getX());
		int y = (int) (position.getY());
		if(isCentered == true)
		{
			x -= (int) (dstWidth / 2);
			y -= (int) (dstHeight / 2);
		}
		
		Rectangle2D dstRect = new Rectangle2D.Float(x, y, dstWidth, dstHeight);
		drawAsset(g2d, dstRect);
	}
	
	public void drawAssets(TerrainPanel panel, Graphics2D g2d, Rectangle2D areaRectangle, int numberOfAssets)
	{
		int minX = (int) (areaRectangle.getX() + dstWidth / 2);
		int maxX = (int) (minX + areaRectangle.getWidth() - dstWidth);
		
		int minY = (int) (areaRectangle.getY() + dstHeight / 2);
		int maxY = (int) (minY + areaRectangle.getHeight()- dstHeight);
		
		for (int i = 0; i < numberOfAssets; i++)
		{
			int x = (int) (minX + Math.random() * (maxX - minX));
			int y = (int) (minY + Math.random() * (maxY - minY));
			
			if(panel.isValidAssetPosition(x, y, dstWidth, dstHeight) == true)
			{
				panel.addAssetPosition(x, y);
				Point2D point = new Point2D.Float(x, y);
				drawAsset(g2d, point, true);
			}
			else
			{
				i--;
			}

			
		}

	}
	
	public void drawAssets(TerrainFreeMovement terrain, Graphics2D g2d, Rectangle2D areaRectangle, int numberOfAssets)
	{
		int minX = (int) (areaRectangle.getX() + dstWidth / 2);
		int maxX = (int) (minX + areaRectangle.getWidth() - dstWidth);
		
		int minY = (int) (areaRectangle.getY() + dstHeight / 2);
		int maxY = (int) (minY + areaRectangle.getHeight()- dstHeight);
		
		for (int i = 0; i < numberOfAssets; i++)
		{
			int x = (int) (minX + Math.random() * (maxX - minX));
			int y = (int) (minY + Math.random() * (maxY - minY));
			
			if(terrain.isValidAssetPosition(x, y, dstWidth, dstHeight) == true)
			{
				terrain.addAssetPosition(x, y);
				Point2D point = new Point2D.Float(x, y);
				drawAsset(g2d, point, true);
			}
			else
			{
				i--;
			}

			
		}

	}
	
	public void drawAssets(TerrainPanel panel, Graphics2D g2d, Rectangle2D areaRectangle, int numberOfAssets, int dstWidth, int dstHeight)
	{
		this.dstWidth = dstWidth;
		this.dstHeight = dstHeight;
		drawAssets(panel, g2d, areaRectangle, numberOfAssets);
	}
	
}
