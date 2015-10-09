package engine.utility;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import managers.GraphicsManager;
import engine.camera.Camera;

public class DebugVector2
{
	public Vector2 position;
	public Vector2 vector;
	public Color vectorColor = Color.red;
	public BasicStroke stroke = new BasicStroke(5);
	
	public DebugVector2(Vector2 position, Vector2 vector, Color color)
	{
		this.position = position;
		this.vector = vector;
		this.vectorColor = color;
	}
	

	public void draw(Graphics2D g2d, Camera camera)
	{
		GraphicsManager.saveGraphicsContext(g2d);

//		Vector2 renderPosition = new Vector2(
//				position.x - (camera.position.x - camera.rotatedCornerVector.x),
//				position.y - (camera.position.y - camera.rotatedCornerVector.y)						
//						);
//		g2d.translate(renderPosition.x, renderPosition.y);
		g2d.setColor(vectorColor);
		g2d.setStroke(stroke);
		
		g2d.drawLine((int)position.x, (int) position.y, (int)vector.x, (int)vector.y);
		
		GraphicsManager.restoreGraphicsContext(g2d);
	}
}
