package shooting;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import utility.Vector2;

public class ShotSprite
{
	public BufferedImage shotImage;
	public String name;
	public Vector2 scale = new Vector2(1, 1);
	
	public void draw(Graphics2D g2d)
	{
		g2d.scale(scale.x, scale.y);
		int offsetX = (int) (shotImage.getWidth() / 2.0f);
		int offsetY = (int) (shotImage.getHeight() / 2.0f);
		g2d.drawImage(shotImage, -offsetX, -offsetY, null);
	}
	
}
