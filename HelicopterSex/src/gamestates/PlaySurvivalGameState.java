package gamestates;

import java.awt.Graphics2D;
import java.util.LinkedList;

import camera.Camera;
import camera.CrimsonlandCameraScript;
import camera.FollowActorScript;
import managers.AudioManager;
import managers.GraphicsManager;
import managers.PauseManager;
import managers.PlayerActorManager;
import managers.SlowMotionManager;
import component.GraphicsComponent;
import component.GunComponent;
import component.PhysicsComponent;
import scripts.KeepInAreaScript;
import scripts.PlayerControlScript;
import scripts.PlayerFireScript;
import scripts.PropellerScript;
import shooting.ShotManager;
import terrain.TerrainManager;
import utility.Vector2;
import engine.Actor;
import engine.Game;
import engine.GameTime;
import factories.ActorScriptFactory;
import factories.GunFactory;
import factories.HelicopterFactory;
import factories.ModelFactory;
import factories.ShipFactory;
import input.Input;
import input.Keys;

public class PlaySurvivalGameState
extends GameState
{
	long backgroundSongID;
	LinkedList<Long> playingSongsIDs = new LinkedList<Long>();
	
	long score;
	long secondsPlayed;
	public Camera mainCamera;
	

	
	// Main song fields.
	public String[] songNames = {"archangel", "blackheart", "militaryIndustrial", "insideTheFire"};
	public int currentSong = 3;
	
	
	
	public PlaySurvivalGameState()
	{

	}

	@Override
	public void enterState()
	{
		System.out.println("Enter Survival game state.");
		
		// Set song.
		backgroundSongID = AudioManager.play(songNames[currentSong], true);
		playingSongsIDs.add(backgroundSongID);
		
		// Set terrain.
		TerrainManager.setFreeMovementTerrain("tSnow01");
		
		// Create camera.
		mainCamera = new Camera();
		
		// Initialize managers. ***** (Player Manager, Pause, Slomo, etc...)
		initializeManagersAndFactories(mainCamera);

		Vector2 minCoordinates = new Vector2(Game.game.worldDimension.width / 2, Game.game.worldDimension.height / 2);
		Vector2 terrainDimension = TerrainManager.getTerrainDimensions();
		Vector2 maxCoordinates = new Vector2(terrainDimension.x - minCoordinates.x, terrainDimension.y - minCoordinates.y);
		CrimsonlandCameraScript crimsonlandScript = new CrimsonlandCameraScript(minCoordinates, maxCoordinates);
		crimsonlandScript.addActor(PlayerActorManager.playerActor);
		mainCamera.addScriptComponent(crimsonlandScript);
		
		

	}

	@Override
	public void update(GameTime gameTime)
	{
		handleUpdateInput();
		
		PauseManager.update(gameTime);
		SlowMotionManager.update(gameTime);
		
		if (PauseManager.isPaused == false)
		{
			destroyAndFilter();
			TerrainManager.update(gameTime);
			PlayerActorManager.update(gameTime);
			ShotManager.update(gameTime);
			mainCamera.update(gameTime);

		}
	}
	
	@Override
	public void draw(Graphics2D g2d) 
	{
		
		// Draw Terrain
		
		// Set the starting point 
		GraphicsManager.saveGraphicsContext(g2d);
		g2d.translate(mainCamera.cameraWidth / 2, mainCamera.cameraHeight / 2);
		g2d.rotate(-mainCamera.rotation);
		g2d.scale(mainCamera.scale.x, mainCamera.scale.y);
		g2d.translate(-mainCamera.cameraWidth / 2, -mainCamera.cameraHeight / 2);
		
		TerrainManager.draw(g2d, mainCamera);
		PlayerActorManager.draw(g2d, mainCamera);
		ShotManager.draw(g2d, mainCamera);
		mainCamera.draw(g2d);

		// Restore graphics context before drawing the GUI and post processing.
		GraphicsManager.restoreGraphicsContext(g2d);
		// Draw GUI
		SlowMotionManager.draw(g2d);
		PauseManager.draw(g2d);
		
	}

	@Override
	public void exitState()
	{
		System.out.println("Exit single player state.");
		AudioManager.stop(backgroundSongID);
		playingSongsIDs.remove(backgroundSongID);
	}

	
	
	
	private void handleUpdateInput()
	{
		if (Input.isKeyPressed(Keys.Escape))
		{
			Game.game.exitGame();
		}
		
		if(Input.isKeyPressed(Keys.T))
		{
			SlowMotionManager.toggleTimeScale(playingSongsIDs);
		}
		
		if (Input.isKeyPressed(Keys.P))
		{
			PauseManager.togglePause(playingSongsIDs);
		}
		
		if(Input.isKeyPressed(Keys.Back))
		{
			Game.game.gameStateMachine.changeState(Game.game.gameOverState);
		}
		
	}

	private void destroyAndFilter()
	{
		ShotManager.filterShots();
	}

	
	private void initializeManagersAndFactories(Camera camera)
	{
		ActorScriptFactory.initialize();
		ShipFactory.initialize();
		HelicopterFactory.initialize();
		PauseManager.initialize();
		SlowMotionManager.initialize();
		PlayerActorManager.initializeSurvivalState(camera);

	}
}
