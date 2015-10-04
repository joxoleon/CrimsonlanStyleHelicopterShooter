package component;

import graphics.IRenderable;
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
}
