package managers;

import java.awt.Graphics2D;

import scripts.KeepInAreaScript;
import scripts.PlayerControlScript;
import scripts.PlayerFireScript;
import scripts.PropellerScript;
import terrain.TerrainManager;
import engine.Actor;
import engine.Game;
import engine.GameTime;
import engine.camera.Camera;
import engine.camera.CrimsonlandCameraScript;
import engine.component.GraphicsComponent;
import engine.component.GunComponent;
import engine.component.PhysicsComponent;
import engine.input.Input;
import engine.input.Keys;
import engine.utility.DebugRenderer;
import engine.utility.Vector2;
import factories.GunFactory;
import factories.HelicopterFactory;
import factories.ModelFactory;
import factories.ShipFactory;

public class PlayerActorManager
{
	public static Actor playerActor;
	public static Camera camera;
	
	private static int helicopterIndex = 0;
	public static String playerHelicopterName = "helicopter03";
	

	
	private static void initialize()
	{
		playerActor = HelicopterFactory.getHelicopter(playerHelicopterName);
	}
	
	public static void initializeSurvivalState(Camera camera)
	{

		PlayerActorManager.camera = camera;
		
		initialize();

		
		Vector2 minCoordinates = new Vector2(0, 0);
		Vector2 terrainDimension = TerrainManager.getTerrainDimensions();
		Vector2 maxCooridnates = new Vector2(terrainDimension.x , terrainDimension.y);
		KeepInAreaScript keepInAreaScript= new KeepInAreaScript(minCoordinates, maxCooridnates);
		playerActor.addScriptComponent(keepInAreaScript);
		
		// Enable player rotation.
		PlayerControlScript script = (PlayerControlScript) playerActor.getScriptComponent("playerControllerScript");
		
		// TODO: remove shit!
		playerActor.position = new Vector2(800, 350);

	}
	
	public static void initializeCampaignState(Camera camera)
	{
		initialize();
		
		Vector2 minCoordinates = new Vector2(Game.game.worldDimension.width / 2, Game.game.worldDimension.height / 2);
		Vector2 terrainDimension = TerrainManager.getTerrainDimensions();
		Vector2 maxCooridnates = new Vector2(terrainDimension.x - minCoordinates.x, terrainDimension.y - minCoordinates.y);
		KeepInAreaScript keepInAreaScript= new KeepInAreaScript(minCoordinates, maxCooridnates);
		playerActor.addScriptComponent(keepInAreaScript);
		
	}
	
	public static void update(GameTime gameTime)
	{
		if(Input.isKeyPressed(Keys.B))
		{
			switchHelicopter();	
		}
		playerActor.update(gameTime);
		

	}
	
	public static void draw(Graphics2D g2d, Camera camera)
	{
		playerActor.draw(g2d, camera);
		
		playerActor.collider.draw(g2d, camera);

	}

	public static void switchHelicopter()
	{
		// Get playerActor position, rotitaion and scale.
		Vector2 playerPosition = playerActor.position;
		float playerRotation = playerActor.rotation;
		Vector2 playerScale = playerActor.scale;
		
		helicopterIndex = (helicopterIndex + 1) % HelicopterFactory.helicopterNames.size();
		playerHelicopterName = HelicopterFactory.helicopterNames.get(helicopterIndex);
		initializeSurvivalState(camera);
		CrimsonlandCameraScript camScript = (CrimsonlandCameraScript)camera.getCameraScript("crimsonlandCameraScript");
		camScript.addActor(playerActor);
		
		// Set playerActor old position, rotation and scale.
		playerActor.position = playerPosition;
		playerActor.rotation = playerRotation;
		playerActor.scale = playerScale;
	}

	public static Vector2 getPlayerPosition()
	{
		return playerActor.position.clone();
	}
}
