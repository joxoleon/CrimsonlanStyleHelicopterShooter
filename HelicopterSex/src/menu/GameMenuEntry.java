package menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import utility.StringWriter;

public class GameMenuEntry
{
	public BufferedImage image;
	public String text;
	public int textSize = 50;
	public Rectangle2D destinationRectangle;
	public boolean isSelected = false;
	public boolean isCentered = true;
	
	// Entry Variable.
	public MenuEntryVariable variable = null;
	
	public void update()
	{
		if (variable != null)
		{
			variable.update();
		}
	}
	
	public void draw(Graphics2D g2d)
	{
		
		if (image != null)
		{
			g2d.drawImage(
					image, 
					(int)destinationRectangle.getX(), (int)destinationRectangle.getY(), 
					(int)destinationRectangle.getWidth(), (int)destinationRectangle.getHeight(),
					0, 0,
					image.getWidth(), image.getHeight(),
					null);
		}
		
		if (text != null && text.isEmpty() == false)
		{
			Point point = new Point();
			
			if(isCentered == true)
			{
				point.x = (int)(destinationRectangle.getX() + destinationRectangle.getWidth() / 2);
				point.y = (int)(destinationRectangle.getY() + destinationRectangle.getHeight() / 2);
			}
			else
			{
				point = new Point((int)destinationRectangle.getX(), (int)destinationRectangle.getY());
			}
			
			
			
			Color textColor = Color.black;
			if (isSelected == true)
			{
				textColor = Color.yellow;
			}
			
			Paint oldPaint = g2d.getPaint();
			g2d.setPaint(Color.black);
//			g2d.draw(destinationRectangle);
			g2d.setPaint(oldPaint);
			
			StringWriter.writeString(g2d, text, point, isCentered, "Quartz MS", textSize, textColor);
			
			if(variable != null)
			{
				variable.setPaint(textColor);
				variable.draw(g2d);
			}
		}
	}
}
