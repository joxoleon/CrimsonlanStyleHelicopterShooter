package shooting;

import java.awt.Graphics2D;

import engine.GameTime;

public interface IShootable
{
	public void update(GameTime gameTime);
	
	public void draw(Graphics2D g2d);
	
	// Some other shit too.
}
