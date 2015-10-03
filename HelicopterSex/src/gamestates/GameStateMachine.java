package gamestates;

import java.awt.Graphics2D;

import engine.GameTime;

public class GameStateMachine
{
	public GameState currentGameState = null;
	
	public void changeState(GameState newState)
	{
		if (newState != null)
		{
			if (currentGameState != null)
			{
				currentGameState.exitState();
			}
			
			currentGameState = newState;
			
			currentGameState.enterState();
		}
	}
	
	public void update(GameTime gameTime)
	{
		if (currentGameState != null)
		{
			currentGameState.update(gameTime);
		}
	}
	
	public void draw(Graphics2D g2d)
	{
		if(currentGameState != null)
		{
			currentGameState.draw(g2d);
		}
	}
	
}
