package menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import utility.StringWriter;

public class MenuEntryVariable
{
	// Fields.
	public Rectangle2D destinationRectangle;
	boolean isCentered = false;
	int fontSize = 50;
	Color textColor;
	protected String text = "";
	
	public void setPaint(Color color)
	{
		this.textColor = color;
	}
	public void update()
	{
		
	}
	public void draw(Graphics2D g2d)
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
		
		StringWriter.writeString(g2d, text, point, isCentered, "Quartz MS", fontSize, textColor);
	}
}
