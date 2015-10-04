package camera;

import java.awt.Graphics2D;

import engine.GameTime;


public interface CameraScript
{
	public void onAttach(Camera camera);
	public void update(GameTime gameTime);	
	public void draw(Graphics2D g2d);
}