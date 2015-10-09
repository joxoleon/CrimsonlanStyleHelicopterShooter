package engine.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.utility.Vector2;

public class SpriteSheet
{
	
	protected BufferedImage[] images;
	protected String name;
	
	public SpriteSheet(BufferedImage image, int horizontal, int vertical, int numOfImages)
	{		
		images = new BufferedImage[numOfImages];
		
		int imageWidth = image.getWidth() / horizontal;
		int imageHeight = image.getHeight() / vertical;
		
		for (int i = 0; i < vertical; i++)
		{
			for (int j = 0; j < horizontal; j++)
			{
				if((i * horizontal + j) >= numOfImages)
				{
					continue;
				}
				
				images[i * horizontal + j] = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
				
				Graphics2D g2d = (Graphics2D) images[i * horizontal + j].getGraphics();
				
				int srcx1 = imageWidth * j;
				int srcy1 = imageHeight * i;
				int srcx2 = srcx1 + imageWidth;
				int srcy2 = srcy1 + imageHeight;
								
				g2d.drawImage(
						image,
						0, 0, imageWidth, imageHeight,
						srcx1, srcy1, srcx2, srcy2,
						null
						);
			
			}
		}
	}
	
	public int numOfSprites()
	{
		return images.length;
	}
	

	public void draw(Graphics2D g2d, int index)
	{
		int offsetX = (int) (images[index].getWidth() / 2.0f);
		int offsetY = (int) (images[index].getHeight() / 2.0f);
		
		g2d.drawImage(images[index], (int)-offsetX, (int)-offsetY, null);
		
	}
	
	public String getName()
	{
		return name;
	}

}
