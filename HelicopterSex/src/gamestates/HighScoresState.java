package gamestates;

import engine.Game;
import engine.GameTime;
import input.Input;
import input.Keys;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;

import score.Score;
import utility.StringWriter;
import content.Content;

public class HighScoresState
extends GameState
{
	public HighScoresState()
	{
		backgroundPanel = Content.backgroundPanels.get("highScores");
		
		
	}

	@Override
	public void enterState()
	{
		Score.insertScore("Joxoleon", 1, 2);
		Score.insertScore("Dario", 3, 4);
		Score.insertScore("Osoba3", 5, 6);
		
	}

	@Override
	public void update(GameTime gameTime)
	{
		if(Input.isKeyDown(Keys.Escape))
		{
			Game.game.gameStateMachine.changeState(Game.game.mainMenuState);
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
		
		LinkedList<String> results;
		
		results = Score.printNamesToString(Score.scoresByPoints);
		results.addFirst("Names");
		StringWriter.writeStrings(g2d, results, new Point(300, 300), 10, "Quartz MS", 50, Color.yellow);
		
		results = Score.printScoresToString(Score.scoresByPoints);
		results.addFirst("Scores");
		StringWriter.writeStrings(g2d, results, new Point(800, 300), 10, "Quartz MS", 50, Color.yellow);
		
		results = Score.printTimesToString(Score.scoresByPoints);
		results.addFirst("Times");
		StringWriter.writeStrings(g2d, results, new Point(1100, 300), 10, "Quartz MS", 50, Color.yellow);
		
	}

	@Override
	public void exitState()
	{
		
	}

}
