package engine.graphics;

import java.awt.Graphics2D;

import engine.utility.Vector2;

public interface IRenderable
{
	public void render(Graphics2D g2d);
	
	public Model getModel();
	
	public String getType();
	
	public IRenderable clone();
	
	public Vector2 getDimensions();
}
