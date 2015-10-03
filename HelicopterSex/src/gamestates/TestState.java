package gamestates;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;

import camera.Camera;
import terrain.TerrainFreeMovement;
import utility.Vector2;
import engine.Game;
import engine.GameTime;
import input.Input;
import input.Keys;

public class TestState 
extends GameState
{

	TerrainFreeMovement terrain = new TerrainFreeMovement("firstFreeMovementTerrain");
	Camera camera = new Camera();

	public TestState()
	{
		camera.position = new Vector2(900, 900);
		terrain.terrainTileName = "snowTile01";

	}

	@Override
	public void enterState()
	{
		terrain.initialize();
	}

	@Override
	public void update(GameTime gameTime)
	{
		handleUpdateInput();

	}

	@Override
	public void draw(Graphics2D g2d)
	{

		// Draw Terrain
		terrain.draw(g2d, camera);

	}

	@Override
	public void exitState()
	{

	}

	public void drawGUIPanel(Graphics2D g2d, Paint paint, Rectangle2D guiPanelDstRect)
	{

	}

	private void handleUpdateInput()
	{
		if (Input.isKeyPressed(Keys.Escape))
		{
			Game.game.exitGame();
		}
		
		
		int terrainSpeed = 10;
		
		if(Input.isKeyDown(Keys.Up))
		{
			Vector2 movement = new Vector2(0, -1);
			movement.rotate(-camera.rotation);
			movement.mul(terrainSpeed);
			camera.position.add(movement);
		}
		
		if(Input.isKeyDown(Keys.Down))
		{
			camera.position.y += terrainSpeed;
		}
		
		if(Input.isKeyDown(Keys.Left))
		{
			camera.position.x -= terrainSpeed;
		}
		
		if(Input.isKeyDown(Keys.Right))
		{
			camera.position.x += terrainSpeed;
		}
		
		if(Input.isKeyDown(Keys.E))
		{
			camera.rotation -= 0.05;
		}

		if(Input.isKeyDown(Keys.Q))
		{
			camera.rotation += 0.05;
		}
	}

}
