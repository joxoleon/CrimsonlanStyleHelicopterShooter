package managers;

import java.awt.Graphics2D;

import camera.Camera;
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
import factories.ModelFactory;

public class PlayerManager
{
	public static Actor playerActor;
	
	private static void initialize()
	{
		playerActor = new Actor();
		
		// Set actor position.
		Vector2 terrainDimension = TerrainManager.getTerrainDimensions();
		playerActor.position.x = terrainDimension.x / 2;
		playerActor.position.y = terrainDimension.y / 2;
		
		// Add graphics component.
		GraphicsComponent gc = new GraphicsComponent();
		gc.renderable = ModelFactory.getInstance().getFlyweightModel("helicopter03");
		playerActor.graphicsComponent = gc;
		
		// Add physics component.
		PhysicsComponent pc = new PhysicsComponent();
		playerActor.addBasicComponent(pc);
		
		// Add gun component.
		GunComponent gunComponent = new GunComponent();
		gunComponent.gunSlots = GunFactory.getGunSlotCombination("dekiPantelic11");
		playerActor.addBasicComponent(gunComponent);
		
		// Add script components ****
		// Propeller script.
		playerActor.addScriptComponent(new PropellerScript());
		// Second pass script for firing.
		PlayerFireScript fireScript = new PlayerFireScript();
		playerActor.addScriptComponent(fireScript);
		// Player control script.
		playerActor.addScriptComponent(new PlayerControlScript());

	}
	
	public static void initializeSurvivalState(Camera camera)
	{
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
		playerActor.update(gameTime);
	}
	
	public static void draw(Graphics2D g2d, Camera camera)
	{
		playerActor.draw(g2d, camera);
	}
}
