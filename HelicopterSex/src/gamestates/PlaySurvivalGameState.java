package gamestates;

import java.awt.Graphics2D;
import java.util.LinkedList;

import camera.Camera;
import camera.FollowActorScript;
import managers.AudioManager;
import managers.GraphicsManager;
import managers.PauseManager;
import managers.SlowMotionManager;
import component.GraphicsComponent;
import component.GunComponent;
import component.PhysicsComponent;
import scripts.PlayerControlScript;
import scripts.PlayerFireScript;
import scripts.PropellerScript;
import shooting.ShotManager;
import terrain.TerrainManager;
import utility.Vector2;
import engine.Actor;
import engine.Game;
import engine.GameTime;
import factories.GunFactory;
import factories.ModelFactory;
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
	
	public Actor playerActor;
	
	
	public PlaySurvivalGameState()
	{

	}

	@Override
	public void enterState()
	{
		System.out.println("Enter Survival game state.");

				
		backgroundSongID = AudioManager.play(songNames[currentSong], true);
		playingSongsIDs.add(backgroundSongID);
		
		
		
		// Testing shit.
		playerActor = new Actor();
		GraphicsComponent gc = new GraphicsComponent();
		gc.renderable = ModelFactory.getInstance().getFlyweightModel("helicopter03");
		playerActor.position = new Vector2(500,500);
		playerActor.graphicsComponent = gc;
		PhysicsComponent pc = new PhysicsComponent();
		playerActor.addBasicComponent(pc);
		playerActor.addScriptComponent(new PropellerScript());

		GunComponent gunComponent = new GunComponent();
		
		gunComponent.gunSlots = GunFactory.getGunSlotCombination("dekiPantelic11");

		playerActor.addBasicComponent(gunComponent);
		
		PlayerFireScript fireScript = new PlayerFireScript();
		playerActor.addScriptComponent(fireScript);
		

		playerActor.addScriptComponent(new PlayerControlScript());
		mainCamera = new Camera();
		mainCamera.position.x = Game.game.worldDimension.width / 2;
		mainCamera.position.y = Game.game.worldDimension.height / 2;

		TerrainManager.setFreeMovementTerrain("tSnow01");

		// Initialization
		initializeManagers();
		FollowActorScript cameraScript = new FollowActorScript();
		cameraScript.addActor(playerActor);

		mainCamera.isDrawCamera = false;
		cameraScript.isFollowRotation = false;
		mainCamera.addScriptComponent(cameraScript);
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
			playerActor.update(gameTime);
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
		playerActor.draw(g2d, mainCamera);
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
			System.out.println("toggle");
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

	
	private void initializeManagers()
	{
		PauseManager.initialize();
		SlowMotionManager.initialize();
	}
}
