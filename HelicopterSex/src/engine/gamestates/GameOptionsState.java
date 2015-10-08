package engine.gamestates;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import managers.AudioManager;
import content.Content;
import engine.Game;
import engine.GameTime;
import engine.input.Input;
import engine.input.Keys;
import engine.menu.GameMenuEntry;
import engine.menu.MenuEntryVariable;

public class GameOptionsState
extends GameState
{
	List<GameMenuEntry> entries = new ArrayList<GameMenuEntry>();
	int selectedEntry;
	
	// Entry fields.
	private Point entryStartPoint = new Point(300, 300);
	private int entryOffset = 90;
	
	// Entry variable fields.
	private int menuVariableWidthOffset = 900;
	
	// Difficulty fields.
	public String[] difficultyLevels = {"Extreme fagit", "Light Fagit", "Fagit", "Noob", 
			"Medium", "Hard", "Are you sure", "Anal"};
	
	// Song Fields.
	private long currentlyPlayingSongID = -1;
	
	// Volume fields.
	public String volumeBars = "||||||||||||||||||||";
	
	public GameOptionsState()
	{
		backgroundPanel = Content.backgroundPanels.get("options");
		createEntries();
	}

	@Override
	public void enterState()
	{
		System.out.println("Enter game options state.");
	}

	@Override
	public void update(GameTime gameTime)
	{
		if (Input.isKeyPressed(Keys.Escape))
		{
			Game.game.gameStateMachine.changeState(Game.game.mainMenuState);
		}
		
		// Selecte entry.
		entries.get(selectedEntry).isSelected = false;
		
		if (Input.isKeyPressed(Keys.Up))
		{
			selectedEntry = (selectedEntry + entries.size() - 1) % entries.size();
			AudioManager.playOnceNoInterrupt("blop02");
		}
		
		if (Input.isKeyPressed(Keys.Down))
		{
			selectedEntry = (selectedEntry + 1) % entries.size();
			AudioManager.playOnceNoInterrupt("blop02");
		}
		
		if (Input.isKeyPressed(Keys.Left)  ||
			Input.isKeyPressed(Keys.Right) ||
			Input.isKeyPressed(Keys.Enter))
		{
			handleEntryActions();
		}
		
		
		for (GameMenuEntry entry : entries)
		{
			entry.update();
		}
		
		entries.get(selectedEntry).isSelected = true;

	}
	
	@Override
	public void draw(Graphics2D g2d)
	{
		g2d.drawImage(
				backgroundPanel.backgroundImage, 
				0, 0, 
				Game.game.worldDimension.width, Game.game.worldDimension.height, 
				0, 0, 
				backgroundPanel.backgroundImage.getWidth(), backgroundPanel.backgroundImage.getHeight(), null);
		
		for (GameMenuEntry gameMenuEntry : entries)
		{
			gameMenuEntry.draw(g2d);
		}
	}

	@Override
	public void exitState()
	{
		System.out.println("Exit game options state.");
		if(currentlyPlayingSongID != -1)
		{
			AudioManager.stop(currentlyPlayingSongID);
		}
	}

	private void createEntries()
	{
		GameMenuEntry entry;
		
		
		// Difficulty
		entry = new GameMenuEntry();
		entry.destinationRectangle = new Rectangle2D.Float(entryStartPoint.x, entryStartPoint.y, 400, 50);
		entry.text = "Difficulty";
		entry.isCentered = false;
		entries.add(entry);
		
		// Add variables to Difficulty
		class DifficultyVariable extends MenuEntryVariable
		{
			public void update()
			{
				super.text = difficultyLevels[Game.game.difficultyLevel];
			}
		}
		DifficultyVariable var = new DifficultyVariable();
		var.destinationRectangle = new Rectangle2D.Float(entryStartPoint.x + menuVariableWidthOffset, entryStartPoint.y, 400, 50);
		entry.variable = var;
		
		
		
		// Volume
		entry = new GameMenuEntry();
		entry.destinationRectangle = new Rectangle2D.Float(entryStartPoint.x, entryStartPoint.y + 1 * entryOffset, 400, 50);
		entry.text = "Volume";
		entry.isCentered = false;
		entries.add(entry);
		
		// Add variables to Volume
		class VolumeVariable extends MenuEntryVariable
		{
			public void update()
			{
				double masterVolume = AudioManager.getMasterVolume();
				int endIndex = (int)Math.round(masterVolume * volumeBars.length());
				text = volumeBars.substring(0, endIndex);
			}
		}
		VolumeVariable volumeVar = new VolumeVariable();
		volumeVar.destinationRectangle = new Rectangle2D.Float(entryStartPoint.x + menuVariableWidthOffset, entryStartPoint.y + 1 * entryOffset, 400, 50);
		entry.variable = volumeVar;
		
		
		
		// Game Song
		entry = new GameMenuEntry();
		entry.destinationRectangle = new Rectangle2D.Float(entryStartPoint.x, entryStartPoint.y + 2 * entryOffset, 400, 50);
		entry.text = "Game song";
		entry.isCentered = false;
		entries.add(entry);
		
		// Add variables to game song.
		class SongVariable extends MenuEntryVariable
		{
			public void update()
			{
				text = Game.game.playCampaignGameState.songNames[Game.game.playCampaignGameState.currentSong];
			}
		}
		SongVariable songVar = new SongVariable();
		songVar.destinationRectangle = new Rectangle2D.Float(entryStartPoint.x + menuVariableWidthOffset, entryStartPoint.y + 2 * entryOffset, 400, 50);
		entry.variable = songVar;
		
		// Back
		entry = new GameMenuEntry();
		entry.destinationRectangle = new Rectangle2D.Float(entryStartPoint.x, entryStartPoint.y + 3 * entryOffset, 400, 50);
		entry.text = "Back";
		entry.isCentered = false;
		entries.add(entry);
		
		
		
		// Selected.
		selectedEntry = 0;
		entries.get(selectedEntry).isSelected = true;
	}

	
	private void handleEntryActions()
	{
		switch(selectedEntry)
		{
		case 0: handleDifficultyEntry(); break;
		case 1: handleVolumeEntry(); break;
		case 2: handleSongEntry(); break;
		case 3: handleBackEntry(); break;
		}
	}
	
	private void handleDifficultyEntry()
	{
		if(Input.isKeyPressed(Keys.Left))
		{
			Game.game.difficultyLevel--;
			if(Game.game.difficultyLevel < Game.game.minDifficultyLevel)
			{
				Game.game.difficultyLevel = Game.game.minDifficultyLevel;
			}
		}
		
		if(Input.isKeyPressed(Keys.Right))
		{
			Game.game.difficultyLevel++;
			if(Game.game.difficultyLevel > Game.game.maxDifficultyLevel)
			{
				Game.game.difficultyLevel = Game.game.maxDifficultyLevel;
			}
		}
	}
	
	private void handleVolumeEntry()
	{
		if(Input.isKeyPressed(Keys.Left))
		{
			AudioManager.decreaseMasterVolume();
		}
		
		if(Input.isKeyPressed(Keys.Right))
		{
			AudioManager.increaseMasterVolume();
		}
	}
	
	private void handleSongEntry()
	{
		if(Input.isKeyPressed(Keys.Left))
		{

			Game.game.playCampaignGameState.currentSong++;
			Game.game.playCampaignGameState.currentSong %= Game.game.playCampaignGameState.songNames.length;
			
			if(currentlyPlayingSongID != -1)
			{
				AudioManager.stop(currentlyPlayingSongID);
				currentlyPlayingSongID = AudioManager.play(
						Game.game.playCampaignGameState.songNames[Game.game.playCampaignGameState.currentSong], 
						true
						);
			}			

		}
		
		if(Input.isKeyPressed(Keys.Right))
		{

			Game.game.playCampaignGameState.currentSong+= Game.game.playCampaignGameState.songNames.length - 1;
			Game.game.playCampaignGameState.currentSong %= Game.game.playCampaignGameState.songNames.length;
			
			if(currentlyPlayingSongID != -1)
			{
				AudioManager.stop(currentlyPlayingSongID);
				currentlyPlayingSongID = AudioManager.play(
						Game.game.playCampaignGameState.songNames[Game.game.playCampaignGameState.currentSong], 
						true
						);
			}		

		}
		
		if(Input.isKeyPressed(Keys.Enter))
		{
			if(currentlyPlayingSongID != -1)
			{
				AudioManager.stop(currentlyPlayingSongID);
			}
			
			currentlyPlayingSongID = AudioManager.play(
					Game.game.playCampaignGameState.songNames[Game.game.playCampaignGameState.currentSong], 
					true
					);
		}
	}
	
	private void handleBackEntry()
	{
		if(Input.isKeyPressed(Keys.Enter))
		{
			Game.game.gameStateMachine.changeState(Game.game.mainMenuState);
		}
	}

}
