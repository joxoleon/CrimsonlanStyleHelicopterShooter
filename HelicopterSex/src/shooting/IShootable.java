package shooting;

import java.awt.Graphics2D;

import engine.GameTime;
import engine.camera.Camera;

public interface IShootable
{
	public void update(GameTime gameTime);
	
	public void draw(Graphics2D g2d, Camera camera);
	
	// Some other shit too.
}
