package gamestates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;

import component.GraphicsComponent;
import component.GunComponent;
import component.PhysicsComponent;
import camera.Camera;
import camera.FollowActorScript;
import scripts.PlayerControlScript;
import scripts.PropellerScript;
import terrain.TerrainFreeMovement;
import utility.Vector2;
import engine.Actor;
import engine.Game;
import engine.GameTime;
import factories.GunFactory;
import factories.ModelFactory;
import input.Input;
import input.Keys;

public class TestState 
extends GameState
{

	TerrainFreeMovement terrain = new TerrainFreeMovement("firstFreeMovementTerrain");
	Camera mainCamera = new Camera();
	Actor chopper;

	public TestState()
	{
		mainCamera.position = new Vector2(1920 / 2, 1080 / 2);
		mainCamera.rotation = 0;
		terrain.terrainTileName = "snowTile01";

	}

	@Override
	public void enterState()
	{
		terrain.initialize();
		
		chopper = new Actor();
		GraphicsComponent gc = new GraphicsComponent();
		gc.renderable = ModelFactory.getInstance().getFlyweightModel("helicopter03");
		chopper.position = new Vector2(500,500);
		chopper.graphicsComponent = gc;
		PhysicsComponent pc = new PhysicsComponent();
		chopper.addBasicComponent(pc);
		chopper.addScriptComponent(new PropellerScript());

		GunComponent gunComponent = new GunComponent();
		gunComponent.gunSlots = GunFactory.getGunSlotCombination("dekiPantelic11");

		chopper.addBasicComponent(gunComponent);

		chopper.addScriptComponent(new PlayerControlScript());
		
//		FollowActorScript cameraScript = new FollowActorScript();
//		cameraScript.addActor(chopper);
//		mainCamera.addScriptComponent(cameraScript);
	}

	@Override
	public void update(GameTime gameTime)
	{
		handleUpdateInput();
		chopper.update(gameTime);
		mainCamera.update(gameTime);
		System.out.println("chopper position = " + chopper.position + " chopper rotation = " + chopper.rotation);
		System.out.println("camera position = " + mainCamera.position + " camera rotation = " + mainCamera.rotation);

	}

	@Override
	public void draw(Graphics2D g2d)
	{
		// Set the starting point 
		g2d.translate(mainCamera.cameraWidth / 2, mainCamera.cameraHeight / 2);
		g2d.rotate(mainCamera.rotation);
		g2d.scale(mainCamera.scale.x, mainCamera.scale.y);
		g2d.translate(-mainCamera.cameraWidth / 2, -mainCamera.cameraHeight / 2);
		

		// Draw Terrain
		terrain.draw(g2d, mainCamera);
//		chopper.draw(g2d, mainCamera);
		g2d.setColor(Color.black);
		g2d.fillRect(1920 / 2 - 250, 1080 / 2 - 250, 100, 100);
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
		
		
		int cameraSpeed = 10;
		
		if(Input.isKeyDown(Keys.Up))
		{
			Vector2 movement = new Vector2(0, -1);
			movement.rotate(-mainCamera.rotation);
			movement.mul(cameraSpeed);
			mainCamera.position.add(movement);
		}
		
		if(Input.isKeyDown(Keys.Down))
		{
			Vector2 movement = new Vector2(0, 1);
			movement.rotate(-mainCamera.rotation);
			movement.mul(cameraSpeed);
			mainCamera.position.y += cameraSpeed;
		}
		
		if(Input.isKeyDown(Keys.Left))
		{
			Vector2 movement = new Vector2(-1, 0);
			movement.rotate(-mainCamera.rotation);
			movement.mul(cameraSpeed);
			mainCamera.position.x -= cameraSpeed;
		}
		
		if(Input.isKeyDown(Keys.Right))
		{
			Vector2 movement = new Vector2(1, 0);
			movement.rotate(-mainCamera.rotation);
			movement.mul(cameraSpeed);
			mainCamera.position.x += cameraSpeed;
		}
		
		if(Input.isKeyDown(Keys.E))
		{
			mainCamera.rotation -= 0.05;
		}

		if(Input.isKeyDown(Keys.Q))
		{
			mainCamera.rotation += 0.05;
		}
	}

}
