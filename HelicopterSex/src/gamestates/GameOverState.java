package gamestates;

import engine.Game;
import engine.GameTime;
import input.Input;
import input.Keys;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import score.Score;
import utility.StringWriter;
import content.Content;

public class GameOverState
extends GameState
{
	private Point pointsPosition = new Point(140, 650);
	private Point timePlayedPosition = new Point(140, 750);
	
	private Point yourNameOrigin = new Point(1920 / 2, 1080 / 2 + 300);
	
	private String nameTypedString = "";
	private long maxNumberOfChars = 12;
	
	public GameOverState()
	{
		backgroundPanel = Content.backgroundPanels.get("gameOver");
	}

	@Override
	public void enterState()
	{
		nameTypedString = "";
	}

	@Override
	public void update(GameTime gameTime)
	{
		if(Input.isKeyDown(Keys.Escape))
		{
			Game.game.gameStateMachine.changeState(Game.game.mainMenuState);
		}
		if(Input.isKeyPressed(Keys.Back) && nameTypedString.length() > 0)
		{
			nameTypedString = nameTypedString.substring(0, nameTypedString.length() - 1);
		}
		
		if(nameTypedString.length() < maxNumberOfChars)
		{
			nameTypedString += Input.getKeysTyped();
		}
		
		if(Input.isKeyPressed(Keys.Enter))
		{
			if(nameTypedString.length() > 0)
			{
				Score.insertScore(nameTypedString, (long)Game.game.playCampaignGameState.score, (long)Game.game.playCampaignGameState.secondsPlayed);
				Game.game.gameStateMachine.changeState(Game.game.highScoresState);
			}
		}
		
	}

	@Override
	public void draw(Graphics2D g2d)
	{
		g2d.drawImage(
				backgroundPanel.backgroundImage,
				0, 0, (int)Game.game.worldDimension.getWidth(), (int)Game.game.worldDimension.getHeight(),
				0, 0, (int)backgroundPanel.backgroundImage.getWidth(), (int)backgroundPanel.backgroundImage.getHeight(),
				null
				);
				
		// Write points
		StringWriter.writeString(g2d, "Points: " + Game.game.playCampaignGameState.score , pointsPosition, false, "Quartz MS", 70, Color.yellow);

		// Write time
		StringWriter.writeString(g2d, "Time: " + (long)Game.game.playCampaignGameState.secondsPlayed, timePlayedPosition, false, "Quartz MS", 70, Color.yellow);
		
		// Write Keys Typed
		StringWriter.writeString(g2d, nameTypedString, yourNameOrigin, true, "Quartz MS", 70, Color.yellow);

	}

	@Override
	public void exitState()
	{
		
	}
	
	public void initializeState()
	{
//		BackgroundPanel backgroundPanel = Content.backgroundPanels.get();
		
	}

}
