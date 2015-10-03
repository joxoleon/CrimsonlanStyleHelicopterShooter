package utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class StringWriter
{
	private static Map<String, Font> fonts = new HashMap<String, Font>();
	private static Point startPoint = new Point();
	
	public static void writeString(
			Graphics2D g2d, 
			String text, 
			Point origin,
			boolean isOriginCenter,
			String fontName, 
			int size,
			Color color)
	{
		String key = fontName + size;
		
		Font f = fonts.get(key);
		if (f == null)
		{
			f = new Font(fontName, Font.PLAIN, size);
			fonts.put(key, f);
		}
		
		FontMetrics fm = g2d.getFontMetrics(f);
		
		int width = fm.stringWidth(text);
		int height = fm.getHeight();
		
		Paint oldPaint = g2d.getPaint();
		g2d.setPaint(color);
		
		Font oldFont = g2d.getFont();
		g2d.setFont(f);
		
		startPoint.setLocation(origin);
		if (isOriginCenter)
		{
			startPoint.x -= width / 2;
			startPoint.y += height / 3.3;	// Magic number.
		}
		
		g2d.drawString(
				text,
				startPoint.x,
				startPoint.y
				);
		
		g2d.setFont(oldFont);
		g2d.setPaint(oldPaint);
	}
	
	public static void writeStrings(
		Graphics2D g2d,
		LinkedList<String> strings,
		Point origin,
		int offsetBetweenLines,
		String fontName,
		int size,
		Color color)
	{
		String key = fontName + size;
		
		Font f = fonts.get(key);
		if (f == null)
		{
			f = new Font(fontName, Font.PLAIN, size);
			fonts.put(key, f);
		}
		
		FontMetrics fm = g2d.getFontMetrics(f);
		int height = fm.getHeight();
		
		
		Point orig = new Point(origin);
		
		for (String string : strings)
		{
			StringWriter.writeString(g2d, string, orig, false, "Quartz MS", 50, Color.yellow);
			orig.y += height + offsetBetweenLines;
		}
	}
	
	
}
