package gamestates;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import camera.Camera;
import managers.AudioManager;
import managers.PauseManager;
import managers.SlowMotionManager;
import component.GraphicsComponent;
import component.GunComponent;
import component.PhysicsComponent;
import scripts.PlayerControlScript;
import scripts.PropellerScript;
import shooting.ShotManager;
import shooting.SimpleShot;
import terrain.TerrainScrollDown;
import utility.Vector2;
import content.Content;
import engine.Actor;
import engine.Game;
import engine.GameTime;
import factories.GunFactory;
import factories.ModelFactory;
import input.Input;
import input.Keys;

public class PlaySingleGameState
extends GameState
{
	long backgroundSongID;
	LinkedList<Long> playingSongsIDs = new LinkedList<Long>();
	
	long score;
	long secondsPlayed;
	
	
	TerrainScrollDown terrain;
	private String startinTerrainName = "tSnow01";
	
	public Camera mainCamera = new Camera();
	
	

	
	// Main song fields.
	public String[] songNames = {"archangel", "blackheart", "militaryIndustrial", "insideTheFire"};
	public int currentSong = 3;
	
	public Actor chopper;
	public SimpleShot simple;
	
	public boolean drawShit = false;
	
	public PlaySingleGameState()
	{

	}

	@Override
	public void enterState()
	{
		System.out.println("Enter single player state.");
		terrain = Content.terrains.get(startinTerrainName);
		terrain.initialize();
				
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

		

		// Initialization
		initializeManagers();
	}

	@Override
	public void update(GameTime gameTime)
	{
		handleUpdateInput();
		
		PauseManager.update(gameTime);
		SlowMotionManager.update(gameTime);
		
		if (PauseManager.isPaused == false)
		{
			mainCamera.update(gameTime);
			destroyAndFilter();
			terrain.update(gameTime);
			testingChopperShit(gameTime);
			ShotManager.update(gameTime);
		}
	}
	
	@Override
	public void draw(Graphics2D g2d) 
	{
		
		// Draw Terrain
		
		terrain.draw(g2d);
		
		chopper.draw(g2d);
				
		ShotManager.draw(g2d);
		
		mainCamera.draw(g2d);
		
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
		
		
		if(Input.isKeyPressed(Keys.NumPad1))
		{
			terrain = Content.terrains.get("tForest01");
			terrain.initialize();
		}
		
		if(Input.isKeyPressed(Keys.NumPad2))
		{
			terrain = Content.terrains.get("tDesert01");
			terrain.initialize();
		}
		
		if(Input.isKeyPressed(Keys.NumPad3))
		{
			terrain = Content.terrains.get("tSnow01");
			terrain.initialize();
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
