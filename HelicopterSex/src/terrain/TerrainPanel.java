package terrain;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class TerrainPanel
{
	// ******************** Fields ******************** 
	// Dimension Fields.
	public int width = 1920;
	public int height = 1080;
	public BufferedImage panelImage;
	
	// Asset fields.
	public LinkedList<Point> assetPositions = new LinkedList<Point>();
	
	
	
	// ******************** Methods ********************
	// Helper Methods.
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
}
