package terrain;

import java.awt.Graphics2D;

import engine.GameTime;
import engine.camera.Camera;
import engine.utility.Vector2;

public interface ITerrain
{
	public enum TerrainType {SCROLL_TERRAIN, REGULAR_TERRAIN};
	
	public void initialize();
	public void update(GameTime gameTime);
	public void draw(Graphics2D g2d, Camera camera);
	
	
	public Vector2 getDimensions();
	public TerrainType getType();
}
