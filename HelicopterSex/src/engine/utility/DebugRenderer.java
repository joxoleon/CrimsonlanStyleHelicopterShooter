package engine.utility;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import engine.camera.Camera;

public class DebugRenderer
{
	public static LinkedList<DebugVector2> debugVectors = new LinkedList<DebugVector2>();
	

	public static void draw(Graphics2D g2d, Camera camera)
	{
		for (DebugVector2 debugVector : debugVectors)
		{
			debugVector.draw(g2d, camera);
		}
		
		debugVectors.clear();
	}
	
	public static void addDebugVector(Vector2 position, Vector2 vector, Color color)
	{
		DebugVector2 dv = new DebugVector2(position, vector, color);
		debugVectors.add(dv);
	}
}
