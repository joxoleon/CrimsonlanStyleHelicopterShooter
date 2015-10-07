package managers;

import input.Input;
import input.Keys;

import java.awt.Graphics2D;

import camera.Camera;
import camera.CrimsonlandCameraScript;
import scripts.KeepInAreaScript;
import scripts.PlayerControlScript;
import scripts.PlayerFireScript;
import scripts.PropellerScript;
import terrain.TerrainManager;
import utility.Vector2;
import component.GraphicsComponent;
import component.GunComponent;
import component.PhysicsComponent;
import engine.Actor;
import engine.Game;
import engine.GameTime;
import factories.GunFactory;
import factories.HelicopterFactory;
import factories.ModelFactory;
import factories.ShipFactory;

public class PlayerActorManager
{
	public static Actor playerActor;
	public static Camera camera;
	
	private static int helicopterIndex = 0;
	public static String playerHelicopterName = "helicopter01";
	

	
	private static void initialize()
	{
		playerActor = HelicopterFactory.getHelicopter(playerHelicopterName);
//		playerActor = ShipFactory.getShip("enemy06");
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
		script.hasRotation = true;
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
