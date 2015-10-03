package graphics;

import java.awt.Graphics2D;

public interface IRenderable
{
	public void render(Graphics2D g2d);
	
	public Model getModel();
}
