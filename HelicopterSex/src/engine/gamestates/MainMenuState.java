package engine.gamestates;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import content.Content;
import engine.Game;
import engine.GameTime;
import engine.audio.AudioManager;
import engine.input.Input;
import engine.input.Keys;
import engine.menu.GameMenuEntry;

public class MainMenuState
extends GameState
{
	
	List<GameMenuEntry> entries = new ArrayList<GameMenuEntry>();
	int selectedEntry;
	
	long backgroundSongID;
	
	public MainMenuState()
	{
		backgroundPanel = Content.backgroundPanels.get("mainMenu");
		createEntries();
	}

	@Override
	public void enterState()
	{
		System.out.println("Enter main menu state.");
		backgroundSongID = AudioManager.play("militaryIndustrial", true);
	}

	@Override
	public void update(GameTime gameTime)
	{		
		if (Input.isKeyPressed(Keys.Escape))
		{
			Game.game.exitGame();
		}
		
		if (Input.isKeyPressed(Keys.Enter))
		{
			AudioManager.playOnceNoInterrupt("swish01");
			
			switch (selectedEntry)
			{
			case 0: Game.game.gameStateMachine.changeState(Game.game.playCampaignGameState); break;
			case 1: Game.game.gameStateMachine.changeState(Game.game.gameOptionsState); break;
			case 2: Game.game.gameStateMachine.changeState(Game.game.highScoresState); break;
			case 4: Game.game.exitGame();
			}
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
		System.out.println("Exit main menu state.");
		AudioManager.stop(backgroundSongID);
	}
	
	private void createEntries()
	{
		GameMenuEntry entry;
		
		
		// Single player
		entry = new GameMenuEntry();
		entry.destinationRectangle = new Rectangle2D.Float(980 - 200, 540 - 25 - 300, 400, 50);
		entry.text = "Single player";
		entries.add(entry);
		
		
		// Options
		entry = new GameMenuEntry();
		entry.destinationRectangle = new Rectangle2D.Float(980 - 200, 540 - 25 - 150, 400, 50);
		entry.text = "Options";
		entries.add(entry);
		
		
		// Highscores
		entry = new GameMenuEntry();
		entry.destinationRectangle = new Rectangle2D.Float(980 - 200, 540 - 25, 400, 50);
		entry.text = "Highscores";
		entries.add(entry);
		
		
		// Help
		entry = new GameMenuEntry();
		entry.destinationRectangle = new Rectangle2D.Float(980 - 200, 540 - 25 + 150, 400, 50);
		entry.text = "Help";
		entries.add(entry);
		
		
		// Quit
		entry = new GameMenuEntry();
		entry.destinationRectangle = new Rectangle2D.Float(980 - 200, 540 - 25 + 300, 400, 50);
		entry.text = "Quit";
		entries.add(entry);
		
		
		
		
		// Selected.
		selectedEntry = 0;
		entries.get(selectedEntry).isSelected = true;
	}
	
}
