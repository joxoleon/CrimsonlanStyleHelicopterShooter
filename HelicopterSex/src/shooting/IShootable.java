package shooting;

import java.awt.Graphics2D;

import camera.Camera;
import engine.GameTime;

public interface IShootable
{
	public void update(GameTime gameTime);
	
	public void draw(Graphics2D g2d, Camera camera);
	
	// Some other shit too.
}
