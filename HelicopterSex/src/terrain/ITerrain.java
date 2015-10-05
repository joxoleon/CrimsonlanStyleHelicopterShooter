package terrain;

import java.awt.Graphics2D;

import camera.Camera;
import utility.Vector2;
import engine.GameTime;

public interface ITerrain
{
	public enum TerrainType {SCROLL_TERRAIN, REGULAR_TERRAIN};
	
	public void initialize();
	public void update(GameTime gameTime);
	public void draw(Graphics2D g2d, Camera camera);
	
	
	public Vector2 getDimensions();
	public TerrainType getType();
}
