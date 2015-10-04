package managers;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;

public class GraphicsManager
{
	private static AffineTransform backupTransform = null;
	private static Paint backupPaint = null;
	
	public static void saveGraphicsContext(Graphics2D g2d)
	{
		backupTransform = g2d.getTransform();
		backupPaint = g2d.getPaint();
	}
	
	public static void restoreGraphicsContext(Graphics2D g2d)
	{
		g2d.setTransform(backupTransform);
		g2d.setPaint(backupPaint);
		backupTransform = null;
	}
}
