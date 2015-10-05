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
import scripts.PropellerScript;
import shooting.ShotManager;
import shooting.SimpleShot;
import terrain.TerrainManager;
import utility.Vector2;
import engine.Actor;
import engine.Game;
import engine.GameTime;
import factories.GunFactory;
import factories.ModelFactory;
import input.Input;
import input.Keys;

public class PlayCampaignGameState
extends GameState
{
	long backgroundSongID;
	LinkedList<Long> playingSongsIDs = new LinkedList<Long>();
	
	long score;
	long secondsPlayed;
	
	public Camera mainCamera = new Camera();
	

	
	// Main song fields.
	public String[] songNames = {"archangel", "blackheart", "militaryIndustrial", "insideTheFire"};
	public int currentSong = 3;
	
	public Actor chopper;
	public SimpleShot simple;
	
	public boolean drawShit = false;
	
	public PlayCampaignGameState()
	{

	}

	@Override
	public void enterState()
	{
		System.out.println("Enter single player state.");

				
		backgroundSongID = AudioManager.play(songNames[currentSong], true);
		playingSongsIDs.add(backgroundSongID);
		
		
		
		// Testing shit.
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
		mainCamera.position.x = Game.game.worldDimension.width / 2;
		mainCamera.position.y = Game.game.worldDimension.height / 2;

		TerrainManager.setScrollDownTerrain("tSnow01");

		// Initialization
		initializeManagers();
		FollowActorScript cameraScript = new FollowActorScript();
		cameraScript.addActor(chopper);
		mainCamera.isDrawCamera = false;
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
			testingChopperShit(gameTime);
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
		chopper.draw(g2d, mainCamera);
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


	private void testingChopperShit(GameTime gameTime)
	{

		chopper.update(gameTime);
		
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
