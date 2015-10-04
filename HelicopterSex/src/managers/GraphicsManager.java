package managers;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

public class GraphicsManager
{
	private static LinkedList<AffineTransform> backupTransforms = new LinkedList<AffineTransform>();
	private static LinkedList<Paint> backupPanits = new LinkedList<Paint>();

	public static void saveGraphicsContext(Graphics2D g2d)
	{

		backupTransforms.addLast(g2d.getTransform());
		backupPanits.addLast(g2d.getPaint());
	}

	public static void restoreGraphicsContext(Graphics2D g2d)
	{
		AffineTransform backupTransform = backupTransforms.getLast();
		if(backupTransform != null)
		{
			g2d.setTransform(backupTransform);

		}
		Paint backupPaint = backupPanits.getLast();
		if(backupPaint != null)
		{
			g2d.setPaint(backupPaint);

		}
	}

	public static void clearBackupTransforms()
	{
		if (backupTransforms.size() != 0)
		{
			backupTransforms.clear();
		}
		if (backupPanits.size() != 0)
		{
			backupPanits.clear();
		}
	}
}
