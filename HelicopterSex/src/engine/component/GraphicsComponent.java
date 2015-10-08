package engine.component;

import engine.graphics.IRenderable;
import engine.utility.Vector2;

import java.awt.Graphics2D;

public class GraphicsComponent
{
	// ******************** Fields ******************** 
	public IRenderable renderable;
	
	
	
	// ******************** Fields ********************  
	public void draw(Graphics2D g2d)
	{
		renderable.render(g2d);
	}
	
	public GraphicsComponent clone()
	{
		GraphicsComponent clone = new GraphicsComponent();
		if(renderable.getType().equals("model"))
		{
			clone.renderable = renderable.clone();
		}
		else
		{
			clone.renderable = renderable;	
		}
		return clone;
	}
	
	public Vector2 getDimensions()
	{
		return renderable.getDimensions();
	}
}
